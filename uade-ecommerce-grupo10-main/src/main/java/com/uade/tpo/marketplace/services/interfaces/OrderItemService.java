package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderItemService {

    // Obtener todos los items de una orden específica
    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    // Obtener un detalle de orden por su ID
    Optional<OrderItem> getOrderItemById(Long id);

    // Crear un item de orden (se pasa el precio para congelarlo en la transacción)
    OrderItem createOrderItem(Long orderId, Long variantId, int quantity, double unitPrice);
}
