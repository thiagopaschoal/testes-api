package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.builders.TransactionBuilder;
import br.com.wcaquino.controllers.enums.TransactionType;
import br.com.wcaquino.controllers.models.Transaction;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class TransactionControllerTests extends BaseTest {

    @Test
    @DisplayName("Deve incluir movimentação com sucesso")
    public void shouldCreateMovementWithSuccess() {

        final Integer contaId = findAccountIdByAccountName("Conta para movimentacoes");

        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, Long.valueOf(contaId), 13915L, 200D, "Transacao Inserido", "Testes Automatizados")
                .withObservation(null)
                .withPayDay(LocalDate.now().plusDays(2))
                .withState(false)
                .build();

        given()
                .body(transaction)
                .when()
                .post("/transacoes")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("Deve validar campos obrigatórios da movimentação")
    public void shouldNotCreateMovementWithNoneValue() {
        Transaction transaction = TransactionBuilder
                .oneTransaction(null, null, null, null, null, null)
                .build();

        given()
                .body(transaction)
                .when()
                .post("/transacoes")
                .then()
                .statusCode(400)
                .body("msg", Matchers.hasItems(
                        "Data do pagamento é obrigatório",
                        "Data da Movimentação é obrigatório",
                        "Descrição é obrigatório",
                        "Interessado é obrigatório",
                        "Valor é obrigatório",
                        "Conta é obrigatório",
                        "Situação é obrigatório",
                        "Valor deve ser um número"
                ));
    }

    @Test
    @DisplayName("Nao deve criar uma movimentacao com uma data de movimentacao futura")
    public void shouldNotCreateMovementWithMovementDataGreaterThanPayDay() {
        Transaction transaction = TransactionBuilder
                .oneTransaction(TransactionType.REC, 500386L, 13915L, 200D, "Capitäo América", "Testes automatizados")
                .withMovementDay(LocalDate.now().plusDays(5))
                .withPayDay(LocalDate.now())
                .withObservation(null)
                .withState(false)
                .build();

        given()
                .body(transaction)
                .when()
                .post("/transacoes")
                .then()
                .statusCode(400)
                .body("msg", Matchers.hasItems("Data da Movimentação deve ser menor ou igual à data atual"));
    }

    @Test
    @DisplayName("Deve remover uma transacao com sucesso")
    public void shouldRemoveAccountWithSuccessfully() {
        Integer id = findTransactionIdByTransactionName("Movimentacao para exclusao");
        given()
                .when()
                .pathParam("transactionId", id)
                .delete("/transacoes/{transactionId}")
                .then()
                .statusCode(204);
    }

}
