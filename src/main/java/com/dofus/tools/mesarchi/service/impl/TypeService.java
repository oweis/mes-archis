package com.dofus.tools.mesarchi.service;

import com.dofus.tools.mesarchi.model.FamilyGroup;
import com.dofus.tools.mesarchi.model.Type;
import com.dofus.tools.mesarchi.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAllTypes() {
        List types = new ArrayList();
        typeRepository.findAll().forEach(types::add);
        return types;
    }

    public Type getType(String id) {
        return typeRepository.findById(id).orElseGet(Type::new);
    }

    public Type addType(Type whiskey) {
        return typeRepository.save(whiskey);
    }

    public Type updateType(Type whiskey) {
        return typeRepository.save(whiskey);
    }

    public void deleteType(String id) {
        typeRepository.deleteById(id);
    }

    public Type getTypeByLanguageToNamePair(String language, String name){
        return getAllTypes().stream()
                .filter(type -> name.equals(type.getLanguageToName().get(language)))
                .findFirst()
                .get();
    }
}
