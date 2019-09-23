package com.dofus.tools.scrapper.impl;

import com.dofus.tools.mesarchi.model.Area;
import com.dofus.tools.mesarchi.model.Family;
import com.dofus.tools.mesarchi.model.FamilyGroup;
import com.dofus.tools.mesarchi.model.Type;
import com.dofus.tools.mesarchi.repository.AreaRepository;
import com.dofus.tools.mesarchi.repository.FamilyGroupRepository;
import com.dofus.tools.mesarchi.repository.FamilyRepository;
import com.dofus.tools.mesarchi.repository.TypeRepository;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class BestiaireScrapper implements IScrapper {

    public static final String FRENCH = "fr";
    public static final String ENGLISH = "en";
    public static final String GERMAN = "de";
    public static final String SPANISH = "es";
    public static final String ITALIAN = "it";
    public static final String PORTUGUESE = "pt";
    public static final String BESTIAIRE_URL = "https://www.dofus.com/fr/mmorpg/encyclopedie/monstres";

    @Autowired
    AreaRepository areaRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    FamilyGroupRepository familyGroupRepository;
    @Autowired
    FamilyRepository familyRepository;

    Random random = new Random();

    private static final Logger LOGGER = LoggerFactory.getLogger(BestiaireScrapper.class);

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

    public static void retrieveMonsterDetailsByUrl(String url) throws IOException {
        Document docMonster = Jsoup.connect(url).get();
        Element docMonsterDetail = docMonster.getElementsByClass("ak-container ak-panel-stack ak-glue").get(0);
        String name = docMonsterDetail.getElementsByClass("ak-return-link").get(0).text();

        String picture = docMonsterDetail.getElementsByClass("ak-encyclo-detail-illu").get(0).getElementsByTag("img").get(0).attr("src");
        String types = docMonsterDetail.getElementsByClass("ak-encyclo-detail-type").get(0).getElementsByTag("span").get(0).text();
        String level = docMonsterDetail.getElementsByClass("ak-encyclo-detail-level").get(0).text();
        String zones = docMonsterDetail.getElementsByClass("ak-container ak-panel").get(3).getElementsByClass("ak-panel-content").get(0).text();

        LOGGER.info("name: {0}, picture: {1}, types: {2}, level: {3}, zones:{4}.", name, picture, types, level, zones);
    }

    @Override
    public void populateDatabase() throws IOException {
        populateDatabaseFilters();
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
            Area area = new Area(areaMap.get(row));
            LOGGER.info(area.getName().get("en"));
            areaRepository.save(area);
        }

        Table familyGroupTable = retrieveListFilters(familyGroupDataname);
        Map<String, Map<String, String>> familyGroupMap = familyGroupTable.rowMap();
        for (String row : familyGroupMap.keySet()) {
            familyGroupRepository.save(new FamilyGroup(familyGroupMap.get(row)));
        }

        Table familyTable = retrieveListFilters(familyDataname);
        Map<String, Map<String, String>> familyMap = familyTable.rowMap();
        for (String row : familyMap.keySet()) {
            familyRepository.save(new Family(familyMap.get(row)));
        }

        Table typeTable = retrieveListFilters(typeDataname);
        Map<String, Map<String, String>> typeMap = typeTable.rowMap();
        for (String row : typeMap.keySet()) {
            typeRepository.save(new Type(typeMap.get(row)));
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
        LOGGER.info("URL: {0}", url);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
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
            LOGGER.info("id: {0}, language: {1}, name:{2}.", id, language, name);
        }
        return tableByLanguage;
    }

    public void populateDatabaseMonstres() {
        //Do nothing, will be developed soon

    }
}




