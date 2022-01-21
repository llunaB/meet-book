package org.example.todo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TodoModelTest {
    @Test
    void Test() {
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle("title");
        todoModel.setOrder(10L);
        todoModel.setComplete(true);
        todoModel.setCreatedAt(LocalDateTime.now());
        todoModel.setUpdatedAt(LocalDateTime.now());

        TodoModel todoModel1 = new TodoModel(null, "title1", 10L, true, LocalDateTime.now(), LocalDateTime.now());
        TodoModel todoModel2 = new TodoModel("title", 10L, true);

        TodoModel.TodoModelBuilder todoModel3 = TodoModel.builder()
                .title("title")
                .order(10L)
                .complete(true);

        System.out.println(">>> " + todoModel.toString());
    }

}