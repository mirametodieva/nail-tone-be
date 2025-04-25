package com.getthecolor.nailtonebe.repositories;

import com.getthecolor.nailtonebe.entities.NailPolish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NailPolishRepository extends MongoRepository<NailPolish, String> {
    Optional<NailPolish> findByCatalogNumber(int catalogNumber);
}
