package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.Rol;
import com.uade.tpo.marketplace.entity.RoleName;
import java.util.List;
import java.util.Optional;

public interface RolService {

    // Obtener todos los roles disponibles en el sistema
    List<Rol> getRoles();

    // Obtener un rol por su ID
    Optional<Rol> getRolById(Long id);

    // Obtener un rol por su nombre enumerado (Muy usado en el registro de usuarios)
    Optional<Rol> getRolByName(RoleName roleName);

    // Crear un nuevo rol (Validando que no exista previamente)
    Rol createRol(RoleName roleName);

    // Eliminar un rol y confirmar éxito
    boolean deleteRol(Long id);
}
