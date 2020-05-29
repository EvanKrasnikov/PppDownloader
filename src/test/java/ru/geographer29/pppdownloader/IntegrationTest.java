package ru.geographer29.pppdownloader;

import io.qameta.allure.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import ru.geographer29.pppdownloader.containers.ConsulContainer;
import ru.geographer29.pppdownloader.containers.PppDownloaderContainer;

import java.util.stream.Stream;

@Story("Add integration tests")
public class IntegrationTest {

    static Network network = Network.SHARED;
    static String result = "";

    static PppDownloaderContainer pppDownloader = new PppDownloaderContainer()
            .withNetwork(network)
            .withExposedPorts(8080)
            .withCommand("java -jar PppDownloader-0.0.1-SNAPSHOT.war");

    static ConsulContainer consul = new ConsulContainer()
            .withNetwork(network)
            .withExposedPorts(8500)
            .withCommand("./consul agent -dev -enable-script-checks -config-dir=./consul.d");

    @BeforeAll
    static void init(){
        Stream.of(pppDownloader, consul).parallel().forEach(GenericContainer::start);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Should test if PppDownloader is reachable")
    public void runSteps(){
        testIfContainersLaunched();
        shouldReturnResponseJson();
        responseShouldBeSuccessful();
    }

    @Step
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Test if test containers started")
    public void testIfContainersLaunched() {
        boolean a = pppDownloader.isRunning();
        boolean b = consul.isRunning();
        Assertions.assertTrue(a && b);
    }

    @Step
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Building request and expecting response json from Consul")
    public void shouldReturnResponseJson() {
        //String ip = "127.0.0.1";;
        //String port = "8500";
        String ip = consul.getContainerIpAddress();
        int port = consul.getMappedPort(8500);

        String uri = String.format("http://%s:%d/v1/agent/checks", ip, port);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        result = response.getBody();
        System.out.println(result);
        Allure.addAttachment("Consul response = ", result);
        Assertions.assertNotEquals("", result);
    }

    @Step
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Checking if response successful")
    public void responseShouldBeSuccessful() {
        String seq = "TCP connect localhost:8080: Success";
        Assertions.assertTrue(result.contains(seq));
    }

}
