package com.dofus.tools.mesarchi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
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
    // private List<Area> areas;
    /**
     * Monster, Archimonster, Boss
     */
    private Type type;
    private Family family;
    /**
     * dofus, dofustouch
     */
    private Game game;

    public Monster() {
    }

    public Monster(String id, String picture, Map<String, String> languageToName, String levelMin, String levelMax, Type type, Game game) {
        this.id = id;
        this.picture = picture;
        this.languageToName = languageToName;
        this.levelMin = levelMin;
        this.levelMax = levelMax;
        this.type = type;
        this.game = game;
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

    public void setName(String key, String value) {
        languageToName.put(key, value);
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

    public Type getType() {
        return type;
    }

    public Monster setType(Type type) {
        this.type = type;
        return this;
    }

    public Family getFamily() {
        return family;
    }

    public Monster setFamily(Family family) {
        this.family = family;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public Monster setGame(Game game) {
        this.game = game;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
