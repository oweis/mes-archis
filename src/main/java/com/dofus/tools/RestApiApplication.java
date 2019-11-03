package com.dofus.tools;

import com.dofus.tools.scrapper.IScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootApplication
public class RestApiApplication {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    IScrapper scrapper;

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            //To be removed later:  as we still populating the db, we will need to drop it each time
           // mongoTemplate.getDb().drop();
            scrapper.populateDatabase();
        };
    }
}