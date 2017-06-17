package de.gernd.simplemon.api;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SimplemonApiTest {

    @Test
    public void test_addUrlToMonitor_urlIsInMonitoredUrlsAndMonitoringDataAvailable() throws InterruptedException {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // add site to monitoring
        ExtractableResponse response = given()
                .body("{\"url\" : \"http://www.google.de\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("http://localhost:8081/monitored-sites")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        // extract location of generated resource
        final String monitoredResourceUrl = response.header("Location");

        // site should now be contained in all monitored URLs
        when()
                .get("http://localhost:8081/monitored-sites")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("monitoredSites.url", hasItem("http://www.google.de"));

        // wait some time and check if monitoring data has been gathered
        Thread.sleep(5000);
        Response getMonitoredSiteResponse = when()
                .get(monitoredResourceUrl)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
        List<Map<String,Integer>> monitoringResults = getMonitoredSiteResponse.path("monitoringResults");
        assertTrue("No monitoring results available", monitoringResults.size() > 0);

        // verify that timeNeededForRequest and timestamp are available
        Map<String, Integer> firstMonitoringResult = monitoringResults.get(0);
        int timeNeededForRequest = firstMonitoringResult.get("timeNeededForRequest");
        assertTrue("timeNeededForRequest does not exist or is negative", timeNeededForRequest > 0);
        int timestamp = firstMonitoringResult.get("timestamp");
        assertTrue("timestamp does not exist or is negative", timestamp > 0);
    }
}
