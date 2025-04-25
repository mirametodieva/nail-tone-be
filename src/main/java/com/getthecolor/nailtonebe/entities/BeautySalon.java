package com.getthecolor.nailtonebe.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "beauty_salon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeautySalon {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;
}