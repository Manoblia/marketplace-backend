package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Brand;
import com.uade.tpo.marketplace.exceptions.BrandDuplicateException;
import java.util.List;
import java.util.Optional;

public interface BrandService {

    List<Brand> getBrands();

    Optional<Brand> getBrandById(Long id);

    Brand createBrand(String name) throws BrandDuplicateException;

    Optional<Brand> updateBrand(Long id, String name) throws BrandDuplicateException;

    boolean deleteBrand(Long id);
}
