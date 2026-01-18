package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.image.ImageRequest;
import com.potragis.FlexCart.dto.image.ImageResponse;
import com.potragis.FlexCart.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService imagesService;

    @Value("${product.upload.dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<ImageResponse> addImage(@RequestBody ImageRequest request) {
        return ResponseEntity.ok(imagesService.addImage(request));
    }

//    @GetMapping("/product/{productId}")
//    public ResponseEntity<List<ImageResponse>> getImagesByProduct(@PathVariable Long productId) {
//        return ResponseEntity.ok(imagesService.getImagesByProduct(productId));
//    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read file: " + filename);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imagesService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }
}
