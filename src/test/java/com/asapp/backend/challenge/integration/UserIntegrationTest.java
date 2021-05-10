package com.asapp.backend.challenge.integration;

import com.asapp.backend.challenge.utils.ApiTestUtils;
import com.asapp.backend.challenge.Application;
import com.asapp.backend.challenge.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
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

        String json = "{\"username\": \"emiliano1\"," +
                "\"password\": \"mypassword86\"}";

        ApiTestUtils.TestResponse res = ApiTestUtils.request("POST", "/users", json, null);

        assertEquals(200, res.status);
    }

}
