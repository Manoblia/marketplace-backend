package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.Variant;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.repository.VariantRepository;
import com.uade.tpo.marketplace.services.interfaces.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Variant> getVariantsByProductId(Long productId) {
        return variantRepository.findByProduct_ProductId(productId);
    }

    @Override
    public Optional<Variant> getVariantById(Long id) {
        return variantRepository.findById(id);
    }

    @Override
    public Variant createVariant(Long productId, int size, int stock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Variant variant = new Variant();
        variant.setProduct(product);
        variant.setSize(size);
        variant.setStock(stock);
        return variantRepository.save(variant);
    }

    @Override
    public Optional<Variant> updateVariant(Long id, int size, int stock) {
        return variantRepository.findById(id).map(variant -> {
            variant.setSize(size);
            variant.setStock(stock);
            return variantRepository.save(variant);
        });
    }

    @Override
    public boolean updateStock(Long id, int newStock) {
        return variantRepository.findById(id).map(variant -> {
            variant.setStock(newStock);
            variantRepository.save(variant);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean deleteVariant(Long id) {
        if (variantRepository.existsById(id)) {
            variantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
