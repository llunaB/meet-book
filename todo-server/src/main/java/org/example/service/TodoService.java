package org.example.service;

import lombok.AllArgsConstructor;
import org.example.todo.TodoModel;
import org.example.todo.TodoRequest;
import org.example.service.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private TodoRepository todoRepository;

    // 1. 목록에 아이템을 추가
    // 2. 특정 아이템 조회
    // 3. 리스트 전체 목록 조회
    // 4. 리스트 목록 중 특정 아이템 수정
    // 5. 리스트 목록 중 특정 아이템 삭제
    // 6. 리스트 목록 전체 삭제

    // 메서드 시그니쳐 - request 를 받아서 entity 를 반환하는 메서드
    public TodoModel add(TodoRequest todoRequest) {
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(todoRequest.getTitle());
        todoModel.setOrder(todoRequest.getOrder());
        todoModel.setComplete(todoRequest.getCompleted());
        TodoModel entity = this.todoRepository.save(todoModel);
        return entity;
    }

    public TodoModel searchById(Long id) {
        TodoModel todoModel = this.todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return todoModel;
    }

    public List<TodoModel> searchAll() {
        List<TodoModel> todoRepositoryAll = this.todoRepository.findAll();
        return todoRepositoryAll;
    }

    public TodoModel updateById(Long id, TodoRequest request) {
        TodoModel todoModel = this.searchById(id); // 위에 적은 메서드
        if (request.getTitle() != null) {
            todoModel.setTitle(request.getTitle());
        }
        if (request.getOrder() != null) {
            todoModel.setOrder(request.getOrder());
        }
        if (request.getCompleted() != null) {
            todoModel.setComplete(request.getCompleted());
        }
        return this.todoRepository.save(todoModel);
    }

    public void deleteById(Long id) {
        this.todoRepository.deleteById(id); // 따로 반환값 없음
    }

    public void deleteAll() {
        this.todoRepository.deleteAll();
    }
}
