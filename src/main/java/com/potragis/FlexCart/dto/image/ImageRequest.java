package com.potragis.FlexCart.dto.image;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {

    private String filename;
    private String fileType;
    private String url;
    private boolean isPrimary;
    private Long productId;
}