package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemService {

    // Obtener todos los items de un carrito específico
    List<CartItem> getCartItemsByCartId(Long cartId);

    // Obtener un item específico por su ID
    Optional<CartItem> getCartItemById(Long id);

    // Crear un nuevo item en el carrito
    CartItem createCartItem(Long cartId, Long variantId, int quantity);

    // Actualizar la cantidad de un item existente
    Optional<CartItem> updateCartItemQuantity(Long id, int quantity);

    // Eliminar un item del carrito
    boolean deleteCartItem(Long id);
    
    // Método de utilidad para verificar stock o existencia antes de operar
    boolean existsByCartAndVariant(Long cartId, Long variantId);
}
