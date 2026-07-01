package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    Optional<Category> getCategoryById(Long id);

    Category createCategory(String name) throws CategoryDuplicateException;

    Optional<Category> updateCategory(Long id, String name) throws CategoryDuplicateException;

    boolean deleteCategory(Long id);
}
