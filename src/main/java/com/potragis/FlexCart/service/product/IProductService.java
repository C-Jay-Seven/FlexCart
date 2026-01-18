package com.potragis.FlexCart.service.product;

import com.potragis.FlexCart.dto.product.ProductRequest;
import com.potragis.FlexCart.dto.product.ProductResponse;
import com.potragis.FlexCart.model.entity.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductRequest productRequest, String username) throws IOException;
    ProductResponse getProductById(Long productId);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(Long id, ProductRequest productRequest) throws IOException;
    void deleteProduct(Long productId);
}
