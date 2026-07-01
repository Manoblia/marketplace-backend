package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Brand;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.repository.BrandRepository;
import com.uade.tpo.marketplace.repository.CategoryRepository;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByBrandId(Long brandId) {
        return productRepository.findByBrand_BrandId(brandId);
    }

    @Override
    public Product createProduct(Product product) {

        Brand brand = brandRepository.findById(product.getBrand().getBrandId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        product.setBrand(brand);
        product.setCategory(category);

        // Asocia las variantes al producto
        if (product.getVariants() != null) {
            product.getVariants().forEach(variant -> variant.setProduct(product));
        }

        // Asocia las imágenes al producto
        if (product.getImages() != null) {
            product.getImages().forEach(image -> image.setProduct(product));
        }

        Product savedProduct = productRepository.save(product);

        return productRepository.findById(savedProduct.getProductId())
                .orElse(savedProduct);
    }

    @Override
    public Optional<Product> updateProduct(Long productId, Product productDetails) {

        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {

            Product productToUpdate = existingProduct.get();

            productToUpdate.setModel(productDetails.getModel());
            productToUpdate.setDescription(productDetails.getDescription());
            productToUpdate.setPrice(productDetails.getPrice());
            productToUpdate.setDiscount(productDetails.getDiscount());

            if (productDetails.getBrand() != null) {
                Brand brand = brandRepository.findById(productDetails.getBrand().getBrandId())
                        .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
                productToUpdate.setBrand(brand);
            }

            if (productDetails.getCategory() != null) {
                Category category = categoryRepository.findById(productDetails.getCategory().getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                productToUpdate.setCategory(category);
            }

            return Optional.of(productRepository.save(productToUpdate));
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(Long productId) {

        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }

        return false;
    }
}