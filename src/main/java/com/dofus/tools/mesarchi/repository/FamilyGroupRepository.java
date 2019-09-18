package com.dofus.tools.mesarchi.repository;

import com.dofus.tools.mesarchi.model.Family;
import com.dofus.tools.mesarchi.model.FamilyGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyGroupRepository extends MongoRepository<FamilyGroup, Long> {
}

