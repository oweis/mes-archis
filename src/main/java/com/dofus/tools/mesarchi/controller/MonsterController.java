package com.dofus.tools.mesarchi.controller;

import com.dofus.tools.mesarchi.model.Monster;
import com.dofus.tools.mesarchi.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(value={"/","/monsters"})
public class MonsterController {

    @Autowired
    private MonsterService monsterService;

    @GetMapping("/monsters")
    public String getAll(Model model) {
        model.addAttribute("monsters", monsterService.getAllMonsters());
        return "monsters";
    }

    @GetMapping("/monsters/{id}")
    String  get(@PathVariable String id, Model model) {
        model.addAttribute("monster", monsterService.getMonster(id));
        return "monster";
    }

    @PostMapping("/monsters")
    void add(@RequestBody Monster monster) {
        monsterService.addMonster(monster);
    }

    @PostMapping("/monsters/delete/{id}")
    String delete(@PathVariable String id) {
        monsterService.deleteMonster(id);
        return "redirect:/monsters";
    }

    @PutMapping("/monsters")
    void update(@RequestBody Monster monster) {
        monsterService.updateMonster(monster);
    }
}
