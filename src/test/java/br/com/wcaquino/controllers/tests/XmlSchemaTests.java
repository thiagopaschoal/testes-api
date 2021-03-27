package br.com.wcaquino.controllers.tests;

import io.restassured.matcher.RestAssuredMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class XmlSchemaTests {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://restapi.wcaquino.me";
    }

    @Test
    @DisplayName("deve validar que o resultado obtido está de acordo com o xsd")
    public void shouldValidXMLSchema() {
        given()
                .when()
                .get("/usersXML")
                .then()
                .assertThat()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("schemas/users.xsd"));
    }

    @Test
    @DisplayName("deve retornar erro quando o registro não estiver de acordo com o schema")
    public void shouldThrowExceptionToInvalidXMLSchema() {
        Assertions.assertThrows(SAXException.class, () -> {
            given()
                    .when()
                    .get("/invalidUsersXML")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body(RestAssuredMatchers.matchesXsdInClasspath("schemas/users.xsd"));
        });

    }
}
