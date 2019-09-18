package com.dofus.tools.mesarchi;

import com.dofus.tools.mesarchi.model.Monster;
import com.dofus.tools.mesarchi.repository.MonsterRepository;
import com.dofus.tools.mesarchi.repository.TypeRepository;
import com.dofus.tools.mesarchi.model.Type;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }


    @Bean
    CommandLineRunner init(TypeRepository typeRepository, MonsterRepository monsterRepository) {

        return args -> {
            //  populateType(typeRepository);
            //  populateMonster(monsterRepository);

        };
    }

//    public void populateType(TypeRepository typeRepository){
//        List<Type> types =  new ArrayList<>();
//        Map<String, String> names1 = new HashMap<>();
//        names1.put("en", "the bu3u");
//        names1.put("fr", "le bu3u");
//        names1.put("es", "bu3ruz");
//        types.add(new Type(names1));
//        typeRepository.saveAll(types);
//    }

//
//    public void populateMonster(MonsterRepository monsterRepository){
//        List<Monster> list = new ArrayList<>();
//        Map<String, String> monsterNames1 = new HashMap<>();
//        monsterNames1.put("en", "Aboudbra le Porteur EN");
//        monsterNames1.put("fr", "Aboudbra le Porteur FR");
//        monsterNames1.put("es", "Aboudbra le Porteur ES");
//
//        Map<String, String> monsterNames2 = new HashMap<>();
//        monsterNames2.put("en", "Abrakildas le Vénérable EN");
//        monsterNames2.put("fr", "Abrakildas le Vénérable FR");
//        monsterNames2.put("es", "Abrakildas le Vénérable ES");
//
//        Map<String, String> monsterNames3 = new HashMap<>();
//        monsterNames3.put("en", "Abrakine le Sombre EN");
//        monsterNames3.put("fr", "Abrakine le Sombre FR");
//        monsterNames3.put("es", "Abrakine le Sombre ES");
//
//        Map<String, String> monsterNames4 = new HashMap<>();
//        monsterNames4.put("en", "Abrakanette l'Encapsul EN");
//        monsterNames4.put("fr", "Abrakanette l'Encapsul FR");
//        monsterNames4.put("es", "Abrakanette l'Encapsul ES");
//
//        Map<String, String> monsterNames5 = new HashMap<>();
//        monsterNames5.put("en", "Abraklette le Fondant EN");
//        monsterNames5.put("fr", "Abraklette le Fondant FR");
//        monsterNames5.put("es", "Abraklette le Fondant ES");
//
//        Map<String, String> monsterNames6 = new HashMap<>();
//        monsterNames6.put("en", "Abrakroc l'édenté EN");
//        monsterNames6.put("fr", "Abrakroc l'édenté FR");
//        monsterNames6.put("es", "Abrakroc l'édenté ES");
//
//        list.add(new Monster(monsterNames1, 35, 47,"https://s.ankama.com/www/static.ankama.com/dofus/ng/img/../../../dofus/www/game/monsters/200/5382.w52h52.png"));
//        list.add(new Monster(monsterNames2, 40, 43, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/839.w52h52.png"));
//        list.add(new Monster(monsterNames3, 9, 12, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/384.w52h52.png"));
//        list.add(new Monster(monsterNames4, 17, 26, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/384.w52h52.png"));
//        list.add(new Monster(monsterNames5, 15, 47, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/384.w52h52.png"));
//        list.add(new Monster(monsterNames6, 35, 37, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/446.w52h52.png"));
//        monsterRepository.saveAll(list);
//
//    }
//


}