package br.com.wcaquino.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class UserV2ControllerTest {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://restapi.wcaquino.me";
        basePath = "v2";
    }

    @Test
    @DisplayName("deve retornar um content-type do tipo JSON")
    public void shouldReturnContetTypeAsJson() {
        given()
                .when()
                .queryParam("format", "json")
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("deve retornar um content-type do tipo XML")
    public void shouldReturnContetTypeAsXML() {
        given()
                .when()
                .queryParam("format", "xml")
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.XML);
    }

    @Test
    @DisplayName("deve retornar um content-type do tipo HTML quando nenhum formato for definido")
    public void shouldReturnContetTypeAsHTML() {
        given()
                .when()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.HTML);
    }
}
