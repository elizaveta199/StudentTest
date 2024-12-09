package ru.ishutina.edu.student;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.ishutina.edu.config.Config;

import static io.restassured.RestAssured.given;

public class BaseApiClient {
    public RequestSpecification getPostSpec() {
        return given()
                .filter(new ResponseLoggingFilter())
                .filter(new RequestLoggingFilter())
                //.log().all()
                .contentType(ContentType.JSON)
                .baseUri(Config.BASE_URL);
    }
}