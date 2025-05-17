package com.getthecolor.nailtonebe.services;

import com.getthecolor.nailtonebe.controllers.models.LoginModel;
import com.getthecolor.nailtonebe.controllers.models.RegistrationModel;
import com.getthecolor.nailtonebe.entities.BeautySalon;
import com.getthecolor.nailtonebe.repositories.BeautySalonRepository;
import com.getthecolor.nailtonebe.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    private final BeautySalonRepository salonRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(BeautySalonRepository salonRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.salonRepository = salonRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegistrationModel model) {
        if (salonRepository.findByEmail(model.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A salon with this email already exists.");
        }

        validatePassword(model.getPassword());

        var salon = new BeautySalon();
        salon.setId(UUID.randomUUID().toString());
        salon.setName(model.getName());
        salon.setEmail(model.getEmail());
        salon.setPassword(passwordEncoder.encode(model.getPassword()));
        salonRepository.save(salon);

        return jwtUtil.generateToken(String.valueOf(salon.getId()));
    }

    public String login(LoginModel model) {
        BeautySalon salon = salonRepository.findByEmail(model.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Invalid credentials"));

        if (!passwordEncoder.matches(model.getPassword(), salon.getPassword())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid credentials");
        }

        return jwtUtil.generateToken(String.valueOf(salon.getId()));
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 50) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password must be between 8 and 50 characters.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password must contain at least one digit.");
        }
        if (!password.matches(".*[!@#$%^&*()-+=<>?].*")) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password must contain at least one special character (!@#$%^&*()-+=<>?).");
        }
    }

}
