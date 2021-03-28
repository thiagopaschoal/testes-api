package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.utils.JWTUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AuthenticationTests {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://restapi.wcaquino.me";
    }

    @Test
    @DisplayName("Deve retornar erro quando o usuario nao possuir autorizacao para acesso")
    public void shouldNotAuthenticateUser() {
        given()
                .when()
                .get("/basicAuth")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    @DisplayName("Deve autenticar-se corretamente usando usuário e senha")
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
    @DisplayName("Deve listar todas as contas cadastradas usando JWT como autenticador")
    public void shouldAuthenticateWithJWT() {
        final String token = JWTUtils.getTokenJwt();
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

}
