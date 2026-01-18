package com.potragis.FlexCart.dto.product;

import com.potragis.FlexCart.model.entity.Images;
import com.potragis.FlexCart.model.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private List<String> imageUrls;
    private Long clientId; // <-- add this

    public ProductResponse(Product product, String imageUrl) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productDescription = product.getDescription();
        this.productPrice = product.getPrice();

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            this.imageUrls = product.getImages().stream()
                    .map(Images::getUrl)
                    .toList();
        } else {
            this.imageUrls = List.of();
        }

        this.clientId = product.getClient() != null ? product.getClient().getId() : null;
    }
}
