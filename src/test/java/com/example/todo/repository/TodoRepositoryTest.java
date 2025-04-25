package com.example.todo.repository;

import com.example.todo.model.TeamEntity;
import com.example.todo.model.UserEntity;
import com.example.todo.model.TodoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // データベースの初期化
        todoRepository.deleteAll();
        userRepository.deleteAll();
        teamRepository.deleteAll();

        // Team Aの作成
        TeamEntity teamA = new TeamEntity();
        teamA.setName("Team A");
        teamRepository.save(teamA);

        // Team Bの作成
        TeamEntity teamB = new TeamEntity();
        teamB.setName("Team B");
        teamRepository.save(teamB);

        // Team Aにユーザーを追加
        UserEntity userA1 = new UserEntity();
        userA1.setName("User A1");
        userA1.setTeam(teamA);
        userRepository.save(userA1);

        UserEntity userA2 = new UserEntity();
        userA2.setName("User A2");
        userA2.setTeam(teamA);
        userRepository.save(userA2);

        // Team Bにユーザーを追加
        UserEntity userB1 = new UserEntity();
        userB1.setName("User B1");
        userB1.setTeam(teamB);
        userRepository.save(userB1);

        // User A1にToDoを3件追加
        TodoEntity todoA1 = new TodoEntity("Todo A1",false,userA1);
        todoRepository.save(todoA1);

        TodoEntity todoA2 = new TodoEntity("Todo A2", false, userA1);
        todoRepository.save(todoA2);

        TodoEntity todoA3 = new TodoEntity("Todo A3", false, userA1);
        todoRepository.save(todoA3);

        // User A2にToDoを2件追加
        TodoEntity todoA4 = new TodoEntity("Todo A4", false, userA2);
        todoRepository.save(todoA4);

        TodoEntity todoA5 = new TodoEntity("Todo A5", false, userA2);
        todoRepository.save(todoA5);

        // User B1にToDoを1件追加
        TodoEntity todoB1 = new TodoEntity("Todo B1", false, userB1);
        todoRepository.save(todoB1);
    }

    @Test
    void testCountTodosByTeam() {
        // メソッドの実行
        List<Map<String, Object>> results = todoRepository.countTodosByTeam();

        // 検証
        assertThat(results).hasSize(2);

        // Team Aの検証
        Map<String, Object> teamAResult = results.stream()
                .filter(result -> result.get("teamName").equals("Team A"))
                .findFirst()
                .orElseThrow();
        assertThat(teamAResult.get("todoCount")).isEqualTo(5L);

        // Team Bの検証
        Map<String, Object> teamBResult = results.stream()
                .filter(result -> result.get("teamName").equals("Team B"))
                .findFirst()
                .orElseThrow();
        assertThat(teamBResult.get("todoCount")).isEqualTo(1L);
    }
}
