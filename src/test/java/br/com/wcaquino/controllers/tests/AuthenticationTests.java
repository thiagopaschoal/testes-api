package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.utils.JWTUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class AuthenticationTests {

    @BeforeEach
    public void setUp() {

        final RequestSpecification request = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "JWT " + JWTUtils.getTokenJwt())
                .setBaseUri("http://barrigarest.wcaquino.me")
                .build();

        requestSpecification = request;

        get("/reset").then().statusCode(200);
    }

    @Test
    @DisplayName("Deve listar todas as contas cadastradas usando JWT como autenticador")
    public void shouldAuthenticateWithJWT() {
        given()
                .when()
                .get("/contas")
                .then()
                .assertThat()
                .statusCode(200)
                .body("$.size()", Matchers.is(6));
    }

    @Test
    @DisplayName("Não deve acessar sem token")
    public void shouldNotAuthenticateWithoutToken() {
        FilterableRequestSpecification filterable = (FilterableRequestSpecification) requestSpecification;
        filterable.removeHeader("Authorization");

        given()
                .when()
                .get("/contas")
                .then()
                .assertThat()
                .statusCode(401);
    }

}
