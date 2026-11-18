package test;

import com.github.javafaker.Faker;
import endpoints.UserEndpoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import payload.User;

public class UserTests {

    Faker faker;
    User userPayload;

    private static final Logger logger = LogManager.getLogger(UserTests.class);

    @BeforeClass
    public void setup()

    {

        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
    }


    @Test(priority=1)
    public void testPostUser()
    {
        logger.info("Starting automation tests");
        logger.error("Testing error logging");
        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void testGetUserByName()
    {
        Response response=UserEndpoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 3)
    public void testUpdateUserByName()
    {
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndpoints.updateUser(this.userPayload.getUsername(),userPayload);
        response.then().log().body().statusCode(200);

        Assert.assertEquals(response.getStatusCode(), 200);

        Response responseAfterUpdate=UserEndpoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);


    }

    @Test(priority = 4)
    public void testDeleteUserByName()
    {
        Response response=UserEndpoints.deleteUser(this.userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(),200);

    }

}

