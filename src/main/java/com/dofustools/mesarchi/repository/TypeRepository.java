package com.dofustools.mesarchi.repository;

import com.dofustools.mesarchi.model.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends MongoRepository<Type, Long> {
}

