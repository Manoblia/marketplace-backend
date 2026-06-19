package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Image;
import java.util.List;
import java.util.Optional;

public interface ImageService {

    List<Image> getImagesByProductId(Long productId);

    Optional<Image> getImageById(Long id);

    Image createImage(Long productId, byte[] imageContent, String fileName, String fileType);

    Optional<Image> updateImage(Long id, byte[] imageContent, String fileName, String fileType);

    boolean deleteImage(Long id);

    boolean deleteAllImagesByProductId(Long productId);
}
