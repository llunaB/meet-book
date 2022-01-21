package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.todo.TodoModel;
import org.example.todo.TodoRequest;
import org.example.todo.TodoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin // CORS Issue prevent
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;  // member variable

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {  // body 부분을 모델과 맞춰줌

        if (ObjectUtils.isEmpty(request.getTitle()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getOrder()))
            request.setOrder(0L);

        if (ObjectUtils.isEmpty(request.getCompleted()))
            request.setCompleted(false);

        TodoModel result = this.todoService.add(request);  // 반환받은 것은 TodoEntity
        return ResponseEntity.ok(new TodoResponse(result)); // TodoResponse 를 거쳐서 TodoResponse 객체로 내려준다.
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {  // variable이 바인딩되는 패스의 이름
        TodoModel result = this.todoService.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        List<TodoModel> list = this.todoService.searchAll();
        List<TodoResponse> response = list.stream().map(TodoResponse::new)  // stream으로 쭉 돌면서 reponse로 매핑하고 그걸 리스트로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        TodoModel result = this.todoService.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        this.todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        this.todoService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
