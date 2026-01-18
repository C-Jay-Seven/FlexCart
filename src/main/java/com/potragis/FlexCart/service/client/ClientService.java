package com.potragis.FlexCart.service.client;

import com.potragis.FlexCart.dto.client.ClientRequest;
import com.potragis.FlexCart.dto.client.ClientResponse;
import com.potragis.FlexCart.dto.order.OrderRequest;
import com.potragis.FlexCart.mapper.client.ClientDTOMapper;
import com.potragis.FlexCart.model.entity.*;
import com.potragis.FlexCart.model.enums.RoleName;
import com.potragis.FlexCart.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final IClientRepository clientRepository;
    private final IRoleRepository roleRepository;
    private final ClientDTOMapper mapper;
    private final ICartRepository cartRepository;
    private final IReviewRepository reviewRepository;
    private final IProductRepository productRepository;
    private final IOrderRepository orderRepository;

    private static final String DEFAULT_AVATAR = "/avatars/default.png";

    @Override
    public ClientResponse createClient(ClientRequest clientRequest) {
        Client client = mapper.toEntity(clientRequest);
        client.setActive(true);

        if (client.getRoles() == null) client.setRoles(new ArrayList<>());

        // Get default CLIENT role
        Role defaultRole = roleRepository.findByRoleName(RoleName.CLIENT)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setRoleName(RoleName.CLIENT);
                    role.setDescription("Role granted automatically on request approval");
                    role.setActive(true);
                    return roleRepository.save(role);
                });

        // Assign CLIENT role only if not already assigned
        boolean hasClientRole = client.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.CLIENT));
        if (!hasClientRole) {
            client.getRoles().add(defaultRole);
        }

        client.setAddress(clientRequest.getAddress());
        Client saved = clientRepository.save(client);
        return mapper.toDTO(saved);
    }

    @Override
    public ClientResponse getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public List<ClientResponse> getAllClient() {
        return mapper.toDTO(clientRepository.findAll());
    }

    @Override
    @Transactional
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // =========================
        // BASIC FIELD UPDATES
        // =========================
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setUsername(request.getUsername());
        client.setPassword(request.getPassword());
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(request.getAddress());

        // =========================
        // ROLE UPDATE (SAFE + ADDITIVE)
        // =========================
        if (request.getRole() != null) {

            RoleName roleName = request.getRole().getRoleName();

            boolean hasRole = client.getRoles().stream()
                    .anyMatch(role -> role.getRoleName() == roleName);

            if (!hasRole) {
                Role role = new Role();
                role.setRoleName(roleName);
                role.setClient(client);
                role.setActive(true);

                client.getRoles().add(role);
                roleRepository.save(role);
            }
        }

        Client updated = clientRepository.save(client);
        return mapper.toDTO(updated);
    }

    @Transactional
    public void addRoleIfNotExists(Client client, RoleName roleName) {

        boolean exists = roleRepository
                .existsByClientIdAndRoleName(client.getId(), roleName);

        if (!exists) {
            Role role = new Role();
            role.setRoleName(roleName);
            role.setClient(client);
            role.setActive(true);
            role.setDescription(roleName.name() + " role");

            roleRepository.save(role);
        }
    }


    @Transactional
    @Override
    public void deleteClient(Long clientId, RoleName roleName) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (client.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleName.ADMIN))) {
            throw new RuntimeException("Cannot delete an ADMIN");
        }

        if (client.getCart() != null) cartRepository.delete(client.getCart());
        if (client.getReviews() != null && !client.getReviews().isEmpty()) reviewRepository.deleteAll(client.getReviews());
        if (client.getProduct() != null && !client.getProduct().isEmpty()) productRepository.deleteAll(client.getProduct());
        if (client.getOrders() != null && !client.getOrders().isEmpty()) orderRepository.deleteAll(client.getOrders());

        clientRepository.delete(client);
    }

    @Transactional
    public ClientResponse setIsActive(Long clientId, boolean isActive) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setActive(isActive);
        Client savedClient = clientRepository.save(client);
        return new ClientResponse(savedClient);
    }

    @Transactional
    public String updateAvatar(Long clientId, MultipartFile file) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (file.isEmpty()) throw new RuntimeException("File is empty");
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/"))
            throw new RuntimeException("Only image files are allowed");

        try {
            Path uploadDir = Paths.get("uploads/avatars");
            Files.createDirectories(uploadDir);

            String extension = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename().lastIndexOf("."));

            String fileName = "client-" + clientId + extension;
            Path filePath = uploadDir.resolve(fileName);

            Files.write(filePath, file.getBytes());

            String avatarUrl = "/avatars/" + fileName;
            client.setAvatarUrl(avatarUrl);
            clientRepository.save(client);

            return avatarUrl;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar", e);
        }
    }

    @Override
    public ClientResponse getCurrentSeller(Long sellerId) {
        Client client = clientRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        boolean isSeller = client.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.SELLER));

        if (!isSeller) throw new RuntimeException("Client is not a seller");

        return new ClientResponse(client);
    }

    // ------------------- NEW: Order creation -------------------
    @Transactional
    public Orders createOrder(Long clientId, OrderRequest orderRequest) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Orders order = new Orders();
        order.setClient(client);
        order.setStatus(Boolean.parseBoolean("PENDING")); // PENDING as string, not Boolean

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItems> items = new ArrayList<>();

        for (var itemReq : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));

            OrderItems orderItem = new OrderItems();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setUnitPrice(itemReq.getUnitPrice());
            orderItem.setSubtotal(itemReq.getSubtotal());
            orderItem.setOrder(order);

            totalAmount = totalAmount.add(orderItem.getSubtotal());
            items.add(orderItem);
        }

        order.setOrderItems(items);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
}
