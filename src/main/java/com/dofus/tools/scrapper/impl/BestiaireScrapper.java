package com.dofus.tools.scrapper.impl;

import com.dofus.tools.mesarchi.model.*;
import com.dofus.tools.mesarchi.service.MonsterServiceImpl;
import com.dofus.tools.scrapper.IScrapper;
import com.dofus.tools.scrapper.common.Constants;
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

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BestiaireScrapper implements IScrapper {
/*
    public static final String FRENCH = "fr";
    public static final String ENGLISH = "en";
    public static final String GERMAN = "de";
    public static final String SPANISH = "es";
    public static final String ITALIAN = "it";
    public static final String PORTUGUESE = "pt";
    public static final String DOFUS_URL = "https://www.dofus.com";
    public static final String BESTIAIRE_URL = "https://www.dofus.com/fr/mmorpg/encyclopedie/monstres";
    // This user agent is for if the server wants real humans to visit

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
    MonsterServiceImpl monsterService;
    @Autowired
    ZoneService zoneService;

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

    @Override
    public void populateDatabase() throws IOException, InterruptedException {
    //    populateDatabaseFilters();
          populateDatabaseMonstres();
    }

    public void populateDatabaseFilters() throws IOException {

        final String areaDataname = "item_monster_zones";
        final String familyGroupDataname = "item_monster_superrace_id";
        final String familyDataname = "item_monster_category";
        final String typeDataname = "item_monster_type";

        Table areaTable = retrieveListFilters(areaDataname);
        Map<String, Map<String, String>> areaMap = areaTable.rowMap();
        for (String row : areaMap.keySet()) {
            areaService.save(new Area(areaMap.get(row)));
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
                .userAgent(Constants.USER_AGENT_MOZILLA)
                .timeout(random.nextInt(10000) + 10000)
                .get();
        Element filtersDivElement = doc.getElementsByAttributeValue("data-name", dataname).get(0);
        Elements filtersListElements = filtersDivElement.getElementsByClass("ak-list-filters-check ak-searchable-list");
        Element filtersListElement = filtersListElements != null && !filtersListElements.isEmpty() ? filtersListElements.get(0) : filtersDivElement.getElementsByClass("ak-list-filters-check").get(0);


        Elements filters = filtersListElement.getElementsByTag("li");
        Table<String, String, String> filterNameTable = HashBasedTable.create();
        for (Element filter : filters) {
            String id = filter.getElementsByTag("input").get(0).attr("value");
            String name = filter.text();
            filterNameTable.put(id, language, name);
            LOGGER.info("id: {}, language: {}, name:{}.", id, language, name);
        }
        return filterNameTable;
    }

    public void populateDatabaseMonstres() throws IOException {
        int bestiairePagesNumber = 19;

        for (int i = 1; i <= bestiairePagesNumber; i++) {
            Document doc = Jsoup.connect(BESTIAIRE_URL)
                    .data("size", "96")
                    .data("display", "table")
                    .data("page", String.valueOf(i))
                    .userAgent(Constants.USER_AGENT_MOZILLA)
                    .timeout(random.nextInt(10000) + 10000)
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
        monster.getLanguageToName().put(ENGLISH, scrapMonsterNameByLanguage(monsterUrl, ENGLISH));
        monster.getLanguageToName().put(SPANISH, scrapMonsterNameByLanguage(monsterUrl, SPANISH));
        monster.getLanguageToName().put(GERMAN, scrapMonsterNameByLanguage(monsterUrl, GERMAN));
        monster.getLanguageToName().put(PORTUGUESE, scrapMonsterNameByLanguage(monsterUrl, PORTUGUESE));
        monster.getLanguageToName().put(ITALIAN, scrapMonsterNameByLanguage(monsterUrl, ITALIAN));

        return monster;
    }

    public Monster initializeMonster(String urlMonster) throws IOException {
        Monster monster = new Monster();

        Document docMonster = Jsoup.connect(urlMonster)
                .userAgent(Constants.USER_AGENT_MOZILLA)
                .timeout(random.nextInt(10000) + 10000)
                .get();
        Element docMonsterDetail = docMonster.getElementsByClass("ak-container ak-panel-stack ak-glue").get(0);


        String name = docMonsterDetail.getElementsByClass("ak-return-link").get(0).text();
        Map<String, String> languageToName = new HashMap<>();
        languageToName.put(FRENCH, name);
        monster.setLanguageToName(languageToName);


        String picture = docMonsterDetail.getElementsByClass("ak-encyclo-detail-illu").get(0).getElementsByTag("img").get(0).attr("data-src");
        monster.setPicture(picture);



//      String familyNameFrench = docMonsterDetail.getElementsByClass("ak-encyclo-detail-type").get(0).getElementsByTag("span").get(0).text();
//      System.out.println("Family: " + familyNameFrench);
//      Family family = familyService.getFamilyByLanguageToNamePair(FRENCH, familyNameFrench);
//      monster.setFamily(family);



        String zones = docMonsterDetail.getElementsByClass("ak-container ak-panel").get(3).getElementsByClass("ak-panel-content").get(0).text();
        zones = zones.contains("Spell animations") ?  "" : zones;
        List<String> zoneNameList = Arrays.stream(zones.split(","))
                .map(zoneElement -> zoneElement.trim())
                .collect(Collectors.toList());

        List<Zone> zoneList = new ArrayList<>();
        for (String zoneName:zoneNameList){
           Optional<Zone> zone = zoneService.getZoneByLanguageToNamePair(FRENCH, zoneName);
           Zone newZone = null;
           if(!zone.isPresent()){
               Map<String, String> languageToNameZone = new HashMap<>();
               languageToNameZone.put(FRENCH, name);
               newZone = new Zone(languageToNameZone);
               zoneService.addZone(newZone);
           }
           zoneList.add(newZone);
        }
        monster.setZones(zoneList);


        String level = docMonsterDetail.getElementsByClass("ak-encyclo-detail-level").get(0).text();
        String levelMin;
        String levelMax;
        String[] levelList = level.replace("Niveau :", "").replace("à", ",").split(",");
        if (levelList.length == 2) {
            levelMin = levelList[0].trim();
            levelMax = levelList[1].trim();
        } else {
            levelMin = levelList[0].trim();
            levelMax = levelList[0].trim();
        }
        monster.setLevelMax(levelMax);
        monster.setLevelMin(levelMin);

        //DownloadHelper.downloadImage(picture, "C:/Workspace/Java/Picture/"+name+".jpg");
        //System.out.println("Types: " + typeNameFrench + ", levelMin: " + levelMin + ", levelMax: " + levelMax + " areas: " + areas + ".");
        //LOGGER.info("name: {}, picture: {}, types: {}, level: {}, areas: {}.", name, picture, types, level, areas);
        //monster.setGame();

        return monster;
    }

    public String scrapMonsterNameByLanguage(String monsterUrl, String language) throws IOException {
        Document docMonster = Jsoup.connect(getLocalizedURL(monsterUrl, FRENCH, language))
                .userAgent(Constants.USER_AGENT_MOZILLA)
                .timeout(random.nextInt(10000) + 10000)
                .get();
        Element docMonsterDetail = docMonster.getElementsByClass("ak-container ak-panel-stack ak-glue").get(0);
        return docMonsterDetail.getElementsByClass("ak-return-link").get(0).text();
    }

    */
}