package de.gernd.simplemon.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SimplemonIntegrationTest {

    @Test
    public void test_addUrlToMonitor_urlIsInMonitoredUrls() {
        // add site to monitoring
        given()
                .body("{\"url\" : \"http://www.google.de\"}")
                .header("Content-Type","application/json")
                .when()
                .post("http://localhost:8081/monitored-sites")
                .then()
                .statusCode(HttpStatus.CREATED.value());


        when()
                .get("http://localhost:8081/monitored-sites")
                .then().statusCode(HttpStatus.OK.value());
    }
}
