package ru.geographer29.pppdownloader;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Story("Add integration tests")
public class LocalIntegrationTest {

    String ip = "127.0.0.1";
    int port = 8500;
    static String checkResult = "";
    RestTemplate restTemplate = new RestTemplate();

    @BeforeAll
    static void build() throws Exception {
        Runtime.getRuntime().exec("mvn clean package -DskipTests && docker-compose build");
    }

    @BeforeEach
    public void startContainers() throws Exception {
        Runtime.getRuntime().exec("docker-compose up");
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Should test if PppDownloader is reachable")
    public void lifeCheck() throws Exception {
        String checkStatus = "passing";
        registerCheck(ConsulChecks.lifeCheck);
        TimeUnit.SECONDS.sleep(5);
        getChecksResult();
        isCheckStatusPresent(checkStatus);
        deregisterCheck("lifeCheck");
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Should test if PppDownloader is unreachable")
    public void failCheck() throws Exception {
        String checkStatus = "critical";
        registerCheck(ConsulChecks.lifeCheck);
        Runtime.getRuntime().exec("docker-compose stop ppp_downloader");
        TimeUnit.SECONDS.sleep(5);
        getChecksResult();
        isCheckStatusPresent(checkStatus);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Should test if possible to lost and the restore connection with Consul")
    public void restoreCheck() throws Exception {
        // get life check status == passing
        String checkStatus = "passing";
        registerCheck(ConsulChecks.lifeCheck);
        TimeUnit.SECONDS.sleep(5);
        getChecksResult();
        isCheckStatusPresent(checkStatus);

        // Pause the container and fail the check
        Runtime.getRuntime().exec("docker-compose stop ppp_downloader");
        Runtime.getRuntime().exec("docker-compose ps");
        TimeUnit.SECONDS.sleep(10);
        checkStatus = "critical";
        getChecksResult();
        isCheckStatusPresent(checkStatus);

        // Resume the container and pass the check
        Runtime.getRuntime().exec("docker-compose start ppp_downloader");
        TimeUnit.SECONDS.sleep(5);
        checkStatus = "passing";
        getChecksResult();
        isCheckStatusPresent(checkStatus);
        deregisterCheck("lifeCheck");
    }

    @Step("Register Consul check with REST API")
    public void registerCheck(String checkJson) {
        Allure.addAttachment("Consul check", checkJson);
        String uri = String.format("http://%s:%d/v1/agent/check/register", ip, port);
        restTemplate.put(uri, checkJson);
    }

    @Step("Deregister Consul check with REST API")
    public void deregisterCheck(String checkName) {
        String uri = String.format("http://%s:%d/v1/agent/check/deregister/%s", ip, port, checkName);
        restTemplate.put(uri, "");
    }

    @Step("Get Consul check result with REST API")
    public void getChecksResult() {
        String uri = String.format("http://%s:%d/v1/agent/checks", ip, port);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        checkResult = response.getBody();
        Assertions.assertTrue(checkResult != null && !"".equals(checkResult));
        Allure.addAttachment("Consul response = ", checkResult);
    }

    @Step("Checking if response successful")
    public void isCheckStatusPresent(String str) {
        Assertions.assertTrue(checkResult.contains(str));
    }

    @AfterEach
    public void clean() throws Exception {
        Runtime.getRuntime().exec("docker-compose down");
        TimeUnit.SECONDS.sleep(5);
    }

}
