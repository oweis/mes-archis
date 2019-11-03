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
    private String name;
    //  private Map<String, String> languageToName;
    private int levelMin;
    private int levelMax;

    public Monster() {}

    public Monster(String id, String picture, String name, int levelMin, int levelMax) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        //this.languageToName = languageToName;
        this.levelMin = levelMin;
        this.levelMax = levelMax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Monster setName(String name) {
        this.name = name;
        return this;
    }

    public int getLevelMin() {
        return levelMin;
    }

    public Monster setLevelMin(int levelMin) {
        this.levelMin = levelMin;
        return this;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public Monster setLevelMax(int levelMax) {
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
