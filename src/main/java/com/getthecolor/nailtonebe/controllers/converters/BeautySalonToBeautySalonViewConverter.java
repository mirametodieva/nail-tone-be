package com.getthecolor.nailtonebe.controllers.converters;

import com.getthecolor.nailtonebe.controllers.views.BeautySalonView;
import com.getthecolor.nailtonebe.controllers.views.NailPolishView;
import com.getthecolor.nailtonebe.entities.BeautySalon;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeautySalonToBeautySalonViewConverter implements Converter<BeautySalon, BeautySalonView> {
    private final NailPolishToNailPolishViewConverter conversionService;

    public BeautySalonToBeautySalonViewConverter(NailPolishToNailPolishViewConverter conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public BeautySalonView convert(BeautySalon source) {
        var view = new BeautySalonView();
        view.setName(source.getName());

        List<NailPolishView> nailPolishViews = new ArrayList<>();
        if (source.getNailPolishes() != null) {
            nailPolishViews.addAll(source.getNailPolishes()
                    .stream()
                    .map(conversionService::convert)
                    .toList());
        }
        view.setNailPolishes(nailPolishViews);

        return view;
    }
}
