import client.UserClient;
import helpers.Utils;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.User;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

@Feature("User Creation")
@Test(groups = {"users"})
public class CreateUserTest extends BaseTest {


    @Test(description = "Verify user creation successful")
    void createUserSuccess() {
        User userToCreate = User.builder()
                .name("Georgi")
                .job("Banker")
                .build();
        Response createUserResponse = UserClient.createUser(userToCreate);

        Assert.assertEquals(createUserResponse.getStatusCode(), HttpStatus.SC_CREATED);
    }

    @Test(description = "Verify user creation data")
    void createUserVerifyData() {
        User userToCreate = User.builder()
                .name("Georgi")
                .job("Banker")
                .build();
        User createdUser = UserClient.createUser(userToCreate).as(User.class);

        Assert.assertEquals(createdUser.getName(), userToCreate.getName());
        Assert.assertEquals(createdUser.getJob(), userToCreate.getJob());
        Assert.assertNotEquals(createdUser.getId(), 0);
    }

    @Test(description = "Verify user createdAt time")
    void createUserVerifyCreatedAt() {
        User userToCreate = User.builder()
                .name("Georgi")
                .job("Banker")
                .build();
        Response createUserResponse = UserClient.createUser(userToCreate);
        String headerDateString = createUserResponse.getHeader("DATE");
        User createdUser = createUserResponse.as(User.class);
        LocalDateTime headerDate = Utils.parseDate(headerDateString, HEADER_DATE_FORMATTER);
        LocalDateTime createdAt = Utils.parseDate(createdUser.getCreatedAt());

        Assert.assertEquals(headerDate.withNano(0), createdAt.withNano(0));
    }

    @Test(description = "Verify user with the same data can be created twice")
    void createTheSameUserTwice() {
        User userToCreate = User.builder()
                .name("Repeating")
                .job("Banker")
                .build();
        Response createUserResponse1 = UserClient.createUser(userToCreate);
        Response createUserResponse2 = UserClient.createUser(userToCreate);
        User createdUser1 = createUserResponse1.as(User.class);
        User createdUser2 = createUserResponse2.as(User.class);

        Assert.assertEquals(createUserResponse1.getStatusCode(), HttpStatus.SC_CREATED);
        Assert.assertEquals(createUserResponse2.getStatusCode(), HttpStatus.SC_CREATED);
        Assert.assertNotEquals(createdUser1.getId(), createdUser2.getId());
    }
}
