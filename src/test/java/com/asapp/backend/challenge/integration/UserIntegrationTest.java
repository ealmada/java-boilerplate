package com.asapp.backend.challenge.integration;

import com.asapp.backend.challenge.ApiTestUtils;
import com.asapp.backend.challenge.Application;
import com.asapp.backend.challenge.repository.UserRepository;
import com.asapp.backend.challenge.resources.UserResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spark.route.Routes;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static spark.Spark.awaitInitialization;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class UserIntegrationTest {

    @Spy
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        awaitInitialization();
    }

    @Test
    public void shouldCreateUser() throws Exception {

        UserResource userResource = new UserResource();
        userResource.setUsername("emiliano");
        userResource.setPassword("1234haa");

        String json = "{\"username\": \"emiliano\"," +
                "\"password\": \"mypassword86\"}";
        String jsonResponse = "{\"id\":\"1\"}";

        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", "/users", json);

        System.out.println(res.body);
        assertEquals(200, res.status);
    }

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
    }

}
