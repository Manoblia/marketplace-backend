package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.Variant;
import com.uade.tpo.marketplace.exceptions.InsufficientStockException;
import com.uade.tpo.marketplace.repository.CartItemRepository;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.repository.VariantRepository;
import com.uade.tpo.marketplace.services.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final VariantRepository variantRepository;

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUser_UserId(userId);
    }

    @Override
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new ArrayList<>());

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long userId, Long variantId, Integer quantity) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseGet(() -> createCart(userId));

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));

        if (variant.getStock() < quantity) {
            throw new InsufficientStockException("Stock insuficiente para el talle seleccionado");
        }

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getVariant().getVariantId().equals(variantId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();

            int newQuantity = item.getQuantity() + quantity;

            if (variant.getStock() < newQuantity) {
                throw new InsufficientStockException("Stock insuficiente para el talle seleccionado");
            }

            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQuantity(quantity);

            cart.getCartItems().add(newItem);

            cartItemRepository.save(newItem);
        }

        return cartRepository.findById(cart.getCartId()).orElse(cart);
    }

    @Override
    @Transactional
    public Cart updateItemQuantity(Long userId, Long cartItemId, Integer quantity) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        CartItem item = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getCartItemId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (quantity <= 0) {
            cart.getCartItems().remove(item);
            cartRepository.save(cart);
            cartItemRepository.delete(item);

            return cartRepository.findById(cart.getCartId()).orElse(cart);
        }

        if (item.getVariant().getStock() < quantity) {
            throw new InsufficientStockException("Stock insuficiente para el talle seleccionado");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return cartRepository.findById(cart.getCartId()).orElse(cart);
    }

    @Override
    @Transactional
    public boolean removeItemFromCart(Long userId, Long cartItemId) {
        return cartRepository.findByUser_UserId(userId).map(cart -> {
            Optional<CartItem> itemToRemove = cart.getCartItems().stream()
                    .filter(item -> item.getCartItemId().equals(cartItemId))
                    .findFirst();

            if (itemToRemove.isPresent()) {
                CartItem item = itemToRemove.get();

                cart.getCartItems().remove(item);
                cartRepository.save(cart);

                cartItemRepository.delete(item);

                return true;
            }

            return false;
        }).orElse(false);
    }

    @Override
    @Transactional
    public boolean clearCart(Long userId) {
        return cartRepository.findByUser_UserId(userId).map(cart -> {
            cart.getCartItems().clear();
            cartRepository.save(cart);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean deleteCart(Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return true;
        }

        return false;
    }
}