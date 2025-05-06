package com.getthecolor.nailtonebe.controllers;


import com.getthecolor.nailtonebe.controllers.models.CreateNailPolishModel;
import com.getthecolor.nailtonebe.entities.NailPolish;
import com.getthecolor.nailtonebe.services.NailPolishService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nail-polishes")
public class NailPolishController {

    private final NailPolishService nailPolishService;

    public NailPolishController(NailPolishService nailPolishService) {
        this.nailPolishService = nailPolishService;
    }

    @GetMapping()
    public List<NailPolish> getAllNailPolishes() {
        return nailPolishService.getAll();
    }

    @PostMapping()
    public void createNailPolish(@RequestBody CreateNailPolishModel model, Authentication authentication) {
        nailPolishService.create(model, authentication);
    }

    @DeleteMapping("/{id}")
    public void deleteNailPolish(@PathVariable String id, Authentication authentication) {
        nailPolishService.deleteById(id, authentication);
    }
}
