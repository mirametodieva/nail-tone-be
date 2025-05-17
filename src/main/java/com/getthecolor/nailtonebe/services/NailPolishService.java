package com.getthecolor.nailtonebe.services;

import com.getthecolor.nailtonebe.controllers.models.CreateNailPolishModel;
import com.getthecolor.nailtonebe.entities.NailPolish;
import com.getthecolor.nailtonebe.repositories.BeautySalonRepository;
import com.getthecolor.nailtonebe.repositories.NailPolishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NailPolishService {

    private final NailPolishRepository repository;
    private final BeautySalonRepository beautySalonRepository;

    public NailPolishService(NailPolishRepository repository, BeautySalonRepository beautySalonRepository) {
        this.repository = repository;
        this.beautySalonRepository = beautySalonRepository;
    }

    public List<NailPolish> getAll() {
        return repository.findAll();
    }

    public void create(CreateNailPolishModel model, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        var salon = beautySalonRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Salon not found"));
        if (salon.getNailPolishes() == null) {
            salon.setNailPolishes(new ArrayList<>());
        }

        if (salon.getNailPolishes().stream().anyMatch(nailPolish ->
                nailPolish.getCatalogNumber() == model.getCatalogNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A nail polish with this catalog number already exists.");
        }

        var nailPolish = new NailPolish();
        nailPolish.setId(UUID.randomUUID().toString());
        nailPolish.setName(model.getName());
        nailPolish.setCatalogNumber(model.getCatalogNumber());
        nailPolish.setSerialNumber(model.getSerialNumber());
        nailPolish.setColorCode(model.getColorCode());
        if (model.getBrand() != null) {
            nailPolish.setBrand(model.getBrand());
        }
        repository.save(nailPolish);

        salon.getNailPolishes().add(nailPolish);
        beautySalonRepository.save(salon);
    }

    public void deleteById(String id, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        var salon = beautySalonRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Salon not found"));

        List<NailPolish> updatedPolishes = salon.getNailPolishes()
                .stream()
                .filter(polish -> !id.equals(polish.getId()))
                .toList();

        salon.setNailPolishes(updatedPolishes);
        beautySalonRepository.save(salon);
        repository.deleteById(id);
    }
}
