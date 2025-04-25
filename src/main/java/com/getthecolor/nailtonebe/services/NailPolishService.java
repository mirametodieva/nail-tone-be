package com.getthecolor.nailtonebe.services;

import com.getthecolor.nailtonebe.controllers.models.CreateNailPolishModel;
import com.getthecolor.nailtonebe.entities.NailPolish;
import com.getthecolor.nailtonebe.repositories.NailPolishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NailPolishService {

    private final NailPolishRepository repository;

    public NailPolishService(NailPolishRepository repository) {
        this.repository = repository;
    }

    public List<NailPolish> getAll() {
        return repository.findAll();
    }

    public void create(CreateNailPolishModel model) {
        if (repository.findByCatalogNumber(model.getCatalogNumber()).isPresent()) {
            throw new RuntimeException("A nail polish with this catalog number already exists.");
        }

        var nailPolish = new NailPolish();
        nailPolish.setId(UUID.randomUUID().toString());
        nailPolish.setName(model.getName());
        nailPolish.setCatalogNumber(model.getCatalogNumber());
        nailPolish.setSerialNumber(model.getSerialNumber());
        nailPolish.setColorCode(model.getColorCode());
        repository.save(nailPolish);
    }
}
