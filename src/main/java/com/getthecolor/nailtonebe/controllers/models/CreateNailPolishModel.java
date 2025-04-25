package com.getthecolor.nailtonebe.controllers.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNailPolishModel {

    private String name;

    private int catalogNumber;

    private String serialNumber;

    private String colorCode;
}
