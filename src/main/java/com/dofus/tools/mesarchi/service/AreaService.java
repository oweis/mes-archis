package com.dofus.tools.mesarchi.service;

import com.dofus.tools.mesarchi.model.Area;
import com.dofus.tools.mesarchi.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public Optional<Area> get(String id) {
        return areaRepository.findById(id);//orElseGet(Area::new);
    }


    public List<Area> getAll() {
        List areas = new ArrayList();
        areaRepository.findAll().forEach(areas::add);
        return areas;
    }

    public Area save(Area area) {
        return areaRepository.save(area);
    }

    public Area update(Area area) {
        return areaRepository.save(area);
    }

    public void delete(Area area) {
        areaRepository.delete(area);
    }

    public Area getAreaByLanguageToNamePair(String language, String name){
        return getAll().stream()
                .filter(area -> name.equals(area.getLanguageToName().get(language)))
                .findFirst()
                .orElse(null);
    }
}
