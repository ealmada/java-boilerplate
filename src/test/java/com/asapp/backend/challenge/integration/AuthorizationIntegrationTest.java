package com.asapp.backend.challenge.integration;

import com.asapp.backend.challenge.utils.ApiTestUtils;
import com.asapp.backend.challenge.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.asapp.backend.challenge.utils.Utils.getRequestBodyForCreatingAUser;
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

        //ApiTestUtils.TestResponse loginUserResponse2 = ApiTestUtils.test("/login", new HttpPost(URI.create("nada")));

        ApiTestUtils.TestResponse loginUserResponse = ApiTestUtils.request("POST", "/login", userResponseBodyForLogin, null);

        assertEquals(401, loginUserResponse.status);
    }


}
