package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.controllers.dto.CheckoutRequest;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByUserId(user.getUserId())
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(
                orderService.getOrders()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id
    ) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(
            @AuthenticationPrincipal User user,
            @RequestBody CheckoutRequest request
    ) {

        Order order = orderService.checkoutFromCart(
                user.getUserId(),
                request.getFullName(),
                request.getShippingAddress(),
                request.getPaymentMethod()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id
    ) {

        if (orderService.deleteOrder(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}