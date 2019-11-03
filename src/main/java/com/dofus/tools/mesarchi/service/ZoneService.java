package com.dofus.tools.mesarchi.service;

import com.dofus.tools.mesarchi.model.Zone;
import com.dofus.tools.mesarchi.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    public List<Zone> getAllZones() {
        List zones = new ArrayList();
        zoneRepository.findAll().forEach(zones::add);
        return zones;
    }

    public Zone getZone(String id) {
        return zoneRepository.findById(id).orElseGet(Zone::new);
    }

    public Zone addZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    public Zone updateZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    public void deleteZone(String id) {
        zoneRepository.deleteById(id);
    }

    public Optional<Zone> getZoneByLanguageToNamePair(String language, String name){
        return getAllZones().stream()
                .filter(type -> name.equals(type.getLanguageToName().get(language)))
                .findFirst();
    }
}
