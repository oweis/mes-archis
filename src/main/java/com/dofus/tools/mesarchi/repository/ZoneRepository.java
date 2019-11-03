package com.dofus.tools.mesarchi.repository;


import com.dofus.tools.mesarchi.model.Zone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends MongoRepository<Zone, String> {
}
