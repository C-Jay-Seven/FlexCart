package com.potragis.FlexCart.service.order;

import com.potragis.FlexCart.dto.order.OrderRequest;
import com.potragis.FlexCart.dto.order.OrderResponse;
import com.potragis.FlexCart.dto.orderItems.OrderItemsRequest;
import com.potragis.FlexCart.mapper.order.OrderDTOMapper;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.OrderItems;
import com.potragis.FlexCart.model.entity.Orders;
import com.potragis.FlexCart.model.entity.Product;
import com.potragis.FlexCart.model.enums.PaymentMethod;
import com.potragis.FlexCart.model.enums.PaymentStatus;
import com.potragis.FlexCart.repository.ICartRepository;
import com.potragis.FlexCart.repository.IClientRepository;
import com.potragis.FlexCart.repository.IOrderRepository;
import com.potragis.FlexCart.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final IOrderRepository orderRepository;
    private final OrderDTOMapper mapper;
    private final IProductRepository productRepository;
    private final IClientRepository clientRepository;
    private final ICartRepository cartItemRepository;

    @Override
    public List<OrderResponse> getOrdersByClient(Long clientId) {

        List<Orders> orders =
                orderRepository.findByClientIdOrderByCreatedAtDesc(clientId);

        // ✅ NO exception — empty list is OK
        return orders.stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        return mapper.toDTO(order);
    }

    @Transactional
    public OrderResponse createOrder(Long clientId, OrderRequest orderRequest) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Orders order = new Orders();
        order.setClient(client);
        order.setStatus(true);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPaymentMethod(
                orderRequest.getPaymentMethod() != null
                        ? orderRequest.getPaymentMethod()
                        : PaymentMethod.CASH_ON_DELIVERY
        );

        List<OrderItems> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemsRequest itemReq : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));

            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setUnitPrice(product.getPrice());

            BigDecimal subtotal = (product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO)
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            orderItem.setSubtotal(subtotal);

            total = total.add(subtotal);
            items.add(orderItem);
        }

        order.setOrderItems(items);
        order.setTotalAmount(total);

        Orders savedOrder = orderRepository.save(order);

        // 🔹 DELETE CART ITEMS FIRST
        cartItemRepository.deleteAllByClientId(clientId);  // new method, deletes items only

        return mapper.toDTO(savedOrder);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Paid orders cannot be cancelled.");
        }

        order.setStatus(false);
        order.setPaymentStatus(PaymentStatus.CANCELED);

        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getOrdersBySeller(Long sellerId) {

        return orderRepository.findBySellerId(sellerId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }



}
