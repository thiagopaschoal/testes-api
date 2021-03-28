package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.utils.JWTUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.requestSpecification;

public class BaseTest {

    @BeforeAll
    public static void setUpClass() {

        final RequestSpecification request = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "JWT " + JWTUtils.getTokenJwt())
                .setBaseUri("http://barrigarest.wcaquino.me")
                .build();

        requestSpecification = request;

        get("/reset").then().statusCode(200);
    }

    public Integer findAccountIdByAccountName(String nome) {
        return get("/contas?nome=" + nome).then().extract().path("id[0]");
    }

    public Integer findTransactionIdByTransactionName(String nome) {
        return get("/transacoes?nome=" + nome).then().extract().path("id[0]");
    }

}
