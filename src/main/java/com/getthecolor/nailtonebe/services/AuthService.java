package com.getthecolor.nailtonebe.services;

import com.getthecolor.nailtonebe.controllers.models.LoginModel;
import com.getthecolor.nailtonebe.controllers.models.RegistrationModel;
import com.getthecolor.nailtonebe.entities.BeautySalon;
import com.getthecolor.nailtonebe.repositories.BeautySalonRepository;
import com.getthecolor.nailtonebe.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            throw new RuntimeException("A salon with this email already exists.");
        }

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
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(model.getPassword(), salon.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(String.valueOf(salon.getId()));
    }
}
