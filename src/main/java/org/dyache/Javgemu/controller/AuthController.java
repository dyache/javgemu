package org.dyache.Javgemu.controller;

import org.dyache.Javgemu.dto.LoginRequest;
import org.dyache.Javgemu.dto.RegisterRequest;
import org.dyache.Javgemu.dto.TokenResponse;
import org.dyache.Javgemu.security.AuthService;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        TokenResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<TokenResponse> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        LoginRequest log = new LoginRequest(username, password);

        TokenResponse response = authService.login(log);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/json-login")
    public ResponseEntity<TokenResponse> loginJson(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}