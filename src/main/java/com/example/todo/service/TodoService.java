package com.example.todo.service;

import com.example.todo.controller.TodoController;
import com.example.todo.model.TodoEntity;
import com.example.todo.model.TodoDto;
import com.example.todo.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

//    private int nextId = 1;  // IDのカウンタ
//    private List<Todo> todoList = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    /**
     * 表示用Todoリストを取得
     * @return  DBから取得したEntityをDto形式で変換して返却
     */
    public List<TodoDto> getTodoList() {
        log.info("Todoリスト取得開始");

        List<TodoEntity> entities = todoRepository.findAll();

        List<TodoDto> dtoList = entities.stream()
                .map(this::convertToDto)
                .toList();

        log.info("取得件数: {}", dtoList.size());
        return dtoList;
    }

    /**
     * チームごとのToDo件数を取得する
     * - Repository層のSQL実行メソッドを呼び出す
     *
     * @return チームごとのToDo件数のリスト（teamName, todoCount）
     */
    public List<Map<String, Object>> getTodoCountByTeam() {
        return todoRepository.countTodosByTeam();
    }


    /**
     * 新規Todoを追加（DTO → Entity に変換して保存）
     */
    public void addTodo(TodoDto dto) {
        log.info("Todo追加処理開始: title={}, done={}", dto.getTitle(), dto.isDone());

        TodoEntity todoEntity = this.convertToEntity(dto);
        this.todoRepository.save(todoEntity);
//        todo.setId(this.nextId++);
//        this.todoList.add(todo);entity
        log.info("Todo追加完了: id={}", todoEntity.getId());
    }

    /**
     * Todoの状態切り替え処理
     * @param id
     */
    public void toggleDone(int id){
        log.info("トグル処理開始: id={}", id);

        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("IDが見つかりません: {}", id);
                    return new IllegalStateException("IDが見つかりません。: " + id);
                });
        todoEntity.setDone(!todoEntity.isDone());
        todoRepository.save(todoEntity);

        log.info("トグル処理完了: id={}, 新しい状態={}", id, todoEntity.isDone());
    }

    /**
     * 指定のIDのTodoを削除
     * @param id
     */
    public void deleteTodo(int id) {
        log.info("削除処理開始: id={}", id);
        todoRepository.deleteById(id);
        log.info("削除完了: id={}", id);
    }


    /**
     * 指定のIDのTodoを返す
     * @param id
     * @return
     */
    public TodoDto findById(int id){
        log.info("ID指定取得開始: id={}", id);
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("IDが見つかりません: {}", id);
                    return new IllegalStateException("IDが見つかりません: " + id);
                });
        TodoDto dto = convertToDto(entity);
        log.info("取得完了: title={}, done={}", dto.getTitle(), dto.isDone());
        return dto;
    }


    /**
     * Todoを更新（DTO → Entity に変換して保存）
     */
    public void updateTodo(TodoDto dto){
        log.info("更新処理開始: id={}, title={}, done={}", dto.getId(), dto.getTitle(), dto.isDone());

        TodoEntity entity = convertToEntity(dto);
        this.todoRepository.save(entity);

        log.info("更新完了: id={}", entity.getId());
    }


    /**
     * Entity → DTO 変換
     */
    private TodoDto convertToDto(TodoEntity entity) {
        return new TodoDto(entity.getId(), entity.getTitle(), entity.isDone());
    }

    /**
     * DTO → Entity 変換
     */
    private TodoEntity convertToEntity(TodoDto dto) {
        return new TodoEntity(dto.getId(), dto.getTitle(), dto.isDone());
    }

}
