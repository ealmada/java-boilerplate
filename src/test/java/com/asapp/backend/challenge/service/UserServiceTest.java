package com.asapp.backend.challenge.service;

import com.asapp.backend.challenge.Application;
import com.asapp.backend.challenge.repository.UserRepository;
import com.asapp.backend.challenge.resources.UserResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.as;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = Application.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createAUser_Ok() throws Exception {

        UserResource userResource = new UserResource();
        userResource.setUsername("emiliano");
        userResource.setPassword("1234haa");



        //assertEquals(userService.save(userResource), as(userResource));
        //System.out.println("HOLA");
        //assert (true);
        /*UserResource user = new UserResource();
        user.setUsername("juan");
        user.setPassword("HOLA");
        UserResource persistedUser = userService.save(user);
        assert user.getUsername().equals("juan");*/
    }

}
