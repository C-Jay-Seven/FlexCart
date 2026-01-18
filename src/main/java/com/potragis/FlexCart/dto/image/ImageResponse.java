package com.potragis.FlexCart.dto.image;


import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private Long id;
    private String filename;
    private String fileType;
    private String url;
    private boolean isPrimary;
    private Long productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ImageResponse(Long id,
                         String filename,
                         String fileType,
                         String url,
                         boolean isPrimary,
                         Long productId) {
        this.id = id;
        this.filename = filename;
        this.fileType = fileType;
        this.url = url;
        this.isPrimary = isPrimary;
        this.productId = productId;
    }


}
