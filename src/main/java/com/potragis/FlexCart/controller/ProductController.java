package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.product.ProductRequest;
import com.potragis.FlexCart.dto.product.ProductResponse;
import com.potragis.FlexCart.service.product.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @ModelAttribute ProductRequest productRequest, // binds form-data including image
            @RequestParam("username") String username      // binds query param ?username=sellerUsername
    ) throws IOException {
        // Call service with productRequest and username
        ProductResponse createdProduct = productService.createProduct(productRequest, username);
        return ResponseEntity.ok(createdProduct);
    }


    @GetMapping("/{id}")
    public ProductResponse fetchProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> fetchAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@Valid @PathVariable Long id,
                                         @ModelAttribute ProductRequest productRequest) throws IOException {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

}
