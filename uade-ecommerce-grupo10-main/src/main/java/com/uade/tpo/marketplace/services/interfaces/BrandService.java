package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Brand;
import com.uade.tpo.marketplace.exceptions.BrandDuplicateException;
import java.util.List;
import java.util.Optional;

public interface BrandService {

    // Obtener todas las marcas
    List<Brand> getBrands();

    // Obtener una marca por su ID
    Optional<Brand> getBrandById(Long id);

    // Crear una marca (lanzando excepción si ya existe el nombre)
    Brand createBrand(String name) throws BrandDuplicateException;

    // Eliminar una marca devolviendo confirmación de éxito
    boolean deleteBrand(Long id);
}
