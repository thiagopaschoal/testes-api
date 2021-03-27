package br.com.wcaquino.controllers.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class HelloControllerTest {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://restapi.wcaquino.me";
    }

    @Test
    @DisplayName("deve retorna ola mundo como mensagem no response")
    public void shouldReturnOlaMundoAsMessageIntoResponse() {
        given()
                .when()
                .get("/ola")
                .then()
                .statusCode(200)
                .body(is("Ola Mundo!"));
    }

    @Test
    @DisplayName("deve retornar ola mundo como mensagem mas usando outro jeito no Rest Assured")
    public void shouldReturnOlaMundoAsMessageUsingAnotherWayWithRestAssured() {
        given()
                .when()
                .get("/ola")
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("Ola"))
                .body(is("Ola Mundo!"));
    }
}
