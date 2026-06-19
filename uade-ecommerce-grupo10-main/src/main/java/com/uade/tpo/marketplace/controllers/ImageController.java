package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.Image;
import com.uade.tpo.marketplace.services.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Image>> getImagesByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(imageService.getImagesByProductId(productId));
    }

    @GetMapping("/{id}/data")
    public ResponseEntity<byte[]> getImageData(@PathVariable Long id) {
        return imageService.getImageById(id).map(image -> ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .body(image.getImage()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadImage(
            @RequestParam Long productId,
            @RequestParam MultipartFile file) throws IOException {
        Image image = imageService.createImage(
                productId,
                file.getBytes(),
                file.getOriginalFilename(),
                file.getContentType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        if (imageService.deleteImage(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
