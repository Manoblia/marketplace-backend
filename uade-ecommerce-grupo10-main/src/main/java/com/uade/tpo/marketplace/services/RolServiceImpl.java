package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.entity.Rol;
import com.uade.tpo.marketplace.entity.RoleName;
import com.uade.tpo.marketplace.repository.RolRepository;
import com.uade.tpo.marketplace.services.interfaces.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> getRolById(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Optional<Rol> getRolByName(RoleName roleName) {
        return rolRepository.findByRolName(roleName);
    }

    @Override
    public Rol createRol(RoleName roleName) {
        Rol rol = new Rol();
        rol.setRolName(roleName);
        return rolRepository.save(rol);
    }

    @Override
    public boolean deleteRol(Long id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
