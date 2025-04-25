package com.example.todo.repository;

import com.example.todo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

    /**
     * チームごとのToDo件数を集計するネイティブSQL
     * - team, users, todo テーブルを結合して、チーム単位でカウント
     * - 結果は List<Map<String, Object>> 形式で取得
     *
     * @return チーム名とToDo件数の一覧
     */
    @Query(value = """
        SELECT t.name AS teamName, COUNT(td.id) AS todoCount
        FROM team t
        JOIN users u ON t.id = u.team_id
        JOIN todo td ON u.id = td.assignee_id
        GROUP BY t.name
        """, nativeQuery = true)
    List<Map<String, Object>> countTodosByTeam();
}
