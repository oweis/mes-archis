package com.dofus.tools.mesarchi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Document(collection = "type")
public class Type {
    @Id
    public ObjectId id;
    //Key: Language -> Value: name in the key language
    private Map<String, String> name;//Monster, ArchiMonster, DofusBoss

    public Type(Map<String, String> name) {
        this.name = name;
    }
}

