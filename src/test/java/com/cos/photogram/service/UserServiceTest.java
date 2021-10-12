package com.cos.photogram.service;

import com.cos.photogram.domain.user.User;
import com.cos.photogram.domain.user.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private EntityManager em;


    @Test
    public void 회원가입() throws Exception {
        //given
        User user = new User();
        user.setName("Kim");
        user.setPassword("123");
        user.setEmail("asdf@adf.com");
        user.setName("sadf");

        //when
        User savedId = authService.회원가입(user);

        //then
        assertEquals(user, userRepository.findByUsername(savedId.getUsername()));


    }

    @Test
    public void 중복회원예외() throws Exception{
        //given
        User user1 = new User();
        user1.setUsername("Kim1");
        user1.setPassword("123");
        user1.setEmail("Kim1@naver.com");
        user1.setName("KimLeePark");

        User user2 = new User();
        user2.setUsername("Kim1");
        user2.setPassword("123");
        user2.setEmail("Kim1@naver.com");
        user2.setName("KimLeePark");

        //when
        authService.회원가입(user1);
        try {
            authService.회원가입(user2);
        } catch (Exception e) {
            return;
        }

        //then
        fail("예외가 발생해야 한다.");
    }
}