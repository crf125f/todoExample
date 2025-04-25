package com.example.todo.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * ユーザー情報を管理するエンティティクラス。
 */
@Entity
@Table(name = "users") // データベースの 'user' テーブルにマッピング
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 主キー

    @Column(nullable = false)
    private String name; // ユーザー名。null不可

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false) // TeamEntityの主キー 'id' に対応する外部キー
    private TeamEntity team; // ユーザーが所属するチーム

    @OneToMany(mappedBy = "assignee") // TodoEntityの'assignee'フィールドに対応する一対多の関係
    private List<TodoEntity> todos; // このユーザーが担当するToDoのリスト

    // ゲッター・セッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public List<TodoEntity> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoEntity> todos) {
        this.todos = todos;
    }
}
