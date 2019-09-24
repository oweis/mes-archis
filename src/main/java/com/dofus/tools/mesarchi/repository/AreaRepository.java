package com.dofus.tools.mesarchi.repository;

import com.dofus.tools.mesarchi.model.Area;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface AreaRepository extends MongoRepository<Area, String> {
    Area findByName(Map<String, String> name);
}

