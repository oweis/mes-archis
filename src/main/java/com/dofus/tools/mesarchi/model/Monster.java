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
    private List<Area> areas;
    /**
     * Monster, Archimonster, Boss
     */
    private Family family;

    private List<Zone> zones;

    public List<Zone> getZones() {
        return zones;
    }

    public Monster setZones(List<Zone> zones) {
        this.zones = zones;
        return this;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public Monster setAreas(List<Area> areas) {
        this.areas = areas;
        return this;
    }

    /**
     * dofus, dofustouch
     */
    private Game game;

    public Monster() {
    }

    public Monster(String id, String picture, Map<String, String> languageToName, String levelMin, String levelMax, Game game) {
        this.id = id;
        this.picture = picture;
        this.languageToName = languageToName;
        this.levelMin = levelMin;
        this.levelMax = levelMax;
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

    public Monster setPicture(String picture) {
        this.picture = picture;
        return this;
    }
}
