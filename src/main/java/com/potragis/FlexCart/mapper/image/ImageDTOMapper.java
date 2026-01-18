package com.potragis.FlexCart.mapper.image;

import com.potragis.FlexCart.dto.image.ImageRequest;
import com.potragis.FlexCart.dto.image.ImageResponse;
import com.potragis.FlexCart.model.entity.Images;
import com.potragis.FlexCart.model.entity.Product;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ImageDTOMapper {

    public Images toEntity(ImageRequest request, Product product) {
        Images image = new Images();
        image.setFilename(request.getFilename());
        image.setFileType(request.getFileType());
        image.setUrl(request.getUrl());
        image.setPrimary(request.isPrimary());
        image.setProduct(product);
        return image;
    }

    public ImageResponse toDTO(Images image) {
        ImageResponse dto = new ImageResponse();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        return dto;
    }
}
