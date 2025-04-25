package com.getthecolor.nailtonebe.controllers;

import com.getthecolor.nailtonebe.controllers.models.LoginModel;
import com.getthecolor.nailtonebe.controllers.models.RegistrationModel;
import com.getthecolor.nailtonebe.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody RegistrationModel model) {
        String token = authService.register(model);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel model) {
        String token = authService.login(model);
        return ResponseEntity.ok(token);
    }
}
