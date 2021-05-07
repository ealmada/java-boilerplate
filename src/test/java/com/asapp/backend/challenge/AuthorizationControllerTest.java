package com.asapp.backend.challenge;

import com.asapp.backend.challenge.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class AuthorizationControllerTest {

    @Autowired
    ApplicationContext appContext;


    @Test
    public void contextLoads() throws Exception {

        assertNotNull(appContext);


    }

}
