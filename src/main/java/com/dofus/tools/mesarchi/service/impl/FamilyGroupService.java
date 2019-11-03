package com.dofus.tools.mesarchi.service;

import com.dofus.tools.mesarchi.model.Area;
import com.dofus.tools.mesarchi.model.FamilyGroup;
import com.dofus.tools.mesarchi.repository.FamilyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyGroupService {
    @Autowired
    private FamilyGroupRepository familyGroupRepository;

    public List<FamilyGroup> getAllFamilyGroups() {
        List familyGroups = new ArrayList();
        familyGroupRepository.findAll().forEach(familyGroups::add);
        return familyGroups;
    }

    public FamilyGroup getFamilyGroup(String id) {
        return familyGroupRepository.findById(id).orElseGet(FamilyGroup::new);
    }

    public FamilyGroup addFamilyGroup(FamilyGroup whiskey) {
        return familyGroupRepository.save(whiskey);
    }

    public FamilyGroup updateFamilyGroup(FamilyGroup whiskey) {
        return  familyGroupRepository.save(whiskey);
    }

    public void deleteFamilyGroup(String id) {
        familyGroupRepository.deleteById(id);
    }


    public FamilyGroup getFamilyGroupByLanguageToNamePair(String language, String name){
        return getAllFamilyGroups().stream()
                .filter(familyGroup -> name.equals(familyGroup.getLanguageToName().get(language)))
                .findFirst()
                .get();
    }
}
