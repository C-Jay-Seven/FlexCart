package com.potragis.FlexCart.service.image;

import com.potragis.FlexCart.dto.image.ImageRequest;
import com.potragis.FlexCart.dto.image.ImageResponse;

import java.util.List;

public interface IImageService {

    ImageResponse addImage(ImageRequest request);

    List<ImageResponse> getImagesByProduct(Long productId);

    void deleteImage(Long imageId);

}
