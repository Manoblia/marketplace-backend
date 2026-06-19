package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.marketplace.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    // Para buscar todas las fotos de un producto
    List<Image> findByProduct_ProductId(Long productId);
}