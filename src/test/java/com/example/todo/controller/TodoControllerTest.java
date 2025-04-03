package com.example.todo.controller;

import com.example.todo.model.TodoDto;
import com.example.todo.model.TodoForm;
import com.example.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @Mock
    private Model model;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * テスト対象: showTodoList
     * テスト内容:
     * - Serviceから取得したToDoリストがModelに渡されていること
     * - 新しいフォームがModelに渡されていること
     * - 遷移先が "list" であること
     */
    @Test
    void showTodoList_returnsListWithForm() {
        List<TodoDto> mockList = Arrays.asList(new TodoDto(), new TodoDto());
        when(todoService.getTodoList()).thenReturn(mockList);

        String result = todoController.showTodoList(model);

        assertEquals("list", result); // 一覧画面に遷移することを確認
        verify(model).addAttribute("todos", mockList); // todos属性にサービス結果を設定
        verify(model).addAttribute(eq("todo"), any(TodoForm.class)); // 初期フォームが設定されていることを確認
    }
}
