package org.example.service.repository;

import org.example.conference.Conference;
import org.example.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConferenceRepositoryTest {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    void crud() {
        conferenceRepository.save(new Conference());

        System.out.println(">>>" + conferenceRepository.findAll());
    }
}
