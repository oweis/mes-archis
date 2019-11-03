package com.dofus.tools.mesarchi.service;

import com.dofus.tools.mesarchi.model.Area;
import com.dofus.tools.mesarchi.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAllAreas() {
        List areas = new ArrayList();
        areaRepository.findAll().forEach(areas::add);
        return areas;
    }

    public Area getArea(String id) {
        return areaRepository.findById(id).orElseGet(Area::new);
    }

    public Area addArea(Area whiskey) {
        return areaRepository.save(whiskey);
    }

    public Area updateArea(Area whiskey) {
        return areaRepository.save(whiskey);
    }

    public void deleteArea(String id) {
        areaRepository.deleteById(id);
    }

    public Area getAreaByLanguageToNamePair(String language, String name){
        return getAllAreas().stream()
                .filter(area -> name.equals(area.getLanguageToName().get(language)))
                .findFirst()
                .orElse(null);
    }
}
