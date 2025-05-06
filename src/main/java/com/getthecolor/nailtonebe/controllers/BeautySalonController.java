package com.getthecolor.nailtonebe.controllers;

import com.getthecolor.nailtonebe.controllers.views.BeautySalonView;
import com.getthecolor.nailtonebe.services.BeautySalonService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beauty-salon")
public class BeautySalonController {

    private final BeautySalonService beautySalonService;
    private final ConversionService conversionService;


    public BeautySalonController(BeautySalonService beautySalonService, ConversionService conversionService) {
        this.beautySalonService = beautySalonService;
        this.conversionService = conversionService;
    }

    @GetMapping()
    public BeautySalonView getCurrentUser(Authentication authentication) {
        var salon = beautySalonService.getUserDetails(authentication);
        return conversionService.convert(salon, BeautySalonView.class);
    }

}
