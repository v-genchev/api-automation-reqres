import client.UserClient;
import helpers.Utils;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.User;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

@Feature("User Update")
@Test(groups = {"users"})
public class UpdateUserTest extends BaseTest {

    User user;
    Integer createdUserId;

    @BeforeTest
    void beforeTest() {
        user = User.builder()
                .name("Georgi")
                .job("Banker")
                .build();
        User createdUser = UserClient.createUser(user).as(User.class);
        createdUserId = createdUser.getId();
    }

    @Test(description = "Verify user updated successfully")
    void updateUserSuccess() {
        user.setJob("Cleaning guy");
        Response updatedUserResponse = UserClient.updateUser(createdUserId, user);
        Assert.assertEquals(updatedUserResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(description = "Verify user data after update")
    void updateUserVerifyData() {
        user.setJob("Cleaning guy");
        User updatedUser = UserClient.updateUser(createdUserId, user).as(User.class);

        Assert.assertEquals(updatedUser.getJob(), user.getJob());
    }

    @Test(description = "Verify user updatedAt time")
    void updateUserVerifyUpdatedAt() {
        user.setJob("Cleaning guy");
        Response updatedUserResponse = UserClient.updateUser(createdUserId, user);
        User updatedUser = updatedUserResponse.as(User.class);
        String headerDateString = updatedUserResponse.getHeader("DATE");
        LocalDateTime headerDate = Utils.parseDate(headerDateString, HEADER_DATE_FORMATTER);
        LocalDateTime createdAt = Utils.parseDate(updatedUser.getUpdatedAt());

        Assert.assertEquals(headerDate.withNano(0), createdAt.withNano(0));
    }
}
