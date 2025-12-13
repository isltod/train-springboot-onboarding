package com.asdf.todo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.asdf.todo.model.Todo;
import com.asdf.todo.repository.TodoInMemoryRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TodoServiceTests {
    @Autowired private TodoService todoService;

    @BeforeEach
    void setup() {
        todoService = new TodoService(new TodoInMemoryRepository());
        todoService.save(new Todo(null, "Test Todo 1", "Description 1", false));
        todoService.save(new Todo(null, "Test Todo 2", "Description 2", false));
    }

    @Test
    void testFindAll() {
        List<Todo> todos = todoService.findAll();
        assertThat(todos).hasSize(2);
    }

    @Test
    void testSaveTodo() {
        Todo todo = new Todo(null, "New Todo", "New Description", false);
        todoService.save(todo);
        assertThat(todoService.findAll()).hasSize(3);
    }

    @Test
    void testFindById() {
        Todo todo = todoService.findById(1L);
        assertThat(todo).isNotNull();
        assertThat(todo.getTitle()).isEqualTo("Test Todo 1");
    }

    @Test
    void testUpdateTodo() {
        Todo updatedTodo = new Todo(1L, "Updated Todo", "Updated Description", true);
        todoService.update(1L, updatedTodo);
        Todo todo = todoService.findById(1L);
        assertThat(todo.getTitle()).isEqualTo(updatedTodo.getTitle());
        assertThat(todo.getDescription()).isEqualTo(updatedTodo.getDescription());
        assertThat(todo.isCompleted()).isEqualTo(updatedTodo.isCompleted());
    }

    @Test
    void testDeleteTodo() {
        todoService.delete(1L);
        // @BeforeEach에서 각 테스트별로 실행 전에 2개를 만들어 넣으니까, 여기서는 한개...
        assertThat(todoService.findAll()).hasSize(1);
        assertThat(todoService.findById(1L)).isNull();
    }
}
