package com.getthecolor.nailtonebe.controllers.converters;

import com.getthecolor.nailtonebe.controllers.views.NailPolishView;
import com.getthecolor.nailtonebe.entities.NailPolish;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NailPolishToNailPolishViewConverter implements Converter<NailPolish, NailPolishView> {

    @Override
    public NailPolishView convert(NailPolish source) {
        var view = new NailPolishView();
        view.setId(source.getId());
        view.setName(source.getName());
        view.setCatalogNumber(source.getCatalogNumber());
        view.setSerialNumber(source.getSerialNumber());
        view.setColorCode(source.getColorCode());
        if (source.getBrand() != null) {
            view.setBrand(source.getBrand());
        }
        return view;
    }
}
