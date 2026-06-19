package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.marketplace.entity.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    // Para traer todos los talles de una zapatilla
    List<Variant> findByProduct_ProductId(Long productId);
}
