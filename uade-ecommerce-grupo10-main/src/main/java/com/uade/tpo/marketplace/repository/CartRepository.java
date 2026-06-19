package com.uade.tpo.marketplace.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.marketplace.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Para vincular el carrito a un usuario específico
    Optional<Cart> findByUser_UserId(Long userId);
}
