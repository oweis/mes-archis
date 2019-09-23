package com.dofus.tools.scrapper;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface IScrapper {
    void populateDatabase() throws IOException, InterruptedException;
}