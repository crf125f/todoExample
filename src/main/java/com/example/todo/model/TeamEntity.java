package com.example.todo.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * チーム情報を管理するエンティティクラス。
 */
@Entity
@Table(name = "team") // データベースの 'team' テーブルにマッピング
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 主キー

    @Column(nullable = false, unique = true)
    private String name; // チーム名。null不可、一意制約

    @OneToMany(mappedBy = "team") // UserEntityの'team'フィールドに対応する一対多の関係
    private List<UserEntity> users; // このチームに所属するユーザーのリスト

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

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
