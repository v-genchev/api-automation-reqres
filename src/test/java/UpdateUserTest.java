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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
        assertThat(updatedUserResponse.getStatusCode(), is(HttpStatus.SC_OK));
    }

    @Test(description = "Verify user data after update")
    void updateUserVerifyData() {
        user.setJob("Cleaning guy");
        User updatedUser = UserClient.updateUser(createdUserId, user).as(User.class);

        assertThat(updatedUser.getJob(), is(user.getJob()));
    }

    @Test(description = "Verify user updatedAt time")
    void updateUserVerifyUpdatedAt() {
        user.setJob("Cleaning guy");
        Response updatedUserResponse = UserClient.updateUser(createdUserId, user);
        User updatedUser = updatedUserResponse.as(User.class);
        String headerDateString = updatedUserResponse.getHeader("DATE");
        LocalDateTime headerDate = Utils.parseDate(headerDateString, HEADER_DATE_FORMATTER);
        LocalDateTime createdAt = Utils.parseDate(updatedUser.getUpdatedAt());

        assertThat(headerDate.withNano(0),
                is(createdAt.withNano(0)));
    }
}
