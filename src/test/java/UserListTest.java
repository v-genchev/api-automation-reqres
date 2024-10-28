import client.UserClient;
import helpers.LoggingAssertion;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.Support;
import model.User;
import model.UserList;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Feature("User List")
@Test(groups = {"users"})
public class UserListTest extends BaseTest {

    final static int DEFAULT_USERS_PER_PAGE = 6;

    final static int DEFAULT_PAGE_TO_REQUEST = 2;

    @Test(description = "Verify listing of users successful")
    void listUsersSuccessResponse() {
        Response userListResponse = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST);
        LoggingAssertion.assertThat(userListResponse.getStatusCode(), is(HttpStatus.SC_OK),
                "status is successful");
    }

    @Test(description = "Fetch user list for non-existent page")
    void listUsersPageNotExistent() {
        Response userListResponse = UserClient.listUsers(150);
        UserList actualUserList = userListResponse.as(UserList.class);
        assertThat(userListResponse.getStatusCode(), is(HttpStatus.SC_OK));
        assertThat(actualUserList.getData(), hasSize(0));
    }

    @Test(description = "Verify pagination data in user list")
    void listUsersVerifyPagination() {
        UserList actualUserList = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST).as(UserList.class);

        assertThat(actualUserList.getPage(), is(DEFAULT_PAGE_TO_REQUEST));
        assertThat(actualUserList.getPerPage(), is(DEFAULT_USERS_PER_PAGE));
        assertThat(actualUserList.getTotal(),
                is(actualUserList.getPerPage() * actualUserList.getTotalPages()));
    }

    @Test(description = "Verify number of users returned")
    void listUsersVerifyNumberOfUsers() {
        UserList actualUserList = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST).as(UserList.class);

        assertThat(actualUserList.getData(),
                hasSize(actualUserList.getPerPage()));
    }

    @Test(description = "Verify user data in list")
    void listUsersVerifyUserData() {
        UserList actualUserList = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST).as(UserList.class);
        User expectedUser = User.builder()
                .id(7)
                .email("michael.lawson@reqres.in")
                .firstName("Michael")
                .lastName("Lawson")
                .avatar("https://reqres.in/img/faces/7-image.jpg")
                .build();

        assertThat("Expected user is not present in the user list data",
                actualUserList.getData(), hasItem(expectedUser));
    }

    @Test(description = "Verify support info in list")
    void listUsersVerifySupportInfo() {
        UserList actualUserList = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST).as(UserList.class);
        Support expectedSupportObject = Support.builder()
                .url("https://reqres.in/#support-heading")
                .text("To keep ReqRes free, contributions towards server costs are appreciated!")
                .build();

        assertThat(actualUserList.getSupport(), is(expectedSupportObject));
    }


}
