package com.dofus.tools.mesarchi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;


@Document(collection = "monster")
public class Monster implements Serializable {

    @Id
    public ObjectId id;
    private String picture;
    /**
     * Key: Language -> Value: name in the key language
     */
    private Map<String, String> name;
    private long levelMin;
    private long levelMax;
    // private List<Area> areas;
    /**
     *  Monster, Archimonster, Boss
     */
    private Type type;
    private Family family;
    /**
     * dofus, dofustouch
     */
    private Game game;

    public Monster() {
    }

    public Monster(ObjectId id, String picture, Map<String, String> name, long levelMin, long levelMax, Type type, Game game) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.levelMin = levelMin;
        this.levelMax = levelMax;
        this.type = type;
        this.game = game;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public long getLevelMin() {
        return levelMin;
    }

    public void setLevelMin(long levelMin) {
        this.levelMin = levelMin;
    }

    public long getLevelMax() {
        return levelMax;
    }

    public void setLevelMax(long levelMax) {
        this.levelMax = levelMax;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
