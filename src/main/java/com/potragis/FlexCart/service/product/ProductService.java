package com.potragis.FlexCart.service.product;

import com.potragis.FlexCart.dto.product.ProductRequest;
import com.potragis.FlexCart.dto.product.ProductResponse;
import com.potragis.FlexCart.mapper.product.ProductDTOMapper;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.Images;
import com.potragis.FlexCart.model.entity.Product;
import com.potragis.FlexCart.repository.IClientRepository;
import com.potragis.FlexCart.repository.IImageRepository;
import com.potragis.FlexCart.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final IProductRepository productRepository;
    private final ProductDTOMapper mapper;
    private final IClientRepository clientRepository;
    private final IImageRepository imageRepository;

    @Value("${product.upload.dir}")
    private String uploadDir;

    // -------------------
    // CREATE PRODUCT
    // -------------------
    @Override
    public ProductResponse createProduct(ProductRequest productRequest, String username) throws IOException {
        // 1️⃣ Fetch client by username
        Client client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Client with username '" + username + "' not found"));

        // 2️⃣ Map DTO to entity
        Product product = mapper.toEntity(productRequest);
        product.setClient(client);

        // 3️⃣ Handle image upload (single or multiple files)
        handleImageUpload(productRequest.getImage(), product);

        // 4️⃣ Save product
        Product saved = productRepository.save(product);

        return mapper.toDTO(saved);
    }

    // -------------------
    // UPDATE PRODUCT
    // -------------------
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) throws IOException {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update basic fields
        existing.setProductName(productRequest.getProductName());
        existing.setDescription(productRequest.getProductDescription());
        existing.setPrice(productRequest.getProductPrice());
        existing.setStock(productRequest.getStock());

        // Only replace images if new image is provided
        if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
            handleImageUpload(productRequest.getImage(), existing);
        }

        Product saved = productRepository.save(existing);
        return mapper.toDTO(saved);
    }

    // -------------------
    // IMAGE UPLOAD HELPER
    // -------------------
    private void handleImageUpload(MultipartFile imageFile, Product product) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return; // don't overwrite existing images if none uploaded
        }

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path dirPath = Paths.get(uploadDir);

        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        Path filePath = dirPath.resolve(fileName);
        imageFile.transferTo(filePath.toFile());

        Images image = new Images();
        image.setUrl("/images/" + fileName);
        image.setProduct(product);
        image.setPrimary(true); // mark uploaded image as primary
        product.setImages(List.of(image)); // replaces old images (optional)
    }


    // -------------------
    // GET PRODUCT BY ID
    // -------------------
    @Override
    public ProductResponse getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // -------------------
    // GET ALL PRODUCTS
    // -------------------
    @Override
    public List<ProductResponse> getAllProducts() {
        return mapper.toDTO(productRepository.findAll());
    }

    // -------------------
    // DELETE PRODUCT
    // -------------------
    public void deleteProduct(Long productId) {
        // Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        // Fetch all images for this product
        List<Images> images = imageRepository.findByProductId(productId);

        for (Images image : images) {
            if (image != null && image.getUrl() != null) {
                try {
                    // Resolve the file name safely
                    Path filePath = Paths.get(uploadDir).resolve(Paths.get(image.getUrl()).getFileName());
                    if (Files.exists(filePath)) {
                        Files.delete(filePath);
                        System.out.println("Deleted file: " + filePath);
                    } else {
                        System.out.println("File does not exist: " + filePath);
                    }
                } catch (Exception e) {
                    // Log and continue if deletion fails
                    System.err.println("Failed to delete image: " + image.getId() + " -> " + e.getMessage());
                }
            } else {
                System.out.println("Skipping image deletion: image or URL is null for image ID " +
                        (image != null ? image.getId() : "null"));
            }
        }

        // Delete images from DB
        imageRepository.deleteAll(images);

        // Finally, delete the product
        productRepository.delete(product);

        System.out.println("Deleted product ID: " + productId);
    }
}
