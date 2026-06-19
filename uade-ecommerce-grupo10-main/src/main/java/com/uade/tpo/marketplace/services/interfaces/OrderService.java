package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getOrders();

    List<Order> getOrdersByUserId(Long userId);

    Optional<Order> getOrderById(Long id);

    Order createOrder(Long userId, double total);

    Order checkoutFromCart(
            Long userId,
            String fullName,
            String shippingAddress,
            String paymentMethod
    );

    Optional<Order> updateOrder(Long id, double total);

    boolean deleteOrder(Long id);
}