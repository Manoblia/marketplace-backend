package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Brand;
import com.uade.tpo.marketplace.exceptions.BrandDuplicateException;
import com.uade.tpo.marketplace.repository.BrandRepository;
import com.uade.tpo.marketplace.services.interfaces.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand createBrand(String name) throws BrandDuplicateException {
        if (brandRepository.existsByBrandNameIgnoreCase(name)) {
            throw new BrandDuplicateException();
        }

        Brand brand = new Brand();
        brand.setBrandName(name);

        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> updateBrand(Long id, String name) throws BrandDuplicateException {
        Optional<Brand> existingBrand = brandRepository.findById(id);

        if (existingBrand.isEmpty()) {
            return Optional.empty();
        }

        Brand brand = existingBrand.get();

        if (
            !brand.getBrandName().equalsIgnoreCase(name) &&
            brandRepository.existsByBrandNameIgnoreCase(name)
        ) {
            throw new BrandDuplicateException();
        }

        brand.setBrandName(name);

        return Optional.of(brandRepository.save(brand));
    }

    @Override
    public boolean deleteBrand(Long id) {
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
