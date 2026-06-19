package com.uade.tpo.marketplace.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.marketplace.entity.Rol;
import com.uade.tpo.marketplace.entity.RoleName;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolName(RoleName rolName);
}
