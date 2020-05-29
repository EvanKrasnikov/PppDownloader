package ru.geographer29.pppdownloader.services;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geographer29.pppdownloader.entities.LinksTable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static ru.geographer29.pppdownloader.services.LinkCreatorService.Params.RINEX;

@Story("Add unit tests")
public class LinkCreatorServiceTest {

    private LinkCreatorService service = new LinkCreatorService();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Service should parse string to date")
    public void shouldParseDateString() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = LinkCreatorService.class.getDeclaredMethod("tryParse", String.class);
        Assertions.assertNotNull(method);
        method.setAccessible(true);
        String str = "2020-03-03";

        Date date = (Date) method.invoke(service, str);
        Assertions.assertNotNull(date);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Service should fail trying to parse string to date")
    public void shouldFailTryingToParseDateString() throws NoSuchMethodException {
        final Method method = LinkCreatorService.class.getDeclaredMethod("tryParse", String.class);
        Assertions.assertNotNull(method);
        method.setAccessible(true);
        String wrongDate = "yyyyMMdd";

        Date date = null;

        try {
            date = (Date) method.invoke(service, wrongDate);
        } catch (Exception e) {
            // Should throw an exception
        }

        Assertions.assertNull(date);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Service should parse date and return one link")
    public void shouldParseDateAndReturnOneLink() {
        String firstDate = "2020-03-03";
        String secondDate = "2020-03-03";
        List<LinksTable> tables = service.getLinks(firstDate, secondDate, RINEX);
        // In order to pass fix line 28 and 29 of LinkCreatorService
        // Should return 1 instead 4
        Assertions.assertEquals(1, tables.get(0).getLinks().toArray().length);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Service should parse date and return three links")
    public void shouldParseDateAndReturnThreeLinks() {
        String firstDate = "2020-03-03";
        String secondDate = "2020-03-05";
        List<LinksTable> tables = service.getLinks(firstDate, secondDate, RINEX);
        // In order to pass fix line 28 and 29 of LinkCreatorService
        // Should return 3 instead 4
        Assertions.assertEquals(3, tables.get(0).getLinks().toArray().length);
    }


}
