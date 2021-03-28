package br.com.wcaquino.controllers.utils;

import io.restassured.http.ContentType;
import org.apache.groovy.util.Maps;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class JWTUtils {

    private JWTUtils() {}

    public static String getTokenJwt() {

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
