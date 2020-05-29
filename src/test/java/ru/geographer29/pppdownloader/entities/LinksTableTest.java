package ru.geographer29.pppdownloader.entities;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Story("Add unit tests")
public class LinksTableTest {
    static List<String> links;
    static LinksTable linksTable;

    @BeforeAll
    static void init() {
        links = new ArrayList<String>(){{
            add("Link1");
            add("Link2");
        }};

        linksTable = new LinksTable(links, "links");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Should create object and return non null")
    public void shouldCreateObjectAndReturnNonNull(){
        Assertions.assertNotNull(linksTable);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("LinksTable name should be equal to the created one")
    public void shouldReturnLinksTableName(){
        Assertions.assertEquals("links", linksTable.getName());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("LinksTable should contain created link")
    public void shouldContainString(){
        Assertions.assertTrue( linksTable.getLinks().contains("Link1"));
    }

}
