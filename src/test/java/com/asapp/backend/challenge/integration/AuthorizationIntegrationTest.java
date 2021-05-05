package com.asapp.backend.challenge.integration;

import com.asapp.backend.challenge.ApiTestUtils;
import com.asapp.backend.challenge.Application;
import com.asapp.backend.challenge.repository.UserRepository;
import com.asapp.backend.challenge.resources.UserResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static spark.Spark.awaitInitialization;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class AuthorizationIntegrationTest {

    @Before
    public void setUp() throws Exception {
        awaitInitialization();
    }

    @Test
    public void shouldCreateUser() throws Exception {

        UserResource userResource = new UserResource();
        userResource.setUsername("emiliano");
        userResource.setPassword("1234haa");

        ObjectMapper mapper = new ObjectMapper();

        // create a JSON object
        ObjectNode body = mapper.createObjectNode();
        body.put("id", "emiliano");
        body.put("token", "1234haa");

        // convert `ObjectNode` to pretty-print JSON
        // without pretty-print, use `user.toString()` method
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);

        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", "/login", json);

        System.out.println(res.body);
        assertEquals(200, res.status);
    }

    /*
    //TODO remove
    @Test
    public void shouldGetUser() throws Exception {
        UserResource userResource = UserResource.builder().id(1L).username("emiliano").password("1234haa").encryptedPassword("XXXXXX".getBytes(StandardCharsets.UTF_8)).build();
        UserResource userResource1 = UserResource.builder().id(2L).username("gonzalo").password("1234haa").encryptedPassword("XXXXXX".getBytes(StandardCharsets.UTF_8)).build();
        List<UserResource> userResourceList = new ArrayList<>();
        userResourceList.add(userResource);
        userResourceList.add(userResource1);
        doReturn(Optional.of(userResourceList)).when(userRepository).findByUsername(anyString());

        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", "/users", null);

        System.out.println(res.body);
        assertEquals(200, res.status);
    }*/

}
