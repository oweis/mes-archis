package com.dofus.tools.scrapper.impl;

import com.dofus.tools.mesarchi.model.*;
import com.dofus.tools.mesarchi.repository.*;
import com.dofus.tools.mesarchi.service.*;
import com.dofus.tools.scrapper.IScrapper;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BestiaireScrapper implements IScrapper {

    public static final String FRENCH = "fr";
    public static final String ENGLISH = "en";
    public static final String GERMAN = "de";
    public static final String SPANISH = "es";
    public static final String ITALIAN = "it";
    public static final String PORTUGUESE = "pt";
    public static final String DOFUS_URL = "https://www.dofus.com";
    public static final String BESTIAIRE_URL = "https://www.dofus.com/fr/mmorpg/encyclopedie/monstres";
    // This user agent is for if the server wants real humans to visit
    public static final String USER_AGENT_MOZILLA = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";

    private static final Logger LOGGER = LoggerFactory.getLogger(BestiaireScrapper.class);


    @Autowired
    AreaService areaService;
    @Autowired
    TypeService typeService;
    @Autowired
    FamilyGroupService familyGroupService;
    @Autowired
    FamilyService familyService;
    @Autowired
    MonsterService monsterService;


    Random random = new Random();

    private static String getLocalizedURL(String url, String sourceLanguage, String targetLanguage) {

        class UrlParts {
            String language;
            String encyclopedia;
            String monster;

            public UrlParts(String language, String encyclopedia, String monster) {
                this.language = language;
                this.encyclopedia = encyclopedia;
                this.monster = monster;
            }
        }

        Map<String, UrlParts> encyclopediaMap = new HashMap<>();
        encyclopediaMap.put("fr", new UrlParts("/fr/", "/encyclopedie/", "/monstres"));
        encyclopediaMap.put("en", new UrlParts("/en/", "/encyclopedia/", "/monsters"));
        encyclopediaMap.put("de", new UrlParts("/de/", "/leitfaden/", "/monster"));
        encyclopediaMap.put("es", new UrlParts("/es/", "/enciclopedia/", "/monstruos"));
        encyclopediaMap.put("it", new UrlParts("/it/", "/enciclopedia/", "/mostri"));
        encyclopediaMap.put("pt", new UrlParts("/pt/", "/enciclopedia/", "/monstros"));

        return url.replace(encyclopediaMap.get(sourceLanguage).language, encyclopediaMap.get(targetLanguage).language)
                .replace(encyclopediaMap.get(sourceLanguage).encyclopedia, encyclopediaMap.get(targetLanguage).encyclopedia)
                .replace(encyclopediaMap.get(sourceLanguage).monster, encyclopediaMap.get(targetLanguage).monster);
    }

    public static void downloadImage(String search, String path) throws IOException {

        // This will get input data from the server
        InputStream inputStream = null;

        // This will read the data from the server;
        OutputStream outputStream = null;

        try {
            // This will open a socket from client to server
            URL url = new URL(search);


            // This socket type will allow to set user_agent
            URLConnection con = url.openConnection();

            // Setting the user agent
            con.setRequestProperty("User-Agent", USER_AGENT_MOZILLA);

            // Requesting input data from server
            inputStream = con.getInputStream();

            // Open local file writer
            outputStream = new FileOutputStream(path);

            // Limiting byte written to file per loop
            byte[] buffer = new byte[2048];

            // Increments file size
            int length;

            // Looping until server finishes
            while ((length = inputStream.read(buffer)) != -1) {
                // Writing data
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception ex) {
            LOGGER.debug("ERROR ...");
        }

        // closing used resources
        // The computer will not be able to use the image
        // This is a must

        outputStream.close();
        inputStream.close();
    }

    @Override
    public void populateDatabase() throws IOException, InterruptedException {
        populateDatabaseFilters();
        //populateDatabaseMonstres();
    }

    public void populateDatabaseFilters() throws IOException {

        final String areaDataname = "item_monster_zones";
        final String familyGroupDataname = "item_monster_superrace_id";
        final String familyDataname = "item_monster_category";
        final String typeDataname = "item_monster_type";

        Table areaTable = retrieveListFilters(areaDataname);
        Map<String, Map<String, String>> areaMap = areaTable.rowMap();
        for (String row : areaMap.keySet()) {
            areaService.addArea(new Area(areaMap.get(row)));
        }

        Table familyGroupTable = retrieveListFilters(familyGroupDataname);
        Map<String, Map<String, String>> familyGroupMap = familyGroupTable.rowMap();
        for (String row : familyGroupMap.keySet()) {
            familyGroupService.addFamilyGroup(new FamilyGroup(familyGroupMap.get(row)));
        }

        Table familyTable = retrieveListFilters(familyDataname);
        Map<String, Map<String, String>> familyMap = familyTable.rowMap();
        for (String row : familyMap.keySet()) {
            familyService.addFamily(new Family(familyMap.get(row)));
        }

        Table typeTable = retrieveListFilters(typeDataname);
        Map<String, Map<String, String>> typeMap = typeTable.rowMap();
        for (String row : typeMap.keySet()) {
            typeService.addType(new Type(typeMap.get(row)));
        }
    }

    public Table retrieveListFilters(String dataname) throws IOException {
        Table table = HashBasedTable.create();

        table.putAll(retrieveListFiltersByLanguage(FRENCH, dataname));
        table.putAll(retrieveListFiltersByLanguage(ENGLISH, dataname));
        table.putAll(retrieveListFiltersByLanguage(GERMAN, dataname));
        table.putAll(retrieveListFiltersByLanguage(SPANISH, dataname));
        table.putAll(retrieveListFiltersByLanguage(PORTUGUESE, dataname));
        table.putAll(retrieveListFiltersByLanguage(ITALIAN, dataname));

        return table;
    }

    public Table retrieveListFiltersByLanguage(String language, String dataname) throws IOException {
        String url = getLocalizedURL(BESTIAIRE_URL, FRENCH, language);
        LOGGER.info("URL: {}", url);
        Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT_MOZILLA)
                .timeout(random.nextInt(5000) + 10000)
                .get();
        Element familyGroupDivElement = doc.getElementsByAttributeValue("data-name", dataname).get(0);
        Elements familyGroupListElements = familyGroupDivElement.getElementsByClass("ak-list-filters-check ak-searchable-list");
        Element familyGroupListElement = familyGroupListElements != null && !familyGroupListElements.isEmpty() ? familyGroupListElements.get(0) : familyGroupDivElement.getElementsByClass("ak-list-filters-check").get(0);


        Elements familyGroups = familyGroupListElement.getElementsByTag("li");
        Table<String, String, String> tableByLanguage = HashBasedTable.create();
        for (Element area : familyGroups) {
            String id = area.getElementsByTag("input").get(0).attr("value");
            String name = area.text();
            tableByLanguage.put(id, language, name);
            LOGGER.info("id: {}, language: {}, name:{}.", id, language, name);
        }
        return tableByLanguage;
    }

    public void populateDatabaseMonstres() throws IOException {
        //Do nothing, will be developed soon
        int bestiairePagesNumber = 19;

        for (int i = 1; i <= bestiairePagesNumber; i++) {
            Document doc = Jsoup.connect(BESTIAIRE_URL)
                    .data("size", "96")
                    .data("display", "table")
                    .data("page", String.valueOf(i))
                    .userAgent(USER_AGENT_MOZILLA)
                    .timeout(random.nextInt(5000) + 10000)
                    .get();

            Elements monstersTables = doc.getElementsByClass("ak-table ak-responsivetable");

            for (Element monsterTable : monstersTables) {
                Elements monsters = monsterTable.getElementsByClass("ak-bg-odd");
                monsters.addAll(monsterTable.getElementsByClass("ak-bg-even"));

                for (Element monsterElement : monsters) {
                    String monsterPath = monsterElement.getElementsByTag("td").get(1).getElementsByTag("span").get(0).getElementsByTag("a").get(0).attr("href");
                    Monster monster = scrapMonster(DOFUS_URL + monsterPath);
                    monsterService.addMonster(monster);
                }
            }
        }
    }

    public Monster scrapMonster(String monsterUrl) throws IOException {
        //set french name and the other monster information
        Monster monster = initializeMonster(monsterUrl);
        //set name in other languages
        monster.getName().put(ENGLISH, scrapMonsterNameByLanguage(monsterUrl, ENGLISH));
        monster.getName().put(SPANISH, scrapMonsterNameByLanguage(monsterUrl, SPANISH));
        monster.getName().put(GERMAN, scrapMonsterNameByLanguage(monsterUrl, GERMAN));
        monster.getName().put(PORTUGUESE, scrapMonsterNameByLanguage(monsterUrl, PORTUGUESE));
        monster.getName().put(ITALIAN, scrapMonsterNameByLanguage(monsterUrl, ITALIAN));

        return monster;
    }

    public Monster initializeMonster(String urlMonster) throws IOException {

        Document docMonster = Jsoup.connect(urlMonster)
                .userAgent(USER_AGENT_MOZILLA)
                .timeout(random.nextInt(5000) + 10000)
                .get();
        Element docMonsterDetail = docMonster.getElementsByClass("ak-container ak-panel-stack ak-glue").get(0);
        String name = docMonsterDetail.getElementsByClass("ak-return-link").get(0).text();

        String picture = docMonsterDetail.getElementsByClass("ak-encyclo-detail-illu").get(0).getElementsByTag("img").get(0).attr("data-src");
        String types = docMonsterDetail.getElementsByClass("ak-encyclo-detail-type").get(0).getElementsByTag("span").get(0).text();
        String level = docMonsterDetail.getElementsByClass("ak-encyclo-detail-level").get(0).text();


        String areas = docMonsterDetail.getElementsByClass("ak-container ak-panel").get(3).getElementsByClass("ak-panel-content").get(0).text();
        if (areas.contains("Spell animations")) {
            areas = "";
        }

        List<String> areasList = Arrays.stream(areas.split(","))
                .map(areasElement -> areasElement.trim())
                .collect(Collectors.toList());


        String levelMin = "";
        String levelMax = "";
        String[] levelList = level.replace("Level:", "").replace("to", ",").split(",");
        if (levelList.length == 2) {
            levelMin = levelList[0];
            levelMax = levelList[1];
        } else {
            levelMin = levelList[0];
            levelMax = levelList[0];
        }


//        downloadImage(picture, "C:/Workspace/Java/Picture/"+name+".jpg");
        System.out.println("Types: " + types + ", levelMin: " + levelMin + ", levelMax: " + levelMax + " areas: " + areas + ".");
        //LOGGER.info("name: {}, picture: {}, types: {}, level: {}, areas: {}.", name, picture, types, level, areas);
        Monster monster = new Monster();
        monster.getName().put(ENGLISH, name);
        monster.setLevelMax(levelMax);
        monster.setLevelMax(levelMin);
        monster.setPicture(picture);
        //monster.setGame();


        return new Monster();
    }

    public String scrapMonsterNameByLanguage(String monsterUrl, String language) throws IOException {
        Document docMonster = Jsoup.connect(getLocalizedURL(monsterUrl, FRENCH, PORTUGUESE))
                .userAgent(USER_AGENT_MOZILLA)
                .timeout(random.nextInt(5000) + 10000)
                .get();
        Element docMonsterDetail = docMonster.getElementsByClass("ak-container ak-panel-stack ak-glue").get(0);
        return docMonsterDetail.getElementsByClass("ak-return-link").get(0).text();

    }
}




