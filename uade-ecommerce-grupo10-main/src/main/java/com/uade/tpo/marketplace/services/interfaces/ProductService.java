package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    // Obtener todos los productos
    List<Product> getProducts();

    // Obtener un producto por su ID
    Optional<Product> getProductById(Long id);

    // Obtener productos filtrados por categoría
    List<Product> getProductsByCategoryId(Long categoryId);

    // Obtener productos filtrados por marca
    List<Product> getProductsByBrandId(Long brandId);

    // Crear un producto asociando su marca y categoría
    Product createProduct(Product product);

    // Actualizar los datos de un producto existente
    Optional<Product> updateProduct(Long productId, Product product);

    // Eliminar un producto y sus dependencias (variantes/imágenes por cascade)
    boolean deleteProduct(Long ProductId);
}
