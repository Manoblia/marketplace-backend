package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Image;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.repository.ImageRepository;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.services.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Image> getImagesByProductId(Long productId) {
        return imageRepository.findByProduct_ProductId(productId);
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image createImage(Long productId, byte[] imageContent, String fileName, String fileType) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Image image = new Image();
        image.setProduct(product);
        image.setImage(imageContent);
        image.setFileName(fileName);
        image.setFileType(fileType);
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> updateImage(Long id, byte[] imageContent, String fileName, String fileType) {
        return imageRepository.findById(id).map(image -> {
            image.setImage(imageContent);
            image.setFileName(fileName);
            image.setFileType(fileType);
            return imageRepository.save(image);
        });
    }

    @Override
    public boolean deleteImage(Long id) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllImagesByProductId(Long productId) {
        List<Image> images = imageRepository.findByProduct_ProductId(productId);
        if (!images.isEmpty()) {
            imageRepository.deleteAll(images);
            return true;
        }
        return false;
    }
}
