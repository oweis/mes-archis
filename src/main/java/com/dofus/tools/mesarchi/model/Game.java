package com.dofus.tools.mesarchi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "family")
public class Game {

    @Id
    public ObjectId id;
    public String name;
    //Maybe we will need to use another structure if we included pictures or server names in other languages
    public List<String> servers;


    public ObjectId getId() {
        return id;
    }

    public Game setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Game setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getServers() {
        return servers;
    }

    public Game setServers(List<String> servers) {
        this.servers = servers;
        return this;
    }
}
