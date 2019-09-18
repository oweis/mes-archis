package com.dofus.tools.mesarchi.controller;

import com.dofus.tools.mesarchi.model.Monster;
import com.dofus.tools.mesarchi.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(value={"/","/monsters"})
public class MonsterController {

    @Autowired
    private MonsterRepository monsterRepository;

    @GetMapping("/monsters")
    public String getAll(Model model) {
        model.addAttribute("monsters", monsterRepository.findAll());
        return "monsters";
    }

    @GetMapping("/monsters/{id}")
    String  get(@PathVariable long id, Model model) {
        model.addAttribute("monster", monsterRepository.findById(id).get());
        return "monster";
    }

    @PostMapping("/monsters")
    void add(@RequestBody Monster monster) {
        monsterRepository.save(monster);
    }

    @PostMapping("/monsters/delete/{id}")
    String delete(@PathVariable long id) {
        monsterRepository.deleteById(id);
        return "redirect:/monsters";
    }

    @PutMapping("/monsters")
    void update(@RequestBody Monster monster) {
        System.out.println();
        monsterRepository.save(monster);
    }
}
