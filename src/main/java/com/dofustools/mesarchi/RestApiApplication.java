package com.dofustools.mesarchi;

import com.dofustools.mesarchi.model.Type;
import com.dofustools.mesarchi.repository.TypeRepository;
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
    CommandLineRunner init(TypeRepository typeRepository) {

        return args -> {
            List<Type> types =  new ArrayList<>();
            Map<String, String> names1 = new HashMap<>();
            names1.put("en", "the bu3u");
            names1.put("fr", "le bu3u");
            names1.put("es", "bu3ruz");
            types.add(new Type(names1));
            typeRepository.saveAll(types);

/*          List<Monster> list = new ArrayList<>();
            list.add(new Monster("Aboudbra le Porteur", 35, 47,"https://s.ankama.com/www/static.ankama.com/dofus/ng/img/../../../dofus/www/game/monsters/200/5382.w52h52.png"));
            list.add(new Monster("Abrakanette l'Encapsul", 40, 43, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/839.w52h52.png"));
            list.add(new Monster("Abrakildas le Vénérable", 9, 12, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/384.w52h52.png"));
            list.add(new Monster("Abrakine le Sombre", 17, 26, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/384.w52h52.png"));
            list.add(new Monster("Abraklette le Fondant", 15, 47, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/384.w52h52.png"));
            list.add(new Monster("Abrakroc l'édenté", 35, 37, "https://s.ankama.com/www/static.ankama.com/dofus/www/game/monsters/200/446.w52h52.png"));
            monsterRepository.saveAll(list);
*/
        };
    }


}