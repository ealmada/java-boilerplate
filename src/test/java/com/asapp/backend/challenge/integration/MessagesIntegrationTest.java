package com.asapp.backend.challenge.integration;

import com.asapp.backend.challenge.ApiTestUtils;
import com.asapp.backend.challenge.Application;
import com.asapp.backend.challenge.utils.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.asapp.backend.challenge.integration.Utils.getRequestBodyForCreatingAUser;
import static org.junit.Assert.assertEquals;
import static spark.Spark.awaitInitialization;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class MessagesIntegrationTest {

    String token;

    @Before
    public void setUp() throws Exception {
        awaitInitialization();

        String userRequestBody = getRequestBodyForCreatingAUser("emiliano", "1234haa");

        ApiTestUtils.TestResponse createUserResponse = ApiTestUtils.request("POST", "/users", userRequestBody, null);

        String userRequestBody2 = getRequestBodyForCreatingAUser("gonzalo", "1234haa");

        ApiTestUtils.TestResponse createUserResponse2 = ApiTestUtils.request("POST", "/users", userRequestBody2, null);

        ApiTestUtils.TestResponse loginUserResponse = ApiTestUtils.request("POST", "/login", userRequestBody, null);

        token = JSONUtil.readPropertyFromJson(loginUserResponse.body, "token");

        assertEquals(200, loginUserResponse.status);
    }

    @Test
    public void sendATextMessageOk() {
        String requestBody = "{\"sender\": 1,\"recipient\": 2,\"content\": {" +
                "\"type\":\"text\",\"text\":\"hola a todos\"}}";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", token);
        ApiTestUtils.TestResponse sendMessageResponse = ApiTestUtils.request("POST", "/messages", requestBody, requestHeaders);

        assertEquals(200, sendMessageResponse.status);
    }

}

