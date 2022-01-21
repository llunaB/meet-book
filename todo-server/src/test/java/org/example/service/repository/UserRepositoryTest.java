package org.example.service.repository;

import org.example.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() {
        userRepository.save(new User()); // 3번 유저
        userRepository.save(new User()); // 4번 유저

        System.out.println(">>>" + userRepository.findAll());
    }
}
