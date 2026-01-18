package com.potragis.FlexCart.mapper.product;

import com.potragis.FlexCart.dto.product.ProductRequest;
import com.potragis.FlexCart.dto.product.ProductResponse;
import com.potragis.FlexCart.model.entity.Images;
import com.potragis.FlexCart.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDTOMapper {

    public Product toEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getProductPrice());
        product.setStock(productRequest.getStock());
        return product;
    }

    // --------------------
    // ENTITY → DTO
    // --------------------
    public ProductResponse toDTO(Product product) {
        String imageUrl = null;

        // get primary or first image
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            Images primaryImage = product.getImages().stream()
                    .filter(Images::isPrimary)
                    .findFirst()
                    .orElse(product.getImages().get(0));

            imageUrl = primaryImage.getUrl();
        }

        ProductResponse response = new ProductResponse(product, imageUrl);

        // ✅ IMPORTANT: attach seller/client ID
        if (product.getClient() != null) {
            response.setClientId(product.getClient().getId());
        }

        return response;
    }

    // --------------------
    // LIST MAPPING
    // --------------------
    public List<ProductResponse> toDTO(List<Product> products) {
        return products.stream()
                .map(this::toDTO)
                .toList();
    }

}
