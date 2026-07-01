package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
import com.uade.tpo.marketplace.repository.CategoryRepository;
import com.uade.tpo.marketplace.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(String name) throws CategoryDuplicateException {
        if (categoryRepository.existsByCategoryNameIgnoreCase(name)) {
            throw new CategoryDuplicateException();
        }

        Category category = new Category();
        category.setCategoryName(name);

        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> updateCategory(Long id, String name) throws CategoryDuplicateException {
        Optional<Category> existingCategory = categoryRepository.findById(id);

        if (existingCategory.isEmpty()) {
            return Optional.empty();
        }

        Category category = existingCategory.get();

        if (
            !category.getCategoryName().equalsIgnoreCase(name) &&
            categoryRepository.existsByCategoryNameIgnoreCase(name)
        ) {
            throw new CategoryDuplicateException();
        }

        category.setCategoryName(name);

        return Optional.of(categoryRepository.save(category));
    }

    @Override
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }

        return false;
    }
}