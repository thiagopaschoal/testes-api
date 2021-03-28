package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.models.Account;
import br.com.wcaquino.controllers.utils.JWTUtils;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AccountControllerTests {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://barrigarest.wcaquino.me";
    }

    @Test
    @DisplayName("Não deve acessar sem token")
    public void shouldNotAuthenticateWithoutToken() {
        given()
                .when()
                .get("/contas")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    @DisplayName("Deve incluir conta com sucesso")
    public void shouldCreateAccountWithSuccess() {
        final String token = JWTUtils.getTokenJwt();
        final Account hulkAccount = new Account("Hulk", 13915L);
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .body(hulkAccount)
                .contentType(ContentType.JSON)
                .post("/contas")
                .then()
                .assertThat()
                .statusCode(201)
                .body("nome", Matchers.is("Hulk"));
    }

    @Test
    @DisplayName("Deve alterar conta com sucesso")
    public void shouldUpdateAccountWithSuccess() {
        final String token = JWTUtils.getTokenJwt();
        final Account thorOdinson = new Account("Thor Odinson", 13915L);
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .body(thorOdinson)
                .contentType(ContentType.JSON)
                .pathParam("contaId", 500384L)
                .put("/contas/{contaId}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("nome", Matchers.is("Thor Odinson"));
    }

    @Test
    @DisplayName("Não deve incluir conta com nome repetido")
    public void shouldNotCreateAccountWithSameName() {
        final String token = JWTUtils.getTokenJwt();
        final Account ironMan = new Account("Iron Man", 13915L);
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .body(ironMan)
                .contentType(ContentType.JSON)
                .post("/contas")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", Matchers.is("Já existe uma conta com esse nome!"));
    }

    @Test
    @DisplayName("Deve calcular saldo das contas")
    public void shouldCalculateAccountBalance() {
        final String token = JWTUtils.getTokenJwt();
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .contentType(ContentType.JSON)
                .get("/saldo")
                .then()
                .assertThat()
                .statusCode(200)
                .body("find{it.conta_id == 500386}.saldo", Matchers.is("700.00"));
    }

    @Test
    @DisplayName("Não deve remover conta com movimentações")
    public void shouldNotRemoveAccountWithTransactions() {
        final String token = JWTUtils.getTokenJwt();
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .contentType(ContentType.JSON)
                .pathParam("contaId", 500386L)
                .delete("/contas/{contaId}")
                .then()
                .assertThat()
                .statusCode(500)
                .body("constraint", Matchers.is("transacoes_conta_id_foreign"));
    }

    @Test
    @DisplayName("deve remover uma conta com sucesso")
    public void shouldRemoveAccountWithSuccessfully() {
        final String token = JWTUtils.getTokenJwt();
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .contentType(ContentType.JSON)
                .pathParam("contaId", 500969L)
                .delete("/contas/{contaId}")
                .then()
                .assertThat()
                .statusCode(204);
    }
}
