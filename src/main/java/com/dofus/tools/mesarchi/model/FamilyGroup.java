package com.dofus.tools.mesarchi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;


@Document(collection = "familyGroup")
public class FamilyGroup implements Serializable {

    @Id
    public String id;
    //Key: Language -> Value: name in the key language
    private Map<String, String> name;

    public FamilyGroup() {
    }

    public FamilyGroup(Map<String, String> name) {
        this.name = name;
    }

    public String getId() {
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
