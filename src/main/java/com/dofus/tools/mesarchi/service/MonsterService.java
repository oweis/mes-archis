package com.dofus.tools.mesarchi.service;

import com.dofus.tools.mesarchi.model.Monster;
import com.dofus.tools.mesarchi.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonsterService {
    @Autowired
    private MonsterRepository monsterRepository;

    public List getAllMonsters() {
        List monsters = new ArrayList();
        monsterRepository.findAll().forEach(monsters::add);
        return monsters;
    }

    public Monster getMonster(String id) {
        return monsterRepository.findById(id).orElseGet(Monster::new);
    }

    public Monster addMonster(Monster whiskey) {
       return monsterRepository.save(whiskey);
    }

    public Monster updateMonster(Monster whiskey) {
        return monsterRepository.save(whiskey);
    }

    public void deleteMonster(String id) {
        monsterRepository.deleteById(id);
    }
}
