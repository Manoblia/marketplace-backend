package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.Brand;
import com.uade.tpo.marketplace.exceptions.BrandDuplicateException;
import com.uade.tpo.marketplace.services.interfaces.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getBrands() {
        return ResponseEntity.ok(brandService.getBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_VENDEDOR')")
    @PostMapping
    public ResponseEntity<?> createBrand(@RequestBody Map<String, String> body) {
        try {
            Brand brand = brandService.createBrand(body.get("brandName"));
            return ResponseEntity.status(HttpStatus.CREATED).body(brand);
        } catch (BrandDuplicateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_VENDEDOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        if (brandService.deleteBrand(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}