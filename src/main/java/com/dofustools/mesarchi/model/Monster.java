package com.dofustools.mesarchi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "monster")
public class Monster implements Serializable {

    @Id
    public ObjectId id;
    private String picture;
    private String name;
    private long levelMin;
    private long levelMax;
    // private List<Area> areas;
    // private Type type;

    public Monster() {
    }

    public Monster(String name, long levelMin, long levelMax, String picture) {
        this.name = name;
        this.levelMin = levelMin;
        this.levelMax = levelMax;
        this.picture = picture;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
