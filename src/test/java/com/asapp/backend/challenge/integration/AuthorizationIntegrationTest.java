package com.asapp.backend.challenge.integration;

import com.asapp.backend.challenge.ApiTestUtils;
import com.asapp.backend.challenge.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.asapp.backend.challenge.integration.Utils.getRequestBodyForCreatingAUser;
import static org.junit.Assert.assertEquals;
import static spark.Spark.awaitInitialization;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class AuthorizationIntegrationTest {

    @Before
    public void setUp() throws Exception {
        awaitInitialization();
    }

    @Test
    public void shouldAuthenticateUser() throws Exception {

        String userRequestBodyForCreatingAUser = getRequestBodyForCreatingAUser("emiliano", "1234haa");

        String userResponseBodyForLogin = getRequestBodyForCreatingAUser("emiliano", "1234haa");

        ApiTestUtils.TestResponse createUserResponse = ApiTestUtils.request("POST", "/users", userRequestBodyForCreatingAUser, null);

        ApiTestUtils.TestResponse loginUserResponse = ApiTestUtils.request("POST", "/login", userResponseBodyForLogin, null);

        assertEquals(200, loginUserResponse.status);
    }

    @Test
    public void shouldNotAuthenticateUser() throws Exception {

        String userRequestBodyForCreatingAUser = getRequestBodyForCreatingAUser("emiliano", "1234haa");

        String userResponseBodyForLogin = getRequestBodyForCreatingAUser("emiliano", "SmthDiff");

        ApiTestUtils.TestResponse createUserResponse = ApiTestUtils.request("POST", "/users", userRequestBodyForCreatingAUser, null);

        ApiTestUtils.TestResponse loginUserResponse = ApiTestUtils.request("POST", "/login", userResponseBodyForLogin, null);

        assertEquals(401, loginUserResponse.status);
    }



    /*byte[] encryptedPassword = BCrypt.with(LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2A)).hash(6, userResource.getPassword().getBytes(StandardCharsets.UTF_8));
        userResource.setPassword(encryptedPassword.toString());
        userResource.setEncryptedPassword(encryptedPassword);*/


}
