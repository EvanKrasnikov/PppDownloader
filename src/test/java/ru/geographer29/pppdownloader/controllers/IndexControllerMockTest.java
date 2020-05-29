package ru.geographer29.pppdownloader.controllers;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Story("Add integration tests")
public class IndexControllerMockTest {

    @Autowired
    MockMvc mock;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Service should be alive")
    public void shouldBeAlive() throws Exception {
        mock.perform(get("/"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Service should return template with links")
    public void shouldReturnTemplateWithLinks() throws Exception {
        String http = getHttp("/submit?firstDate=2020-05-05&secondDate=2020-05-07");
        String tableWithLinks = "<table class=\"table\">";
        Assertions.assertTrue(http.contains(tableWithLinks));

        Pattern pattern = Pattern.compile( 	"(<td><a href=\").*(\">Download</a></td>)");
        Matcher matcher = pattern.matcher(http);
        Assertions.assertTrue(matcher.find());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Service should return empty template")
    public void shouldReturnEmptyTemplate() throws Exception {
        String http = getHttp("/submit");
        String emptyTable = "        <div class=\"main-table\">\n            \n        </div>";
        Assertions.assertTrue(http.contains(emptyTable));
    }

    private String getHttp(String url) throws Exception {
        MvcResult result = mock
                .perform(post(url)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        return new String(result.getResponse().getContentAsByteArray());
    }

}
