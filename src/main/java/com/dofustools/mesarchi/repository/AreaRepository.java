package com.dofustools.mesarchi.repository;

import com.dofustools.mesarchi.model.Area;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends MongoRepository<Area, Long> {}

