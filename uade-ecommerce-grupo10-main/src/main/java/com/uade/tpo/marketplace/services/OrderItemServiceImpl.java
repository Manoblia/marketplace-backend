package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.OrderItem;
import com.uade.tpo.marketplace.entity.Variant;
import com.uade.tpo.marketplace.repository.OrderItemRepository;
import com.uade.tpo.marketplace.repository.OrderRepository;
import com.uade.tpo.marketplace.repository.VariantRepository;
import com.uade.tpo.marketplace.services.interfaces.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final VariantRepository variantRepository;

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .map(Order::getOrderItems)
                .orElse(List.of());
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItem createOrderItem(Long orderId, Long variantId, int quantity, double unitPrice) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setVariant(variant);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        return orderItemRepository.save(item);
    }
}
