package com.dofus.tools.mesarchi.controller;

import com.dofus.tools.mesarchi.model.Area;
import com.dofus.tools.mesarchi.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AreaController {

    @Autowired
    private AreaService areaService;

    @PostMapping("/areas")
    Area add(@RequestBody Area area) {
        return areaService.save(area);
    }
}

