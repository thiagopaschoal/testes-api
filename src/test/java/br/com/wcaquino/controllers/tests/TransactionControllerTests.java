package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.builders.TransactionBuilder;
import br.com.wcaquino.controllers.enums.TransactionType;
import br.com.wcaquino.controllers.models.Transaction;
import br.com.wcaquino.controllers.utils.JWTUtils;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TransactionControllerTests {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://barrigarest.wcaquino.me";
    }

    @Test
    @DisplayName("Deve incluir movimentação com sucesso")
    public void shouldCreateMovementWithSuccess() {
        final String token = JWTUtils.getTokenJwt();
        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, 500386L, 13915L, 200D, "Capitao America", "Testes Automatizados")
                .withObservation(null)
                .withPayDay(LocalDate.now().plusDays(2))
                .withState(false)
                .build();

        given()
                .header("Authorization", "JWT " + token)
                .body(transaction)
                .contentType(ContentType.JSON)
                .when()
                .post("/transacoes")
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @DisplayName("Nao deve criar uma movimentacao sem especificar um valor")
    public void shouldNotCreateMovementWithoutValue() {
        final String token = JWTUtils.getTokenJwt();
        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, 500386L, 13915L, null, "Capitao America", "Testes Automatizados")
                .withObservation(null)
                .withPayDay(LocalDate.now().plusDays(2))
                .withState(false)
                .build();

        given()
                .header("Authorization", "JWT " + token)
                .body(transaction)
                .contentType(ContentType.JSON)
                .when()
                .post("/transacoes")
                .then()
                .assertThat()
                .statusCode(400)
                .body("msg", Matchers.hasItems("Valor é obrigatório", "Valor deve ser um número"));
    }

    @Test
    @DisplayName("Nao deve criar uma movimentacao sem especificar uma descricao")
    public void shouldNotCreateMovementWithoutDescription() {
        final String token = JWTUtils.getTokenJwt();
        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, 500386L, 13915L, 200D, "Capitao America", null)
                .withObservation(null)
                .withPayDay(LocalDate.now().plusDays(2))
                .withState(false)
                .build();

        given()
                .header("Authorization", "JWT " + token)
                .body(transaction)
                .contentType(ContentType.JSON)
                .when()
                .post("/transacoes")
                .then()
                .assertThat()
                .statusCode(400)
                .body("msg", Matchers.hasItems("Descrição é obrigatório"));
    }

    @Test
    @DisplayName("Nao deve criar uma movimentacao sem especificar um interessado")
    public void shouldNotCreateMovementWithoutInterested() {
        final String token = JWTUtils.getTokenJwt();
        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, 500386L, 13915L, 200D, null, "Testes automatizados")
                .withObservation(null)
                .withPayDay(LocalDate.now().plusDays(2))
                .withState(false)
                .build();

        given()
                .header("Authorization", "JWT " + token)
                .body(transaction)
                .contentType(ContentType.JSON)
                .when()
                .post("/transacoes")
                .then()
                .assertThat()
                .statusCode(400)
                .body("msg", Matchers.hasItems("Interessado é obrigatório"));
    }

    @Test
    @DisplayName("Nao deve criar uma movimentacao sem especificar uma data de pagamento")
    public void shouldNotCreateMovementWithoutPayDay() {
        final String token = JWTUtils.getTokenJwt();
        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, 500386L, 13915L, 200D, "Capitäo América", "Testes automatizados")
                .withObservation(null)
                .withState(false)
                .build();

        given()
                .header("Authorization", "JWT " + token)
                .body(transaction)
                .contentType(ContentType.JSON)
                .when()
                .post("/transacoes")
                .then()
                .assertThat()
                .statusCode(400)
                .body("msg", Matchers.hasItems("Data do pagamento é obrigatório"));
    }

    @Test
    @DisplayName("Nao deve criar uma movimentacao com uma data de movimentacao futura")
    public void shouldNotCreateMovementWithMovementDataGreaterThanPayDay() {
        final String token = JWTUtils.getTokenJwt();
        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, 500386L, 13915L, 200D, "Capitäo América", "Testes automatizados")
                .withMovementDay(LocalDate.now().plusDays(5))
                .withPayDay(LocalDate.now())
                .withObservation(null)
                .withState(false)
                .build();

        given()
                .header("Authorization", "JWT " + token)
                .body(transaction)
                .contentType(ContentType.JSON)
                .when()
                .post("/transacoes")
                .then()
                .assertThat()
                .statusCode(400)
                .body("msg", Matchers.hasItems("Data da Movimentação deve ser menor ou igual à data atual"));
    }

    @Test
    @DisplayName("Deve remover uma transacao com sucesso")
    public void shouldRemoveAccountWithSuccessfully() {
        final String token = JWTUtils.getTokenJwt();
        given()
                .header("Authorization", "JWT " + token)
                .when()
                .contentType(ContentType.JSON)
                .pathParam("transactionId", 462288L)
                .delete("/transacoes/{transactionId}")
                .then()
                .assertThat()
                .statusCode(204);
    }

}
