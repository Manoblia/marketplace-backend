package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.controllers.dto.CartItemRequest;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.services.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getMyCart(@AuthenticationPrincipal User user) {
        return cartService.getCartByUserId(user.getUserId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItem(
            @AuthenticationPrincipal User user,
            @RequestBody CartItemRequest request
    ) {
        Cart cart = cartService.addItemToCart(
                user.getUserId(),
                request.getVariantId(),
                request.getQuantity()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cart);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<Cart> updateItemQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId,
            @RequestBody CartItemRequest request
    ) {
        Cart cart = cartService.updateItemQuantity(
                user.getUserId(),
                cartItemId,
                request.getQuantity()
        );

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long cartItemId
    ) {
        if (cartService.removeItemFromCart(user.getUserId(), cartItemId)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user.getUserId());
        return ResponseEntity.noContent().build();
    }
}
