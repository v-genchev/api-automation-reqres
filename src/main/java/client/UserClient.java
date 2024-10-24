package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;

public class UserClient extends BaseClient {

    private UserClient() {

    }

    @Step("List users on page: {page}")
    public static Response listUsers (int page) {
        return requestSender()
                .queryParam("page", page)
                .when()
                .get("users");
    }

    @Step("Create user")
    public static Response createUser (User user) {
        return requestSender()
                .body(user)
                .when()
                .post("users");
    }

    @Step("Update user")
    public static Response updateUser (int userId, User user) {
        return requestSender()
                .body(user)
                .when()
                .put("users/" + userId);
    }
}
