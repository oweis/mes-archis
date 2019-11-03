package com.dofus.tools.mesarchi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Document(collection = "monster")
public class Monster implements Serializable {

    @Id
    public String id;
    private String picture;
    /**
     * Key: Language -> Value: languageToName in the key language
     */
    private Map<String, String> languageToName;
    private String levelMin;
    private String levelMax;
    /**
     * Monster, Archimonster, Boss
     */


    public Monster() {
    }

    public Monster(String id, String picture, Map<String, String> languageToName, String levelMin, String levelMax) {
        this.id = id;
        this.picture = picture;
        this.languageToName = languageToName;
        this.levelMin = levelMin;
        this.levelMax = levelMax;
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

    public Monster setLanguageToName(Map<String, String> languageToName) {
        this.languageToName = languageToName;
        return this;
    }

    public String getLevelMin() {
        return levelMin;
    }

    public Monster setLevelMin(String levelMin) {
        this.levelMin = levelMin;
        return this;
    }

    public String getLevelMax() {
        return levelMax;
    }

    public Monster setLevelMax(String levelMax) {
        this.levelMax = levelMax;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public Monster setPicture(String picture) {
        this.picture = picture;
        return this;
    }
}
