package com.dofus.tools.mesarchi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Document(collection = "area")
public class Area implements Serializable {

    @Id
    public String id;
    //Key: Language -> Value: name in the key language
    private Map<String, String> name;

    public Area() {
    }

    public Area(Map<String, String> name) {
        this.name = name;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }
}
