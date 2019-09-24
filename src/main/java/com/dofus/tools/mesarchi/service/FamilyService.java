package com.dofus.tools.mesarchi.service;

import com.dofus.tools.mesarchi.model.Family;
import com.dofus.tools.mesarchi.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyService {
    @Autowired
    private FamilyRepository familyRepository;

    public List getAllFamilys() {
        List familys = new ArrayList();
        familyRepository.findAll().forEach(familys::add);
        return familys;
    }

    public Family getFamily(String id) {
        return familyRepository.findById(id).orElseGet(Family::new);
    }

    public Family addFamily(Family whiskey) {
        return familyRepository.save(whiskey);
    }

    public Family updateFamily(Family whiskey) {
        return familyRepository.save(whiskey);
    }

    public void deleteFamily(String id) {
        familyRepository.deleteById(id);
    }
}
