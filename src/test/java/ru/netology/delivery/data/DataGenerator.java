package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static final Faker FAKER = new Faker(new Locale("ru"));

    private DataGenerator() {
    }

    private static RegistrationDto sendRequest(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("api/system/users")
                .then()
                .statusCode(200);
        return user;
    }

    public static String getRandomLogin() {
        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        return FAKER.internet().password();
    }

    public static class Registration {
        private Registration() {

        }
        public static RegistrationDto getUser(String status) {
            return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        }
        public static RegistrationDto getRegisteredUser(String status) {
            return sendRequest(getUser(status));
        }
    }
    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}