package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.*;
import com.uade.tpo.marketplace.exceptions.InsufficientStockException;
import com.uade.tpo.marketplace.repository.*;
import com.uade.tpo.marketplace.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final VariantRepository variantRepository;

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_UserIdOrderByOrderDateDesc(userId);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(Long userId, double total) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Order order = new Order();
        order.setUser(user);
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(new ArrayList<>());

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order checkoutFromCart(
            Long userId,
            String fullName,
            String shippingAddress,
            String paymentMethod
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getVariant().getStock() < cartItem.getQuantity()) {
                throw new InsufficientStockException(
                        "Stock insuficiente para talle " + cartItem.getVariant().getSize()
                );
            }
        }

        double shipping = 12000;

        double subtotal = cart.getCartItems().stream()
                .mapToDouble(item -> {
                    double price = item.getVariant().getProduct().getPrice();
                    return price * item.getQuantity();
                })
                .sum();

        double total = subtotal + shipping;

        Order order = new Order();

        order.setUser(user);
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());
        order.setFullName(fullName);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setOrderItems(new ArrayList<>());

        orderRepository.save(order);

        for (CartItem cartItem : cart.getCartItems()) {
            Variant variant = cartItem.getVariant();

            double unitPrice = variant.getProduct().getPrice();

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setVariant(variant);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(unitPrice);

            orderItemRepository.save(orderItem);

            variant.setStock(variant.getStock() - cartItem.getQuantity());
            variantRepository.save(variant);
        }

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return orderRepository.findById(order.getOrderId()).orElse(order);
    }

    @Override
    public Optional<Order> updateOrder(Long id, double total) {
        return orderRepository.findById(id).map(order -> {
            order.setTotal(total);
            return orderRepository.save(order);
        });
    }

    @Override
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
