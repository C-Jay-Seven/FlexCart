package com.potragis.FlexCart.service.image;

import com.potragis.FlexCart.dto.image.ImageRequest;
import com.potragis.FlexCart.dto.image.ImageResponse;
import com.potragis.FlexCart.mapper.image.ImageDTOMapper;
import com.potragis.FlexCart.model.entity.Images;
import com.potragis.FlexCart.model.entity.Product;
import com.potragis.FlexCart.repository.IImageRepository;
import com.potragis.FlexCart.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final IImageRepository imagesRepository;
    private final IProductRepository productRepository;
    private final ImageDTOMapper mapper;

    @Override
    public ImageResponse addImage(ImageRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        Images image = mapper.toEntity(request, product);

        Images saved = imagesRepository.save(image);

        return mapper.toDTO(saved);
    }

    @Override
    public List<ImageResponse> getImagesByProduct(Long productId) {
        return imagesRepository.findByProductId(productId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void deleteImage(Long imageId) {
        Images image = imagesRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found: " + imageId));

        imagesRepository.delete(image);
    }

}
