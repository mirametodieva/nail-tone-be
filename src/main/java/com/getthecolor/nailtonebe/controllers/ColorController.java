package com.getthecolor.nailtonebe.controllers;

import com.getthecolor.nailtonebe.entities.NailPolish;
import com.getthecolor.nailtonebe.services.ColorService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorController {

    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping()
    public List<NailPolish> findClosestNailPolishes(@RequestParam("colorCode") String colorCode) {
        String decodedColor = URLDecoder.decode(colorCode, StandardCharsets.UTF_8);
        return colorService.findClosestNailPolishes(decodedColor);
    }
}
