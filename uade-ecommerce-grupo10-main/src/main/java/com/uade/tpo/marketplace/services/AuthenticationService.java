package com.uade.tpo.marketplace.services;

import com.uade.tpo.marketplace.config.JwtService;
import com.uade.tpo.marketplace.controllers.dto.AuthenticationRequest;
import com.uade.tpo.marketplace.controllers.dto.AuthenticationResponse;
import com.uade.tpo.marketplace.controllers.dto.RegisterRequest;
import com.uade.tpo.marketplace.entity.Rol;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.EmailAlreadyExistsException;
import com.uade.tpo.marketplace.repository.RolRepository;
import com.uade.tpo.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("El email ya está registrado.");
        }
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + request.getRolId()));
        User user = User.builder()
                .name(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
