package com.dofus.tools.mesarchi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Document(collection = "area")
public class Area implements Serializable {

    @Id
    public String id;
    //Key: Language -> Value: languageToName in the key language
    private Map<String, String> languageToName;

    public Area() {
    }

    public Area(Map<String, String> name) {
        this.languageToName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getLanguageToName() {
        return languageToName;
    }

    public void setLanguageToName(Map<String, String> languageToName) {
        this.languageToName = languageToName;
    }
}
