package br.com.wcaquino.controllers.tests;

import br.com.wcaquino.controllers.builders.UserBuilder;
import br.com.wcaquino.controllers.models.User;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserControllerTest {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://restapi.wcaquino.me";
    }

    @Test
    @DisplayName("deve retornar um usuário específico")
    public void shouldReturnAUser() {
        given()
                .when()
                .get("/users/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("João da Silva"))
                .body("age", is(30))
                .body("salary", notNullValue());
    }

    @Test
    @DisplayName("deve retornar um usuário específico usando path")
    public void shouldReturnAUserUsingPath() {
        final Response response = given()
                .when()
                .get("/users/1")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        Assertions.assertEquals(1, Integer.valueOf(response.path("id").toString()).intValue());
        Assertions.assertEquals("João da Silva", response.path("name"));
        Assertions.assertEquals(30, Integer.valueOf(response.path("age").toString()).intValue());
        Assertions.assertNotNull(response.path("salary"));
    }

    @Test
    @DisplayName("deve retornar um usuário específico usando json path")
    public void shouldReturnAUserUsingJsonPath() {
        final JsonPath response = given()
                .when()
                .get("/users/1")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath();

        Assertions.assertEquals(1, response.getInt("id"));
        Assertions.assertEquals("João da Silva", response.getString("name"));
        Assertions.assertEquals(30, response.getInt("age"));
        Assertions.assertNotNull(response.getDouble("salary"));

    }

    @Test
    @DisplayName("deve retornar um usuário específico com endereco")
    public void shouldReturnAddressFromUser() {
        given()
                .when()
                .get("/users/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(2))
                .body("name", is("Maria Joaquina"))
                .body("age", is(25))
                .body("endereco", notNullValue())
                .body("endereco.rua", is("Rua dos bobos"))
                .body("endereco.numero", is(0));
    }

    @Test
    @DisplayName("deve retornar erro quando usuário não existir na base de dados")
    public void shouldReturnInexistentUserAsError() {
        given()
                .when()
                .get("/users/4")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", is("Usuário inexistente"));
    }

    @Test
    @DisplayName("deve retornar todos os usuários")
    public void shouldReturnAllUsers() {
        given()
                .when()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .body("$.size()", is(3))
                .body("findAll{it.filhos != null}.size()", is(1))
                .body("findAll{it.age >= 20 && it.age < 30}.size()", is(2))
                .body("findAll{it.age >= 20 && it.age < 30}.name", hasItems("Maria Joaquina", "Ana Júlia"))
                .body("name.findAll{it.equals('Maria Joaquina')}.collect{it.toUpperCase()}", hasItems("MARIA JOAQUINA"))
                .body("name.collect{it.toUpperCase()}", hasItems("JOÃO DA SILVA", "MARIA JOAQUINA", "ANA JÚLIA"))
                .body("salary.min()", is(1234.5677f))
                .body("salary.findAll{it != null}.sum()", is(3734.5677490234375))
                .body("age.collect{it * 2}", hasItems(60, 50, 40));
    }

    @Test
    @DisplayName("deve criar um usuário com sucesso")
    public void shouldCreateAnUserSuccessfully() {

        final User thiago = UserBuilder
                .oneUser("Thiago", 27)
                .withSalary(1200D)
                .build();

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(thiago)
                .post("/users")
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Thiago"))
                .body("age", is(27));
    }

    @Test
    @DisplayName("nao deve adicionar um usuário sem nome")
    public void shouldNotCreateAnUserWithoutName() {
        final User thiago = UserBuilder
                .oneUser(null, 0)
                .build();

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(thiago)
                .post("/users")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", is("Name é um atributo obrigatório"));
    }

    @Test
    @DisplayName("deve remover um usuário com sucesso")
    public void shouldRemoveAnUserSuccessfully() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("userId", 1)
                .delete("/users/{userId}")
                .then()
                .assertThat()
                .statusCode(204);
    }

    @Test
    @DisplayName("deve alterar o nome de um usuario existente")
    public void shouldUpdateUserNameCorrectly() {
        final User thor = UserBuilder
                .oneUser("Thor Odinson", 1500)
                .build();

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("userId", 1)
                .body(thor)
                .put("/users/{userId}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Thor Odinson"))
                .body("age", is(1500));
    }

    @Test
    @DisplayName("deve retornar erro quando usuário não existir na base de dados para remover")
    public void shouldNotRemoveAnInexistentUser() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("userId", 5)
                .delete("/users/{userId}")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", is("Registro inexistente"));
    }
}
