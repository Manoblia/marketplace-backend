package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.controllers.dto.VariantRequest;
import com.uade.tpo.marketplace.entity.Variant;
import com.uade.tpo.marketplace.services.interfaces.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Variant>> getVariantsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(variantService.getVariantsByProductId(productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Variant> getVariantById(@PathVariable Long id) {
        return variantService.getVariantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Variant> createVariant(@RequestBody VariantRequest request) {
        Variant variant = variantService.createVariant(request.getProductId(), request.getSize(), request.getStock());
        return ResponseEntity.status(HttpStatus.CREATED).body(variant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variant> updateVariant(@PathVariable Long id, @RequestBody VariantRequest request) {
        return variantService.updateVariant(id, request.getSize(), request.getStock())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        if (variantService.deleteVariant(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
