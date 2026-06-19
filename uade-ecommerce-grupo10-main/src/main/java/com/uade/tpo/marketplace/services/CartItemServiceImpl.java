package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.Variant;
import com.uade.tpo.marketplace.repository.CartItemRepository;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.repository.VariantRepository;
import com.uade.tpo.marketplace.services.interfaces.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final VariantRepository variantRepository;

    @Override
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartRepository.findById(cartId)
                .map(Cart::getCartItems)
                .orElse(List.of());
    }

    @Override
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public CartItem createCartItem(Long cartId, Long variantId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setVariant(variant);
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Optional<CartItem> updateCartItemQuantity(Long id, int quantity) {
        return cartItemRepository.findById(id).map(item -> {
            item.setQuantity(quantity);
            return cartItemRepository.save(item);
        });
    }

    @Override
    public boolean deleteCartItem(Long id) {
        if (cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByCartAndVariant(Long cartId, Long variantId) {
        return cartRepository.findById(cartId)
                .map(cart -> cart.getCartItems().stream()
                        .anyMatch(item -> item.getVariant().getVariantId().equals(variantId)))
                .orElse(false);
    }
}
