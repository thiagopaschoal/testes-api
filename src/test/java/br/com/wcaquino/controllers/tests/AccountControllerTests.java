package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.builders.AccountBuilder;
import br.com.wcaquino.controllers.models.Account;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AccountControllerTests extends BaseTest {

    @Test
    @DisplayName("Deve incluir conta com sucesso")
    public void shouldCreateAccountWithSuccess() {
        final Account accountToBeCreate = AccountBuilder
                .oneAccount("Conta inserida", 13915L)
                .build();

        given()
                .when()
                .body(accountToBeCreate)
                .post("/contas")
                .then()
                .statusCode(201)
                .body("nome", Matchers.is("Conta inserida"));
    }

    @Test
    @DisplayName("Deve alterar conta com sucesso")
    public void shouldUpdateAccountWithSuccess() {

        final Integer id = findAccountIdByAccountName("Conta para alterar");

        final Account accountToBeUpdate = AccountBuilder
                .oneAccount("Conta Alterada", 13915L)
                .build();

        given()
                .when()
                .body(accountToBeUpdate)
                .pathParam("contaId", id)
                .put("/contas/{contaId}")
                .then()
                .statusCode(200)
                .body("nome", Matchers.is("Conta Alterada"));
    }

    @Test
    @DisplayName("Não deve incluir conta com nome repetido")
    public void shouldNotCreateAccountWithSameName() {
        final Account accountInvalid = AccountBuilder
                .oneAccount("Conta mesmo nome", 13915L)
                .build();

        given()
                .when()
                .body(accountInvalid)
                .post("/contas")
                .then()
                .statusCode(400)
                .body("error", Matchers.is("Já existe uma conta com esse nome!"));
    }

    @Test
    @DisplayName("Deve calcular saldo das contas")
    public void shouldCalculateAccountBalance() {
        final Integer id = findAccountIdByAccountName("Conta para saldo");

        given()
                .when()
                .get("/saldo")
                .then()
                .statusCode(200)
                .body("find{it.conta_id == " + id + "}.saldo", Matchers.is("534.00"));
    }

    @Test
    @DisplayName("Não deve remover conta com movimentações")
    public void shouldNotRemoveAccountWithTransactions() {
        final Integer id = findAccountIdByAccountName("Conta com movimentacao");
        given()
                .when()
                .pathParam("contaId", id)
                .delete("/contas/{contaId}")
                .then()
                .statusCode(500)
                .body("constraint", Matchers.is("transacoes_conta_id_foreign"));
    }

}