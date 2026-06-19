package com.uade.tpo.marketplace.services.interfaces;

import com.uade.tpo.marketplace.entity.User;
//import com.uade.tpo.marketplace.exceptions.UserDuplicateException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    // Obtener todos los usuarios (uso administrativo)
    List<User> getUsers();

    // Buscar un usuario por su ID
    Optional<User> getUserById(Long id);

    // Buscar un usuario por su email (vital para el Login)
    Optional<User> getUserByEmail(String email);

    // Crear un nuevo usuario con un rol específico
    //User createUser(String userName, String email, String password, Long rolId) throws UserDuplicateException;

    // Actualizar datos del perfil
    Optional<User> updateUser(Long id, User user);

    // Eliminar un usuario
    boolean deleteUser(Long id);
}
