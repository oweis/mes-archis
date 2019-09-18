package com.dofus.tools.mesarchi.repository;

import com.dofus.tools.mesarchi.model.Area;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends MongoRepository<Area, Long> {
}

