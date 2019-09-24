package com.dofus.tools.mesarchi.repository;

import com.dofus.tools.mesarchi.model.Area;
import com.dofus.tools.mesarchi.model.Family;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends MongoRepository<Family, String> {
}

