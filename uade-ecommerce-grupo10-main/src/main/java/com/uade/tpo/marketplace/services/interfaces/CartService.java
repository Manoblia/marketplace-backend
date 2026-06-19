package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Cart;
import java.util.Optional;

public interface CartService {

    Optional<Cart> getCartByUserId(Long userId);

    Optional<Cart> getCartById(Long id);

    Cart createCart(Long userId);

    Cart addItemToCart(Long userId, Long variantId, Integer quantity);

    Cart updateItemQuantity(Long userId, Long cartItemId, Integer quantity);

    boolean removeItemFromCart(Long userId, Long cartItemId);

    boolean clearCart(Long userId);

    boolean deleteCart(Long id);
}