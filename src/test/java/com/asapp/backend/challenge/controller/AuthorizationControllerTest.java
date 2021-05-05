package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class AuthorizationControllerTest {

    @Autowired
    ApplicationContext appContext;


    @Test
    public void contextLoads() throws Exception {

        String[] beanNames = appContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        assertEquals(42, Integer.sum(19, 23));
        //System.out.println("HOLA");
        //assert (true);
        /*UserResource user = new UserResource();
        user.setUsername("juan");
        user.setPassword("HOLA");
        UserResource persistedUser = userService.save(user);
        assert user.getUsername().equals("juan");*/
    }

}
