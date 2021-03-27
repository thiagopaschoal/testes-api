package br.com.wcaquino.controllers.tests;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;

public class AuthenticationTests {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://restapi.wcaquino.me";
    }

    @Test
    @DisplayName("deve retornar erro quando o usuario nao possuir autorizacao para acesso")
    public void shouldNotAuthenticateUser() {
        given()
                .when()
                .get("/basicAuth")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    @DisplayName("deve autenticar-se corretamente usando usuário e senha")
    public void shouldAuthenticationCorrectlyWithBasicCredentials() {
        final String user = "admin";
        final String password = "senha";

        given()
                .auth()
                .basic(user, password)
                .when()
                .get("/basicAuth")
                .then()
                .assertThat()
                .statusCode(200)
                .body("status", Matchers.is("logado"));

    }

}
