package com.uade.tpo.marketplace.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.marketplace.entity.RoleName;
import com.uade.tpo.marketplace.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Validaciones y búsquedas de UserService
    Optional<User> findByEmail(String email);
    List<User> findByRol_RolName(RoleName rolName);
}
