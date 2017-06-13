package de.gernd.simplemon.api;

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SimplemonApiTest {

    @Test
    public void test_addUrlToMonitor_urlIsInMonitoredUrls() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // add site to monitoring
        given()
                .body("{\"url\" : \"http://www.google.de\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("http://localhost:8081/monitored-sites")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        // site should now be contained in the monitored URLs
        when()
                .get("http://localhost:8081/monitored-sites")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("monitoredSites.url", hasItem("http://www.google.de"));
    }
}
