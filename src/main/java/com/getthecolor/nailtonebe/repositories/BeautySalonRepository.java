package com.getthecolor.nailtonebe.repositories;

import com.getthecolor.nailtonebe.entities.BeautySalon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BeautySalonRepository extends MongoRepository<BeautySalon, String> {

    Optional<BeautySalon> findByEmail(String email);

}
