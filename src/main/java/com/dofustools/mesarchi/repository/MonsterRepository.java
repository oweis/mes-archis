package com.dofustools.mesarchi.repository;

import com.dofustools.mesarchi.model.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends MongoRepository<Monster, Long> {
}

