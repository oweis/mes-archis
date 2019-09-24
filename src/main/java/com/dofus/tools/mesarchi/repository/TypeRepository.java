package com.dofus.tools.mesarchi.repository;

import com.dofus.tools.mesarchi.model.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends MongoRepository<Type, String> {
}

