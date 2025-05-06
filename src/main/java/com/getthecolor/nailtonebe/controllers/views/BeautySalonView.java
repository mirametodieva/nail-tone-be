package com.getthecolor.nailtonebe.controllers.views;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BeautySalonView {
    String name;
    List<NailPolishView> nailPolishes;
}
