package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    // Obtener el listado completo de categorías
    List<Category> getCategories();

    // Buscar una categoría por su ID
    Optional<Category> getCategoryById(Long id);

    // Crear una categoría validando que el nombre no esté duplicado
    Category createCategory(String name) throws CategoryDuplicateException;

    // Eliminar una categoría y confirmar si se realizó la operación
    boolean deleteCategory(Long id);
}
