package com.dofus.tools.mesarchi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Document(collection = "type")
public class Type {
    @Id
    public String id;
    //Key: Language -> Value: name in the key language
    private Map<String, String> name;//Monster, ArchiMonster, DofusBoss

    public Type(){

    }

    public Type(Map<String, String> name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Type setId(String id) {
        this.id = id;
        return this;
    }

    public Map<String, String> getName() {
        return name;
    }

    public Type setName(Map<String, String> name) {
        this.name = name;
        return this;
    }
}

