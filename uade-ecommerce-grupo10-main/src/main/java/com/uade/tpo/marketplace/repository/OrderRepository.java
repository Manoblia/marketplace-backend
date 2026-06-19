package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.marketplace.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Para ver el historial de compras de un usuario, de más reciente a más antiguo
    List<Order> findByUser_UserIdOrderByOrderDateDesc(Long userId);
}
