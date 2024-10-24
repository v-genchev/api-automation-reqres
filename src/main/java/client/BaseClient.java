package client;

import helpers.Properties;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseClient {
    private final static String BASE_URL = Properties.getInstance().getProperty("base.api.url");

    protected final static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .addFilter(new AllureRestAssured())
            .build();


    public static RequestSpecification requestSender() {
        return RestAssured.given(requestSpecification);
    }

}
