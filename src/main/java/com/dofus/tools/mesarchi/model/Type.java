package com.dofus.tools.mesarchi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@Document(collection = "type")
public class Type {
    @Id
    public String id;
    //Monster, ArchiMonster, DofusBoss
    private Map<String, String> languageToName;

    public Type() {

    }

    public Type(Map<String, String> languageToName) {
        this.languageToName = languageToName;
    }

    public String getId() {
        return id;
    }

    public Type setId(String id) {
        this.id = id;
        return this;
    }

    public Map<String, String> getLanguageToName() {
        return languageToName;
    }

    public Type setLanguageToName(Map<String, String> languageToName) {
        this.languageToName = languageToName;
        return this;
    }
}

