package com.getthecolor.nailtonebe.services;

import com.getthecolor.nailtonebe.entities.NailPolish;
import com.getthecolor.nailtonebe.utils.ColorUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorService {

    private final NailPolishService nailPolishService;

    public ColorService(NailPolishService nailPolishService) {
        this.nailPolishService = nailPolishService;
    }

    public List<NailPolish> findClosestNailPolishes(String colorCode) {
        int[] targetRgb = ColorUtils.hexToRgb(colorCode);

        return nailPolishService.getAll().stream()
                .sorted(Comparator.comparingDouble(polish -> {
                    int[] colorRgb = ColorUtils.hexToRgb(polish.getColorCode());
                    return ColorUtils.colorDistance(targetRgb, colorRgb);
                }))
                .limit(5)
                .collect(Collectors.toList());
    }

}
