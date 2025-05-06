package com.getthecolor.nailtonebe.services;

import com.getthecolor.nailtonebe.entities.BeautySalon;
import com.getthecolor.nailtonebe.repositories.BeautySalonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class BeautySalonService {

    private final BeautySalonRepository salonRepository;

    public BeautySalonService(BeautySalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    public BeautySalon getUserDetails(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return salonRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Invalid id"));
    }

}
