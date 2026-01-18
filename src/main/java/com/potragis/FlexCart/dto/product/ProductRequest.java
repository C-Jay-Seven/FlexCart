package com.potragis.FlexCart.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name must not be empty")
    private String productName;

    @NotBlank(message = "Product description must not be empty")
    private String productDescription;

    @NotNull(message = "Product price must not be empty")
    private BigDecimal productPrice;

    @NotNull(message = "Product stock must not empty")
    @Min(value = 1, message = "Product stock must have at least 1")
    private Integer stock;

    private MultipartFile image; // optional for update

    private String username; // now included
}
