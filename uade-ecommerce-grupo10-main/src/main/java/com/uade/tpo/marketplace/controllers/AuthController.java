package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.controllers.dto.AuthenticationRequest;
import com.uade.tpo.marketplace.controllers.dto.AuthenticationResponse;
import com.uade.tpo.marketplace.controllers.dto.RegisterRequest;
import com.uade.tpo.marketplace.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
