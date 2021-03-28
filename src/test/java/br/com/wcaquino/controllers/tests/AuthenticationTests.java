package br.com.wcaquino.controllers.tests;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.apache.groovy.util.Maps;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

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

    @Test
    @DisplayName("deve listar todas as contas cadastradas usando JWT como autenticador")
    public void shouldAuthenticateWithJWT() {
        final String token = getTokenJwt();
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .get("http://barrigarest.wcaquino.me/contas")
                .then()
                .assertThat()
                .statusCode(200)
                .body("$.size()", Matchers.is(3))
                .body("nome", Matchers.hasItems("Thor", "Captain America", "Iron Man"));
    }

    private String getTokenJwt() {

        final Map<String, Object> login = Maps.of("email", "thiago@admin.com", "senha", "123456");
        final String token = given()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("http://barrigarest.wcaquino.me/signin")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("token");
        return token;
    }

}
