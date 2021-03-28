package br.com.wcaquino.controllers.tests;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class FileControllerTests {

    @BeforeAll
    public static void setUpClass() {
        baseURI = "http://restapi.wcaquino.me";
    }

    @Test
    @DisplayName("Não deve permitir o upload de um arquivo sem nome definido")
    public void shouldNotUploadFileWithoutName() {
        given()
                .when()
                .post("/upload")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", is("Arquivo não enviado"));
    }

    @Test
    @DisplayName("Deve fazer o upload de um arquivo corretamente")
    public void shouldUploadFileCorrectly() {
        given()
                .when()
                .multiPart("arquivo", new File("src/test/resources/data/users.csv"))
                .post("/upload")
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", is("users.csv"));
    }

    @Test
    @DisplayName("Deve fazer o download de um arquivo corretamente")
    public void shouldDownloadFileCorrectly() throws IOException {
        byte[] file = given()
                .when()
                .get("/download")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .asByteArray();

        final File filename = new File("src/test/resources/data/imagem.jpeg");
        final OutputStream outputStream = new FileOutputStream(filename);
        outputStream.write(file);
        outputStream.close();

        Assertions.assertEquals(94878, filename.length());
    }

}
