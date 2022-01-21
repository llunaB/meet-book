package org.example.service.repository;

import org.example.todo.TodoModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void crud() {
        todoRepository.save(new TodoModel());

        System.out.println(">>>" + todoRepository.findAll());
    }

}