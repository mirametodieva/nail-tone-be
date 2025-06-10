package com.getthecolor.nailtonebe.controllers;

import com.getthecolor.nailtonebe.controllers.models.LoginModel;
import com.getthecolor.nailtonebe.controllers.models.RegistrationModel;
import com.getthecolor.nailtonebe.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegistrationModel model) {
        String token = authService.register(model);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginModel model) {
        String token = authService.login(model);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
