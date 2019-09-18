package com.dofus.tools.mesarchi.repository;

import com.dofus.tools.mesarchi.model.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends MongoRepository<Monster, Long> {
}

