package com.dofus.tools.mesarchi.service;

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

    public List getAllTypes() {
        List types = new ArrayList();
        typeRepository.findAll().forEach(types::add);
        return types;
    }

    public Type getType(String id) {
        return typeRepository.findById(id).orElseGet(Type::new);
    }

    public void addType(Type whiskey) {
        typeRepository.save(whiskey);
    }

    public void updateType(String id, Type whiskey) {
        typeRepository.save(whiskey);
    }

    public void deleteType(String id) {
        typeRepository.deleteById(id);
    }
}
