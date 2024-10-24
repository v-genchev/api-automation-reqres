import client.UserClient;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import model.Support;
import model.User;
import model.UserList;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("User List")
@Test(groups = {"users"})
public class UserListTest extends BaseTest {

    final static int DEFAULT_USERS_PER_PAGE = 6;

    final static int DEFAULT_PAGE_TO_REQUEST = 2;

    @Test(description = "Verify listing of users successful")
    void listUsersSuccessResponse() {
        Response userListResponse = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST);
        Assert.assertEquals(userListResponse.getStatusCode(), HttpStatus.SC_OK);
    }

    @Test(description = "Fetch user list for non-existent page")
    void listUsersPageNotExistent() {
        Response userListResponse = UserClient.listUsers(150);
        UserList actualUserList = userListResponse.as(UserList.class);
        Assert.assertEquals(userListResponse.getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(actualUserList.getData().size(), 0);
    }

    @Test(description = "Verify pagination data in user list")
    void listUsersVerifyPagination() {
        UserList actualUserList = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST).as(UserList.class);

        Assert.assertEquals(actualUserList.getPage(), DEFAULT_PAGE_TO_REQUEST);
        Assert.assertEquals(actualUserList.getPerPage(), DEFAULT_USERS_PER_PAGE);
        Assert.assertEquals(actualUserList.getTotal(),
                actualUserList.getPerPage() * actualUserList.getTotalPages());
    }

    @Test(description = "Verify number of users returned")
    void listUsersVerifyNumberOfUsers() {
        UserList actualUserList = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST).as(UserList.class);

        Assert.assertEquals(actualUserList.getData().size(), actualUserList.getPerPage());
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

        Assert.assertListContainsObject(actualUserList.getData(), expectedUser,
                "Expected user is not present in the user list data");
    }

    @Test(description = "Verify support info in list")
    void listUsersVerifySupportInfo() {
        UserList actualUserList = UserClient.listUsers(DEFAULT_PAGE_TO_REQUEST).as(UserList.class);
        Support expectedSupportObject = Support.builder()
                .url("https://reqres.in/#support-heading")
                .text("To keep ReqRes free, contributions towards server costs are appreciated!")
                .build();

        Assert.assertEquals(actualUserList.getSupport(), expectedSupportObject);
    }


}
