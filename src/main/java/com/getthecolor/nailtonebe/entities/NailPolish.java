package com.getthecolor.nailtonebe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nail_polishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NailPolish {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private int catalogNumber;

    private String serialNumber;

    private String colorCode;

    private String brand;

}
