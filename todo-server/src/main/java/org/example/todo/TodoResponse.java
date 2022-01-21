package org.example.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {

    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;  // response

    /*TodoEntity 를 파라미터로 받는 생성자를 추가
    생성자는 class 밖에서 사용하므로 public 을 붙인다.*/
    public TodoResponse(TodoModel todoModel) {
        this.id = todoModel.getId();
        this.title = todoModel.getTitle();
        this.order = todoModel.getOrder();
        this.completed = todoModel.getComplete();
        this.url = "http://localhost:8080/" + this.id; // 보통은 config, property 로 주소값 잡는다.
    }

}
