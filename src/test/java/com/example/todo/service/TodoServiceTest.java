package com.example.todo.service;

import com.example.todo.model.TodoDto;
import com.example.todo.model.TodoEntity;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 対象メソッド: getTodoList
     * テスト内容: 件数の境界値（0件、1件、複数件）のリスト取得
     * 期待結果: 件数に応じた正しい件数のDtoリストが返る
     */
    @Test
    void getTodoList_returnsCorrectListByCount() {
        // 0件
        when(todoRepository.findAll()).thenReturn(List.of());
        List<TodoDto> result0 = todoService.getTodoList();
        assertEquals(0, result0.size());

        // 1件
        TodoEntity entity1 = new TodoEntity();
        entity1.setId(1);
        entity1.setTitle("Task 1");
        entity1.setDone(false);
        when(todoRepository.findAll()).thenReturn(List.of(entity1));
        List<TodoDto> result1 = todoService.getTodoList();
        assertEquals(1, result1.size());

        // 複数件
        TodoEntity entity2 = new TodoEntity();
        entity2.setId(2);
        entity2.setTitle("Task 2");
        entity2.setDone(true);
        when(todoRepository.findAll()).thenReturn(List.of(entity1, entity2));
        List<TodoDto> result2 = todoService.getTodoList();
        assertEquals(2, result2.size());
    }

    /**
     * 対象メソッド: addTodo
     * テスト内容: 正常なDtoを渡したとき保存されること、nullを渡したとき例外が出ること
     * 期待結果: save()が1回呼ばれる／NullPointerExceptionがスローされる
     */
    @Test
    void addTodo_behaviorsWithValidAndNullInput() {
        // 正常
        TodoDto dto = new TodoDto();
        dto.setTitle("Test Task");
        dto.setDone(false);
        todoService.addTodo(dto);
        verify(todoRepository, times(1)).save(any(TodoEntity.class));

        // null
        assertThrows(NullPointerException.class, () -> todoService.addTodo(null));
    }

    /**
     * 対象メソッド: toggleDone
     * テスト内容: 完了状態が true→false、false→true に反転すること。存在しないID指定時に例外が出ること
     * 期待結果: 状態が反転／IllegalStateExceptionがスローされる
     */
    @Test
    void toggleDone_switchesStatusAndHandlesInvalidId() {
        TodoEntity entity = new TodoEntity();
        entity.setId(1);
        entity.setTitle("Test");

        // false → true
        entity.setDone(false);
        when(todoRepository.findById(1)).thenReturn(Optional.of(entity));
        todoService.toggleDone(1);
        assertTrue(entity.isDone());

        // true → false
        entity.setDone(true);
        when(todoRepository.findById(2)).thenReturn(Optional.of(entity));
        todoService.toggleDone(2);
        assertFalse(entity.isDone());

        // IDが存在しない
        when(todoRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> todoService.toggleDone(999));
    }

    /**
     * 対象メソッド: updateTodo
     * テスト内容: 正常に更新されること、存在しないIDの場合に例外がスローされること
     * 期待結果: 内容が更新される／IllegalStateExceptionがスローされる
     */
    @Test
    void updateTodo_updatesOrThrowsIfNotFound() {
        // 正常系
        TodoDto dto = new TodoDto();
        dto.setId(1);
        dto.setTitle("Updated");
        dto.setDone(true);

        TodoEntity entity = new TodoEntity();
        entity.setId(1);
        entity.setTitle("Old");
        entity.setDone(false);

        when(todoRepository.findById(1)).thenReturn(Optional.of(entity));
        todoService.updateTodo(dto);
        assertEquals("Updated", entity.getTitle());
        assertTrue(entity.isDone());

        // 存在しないID
        TodoDto dtoInvalid = new TodoDto();
        dtoInvalid.setId(999);
        dtoInvalid.setTitle("Missing");
        when(todoRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> todoService.updateTodo(dtoInvalid));
    }

    /**
     * 対象メソッド: deleteTodo
     * テスト内容: 正常に削除されること、存在しないID指定時に例外がスローされること
     * 期待結果: deleteById()が呼ばれる／IllegalStateExceptionがスローされる
     */
    @Test
    void deleteTodo_deletesOrThrowsIfNotFound() {
        // 正常系
        TodoEntity entity = new TodoEntity();
        entity.setId(1);
        when(todoRepository.findById(1)).thenReturn(Optional.of(entity));
        todoService.deleteTodo(1);
        verify(todoRepository).deleteById(1);

        // IDが存在しない
        when(todoRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> todoService.deleteTodo(999));
    }
}
