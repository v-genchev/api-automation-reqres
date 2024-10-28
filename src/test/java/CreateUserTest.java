import client.UserClient;
import helpers.Utils;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.User;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

        assertThat(createUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));
    }

    @Test(description = "Verify user creation data")
    void createUserVerifyData() {
        User userToCreate = User.builder()
                .name("Georgi")
                .job("Banker")
                .build();
        User createdUser = UserClient.createUser(userToCreate).as(User.class);

        assertThat(createdUser.getName(), is(userToCreate.getName()));
        assertThat(createdUser.getJob(), is(userToCreate.getJob()));
        assertThat(createdUser.getId(), not(0));
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

        assertThat(headerDate.withNano(0),
                is(createdAt.withNano(0)));
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

        assertThat(createUserResponse1.getStatusCode(), is(HttpStatus.SC_CREATED));
        assertThat(createUserResponse2.getStatusCode(), is(HttpStatus.SC_CREATED));
        assertThat(createdUser1.getId(), not(createdUser2.getId()));
    }
}
