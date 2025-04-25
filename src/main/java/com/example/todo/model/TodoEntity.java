package com.example.todo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * ToDo項目を管理するエンティティクラス。
 */
@Entity
@Table(name = "todo") // データベースの 'todo' テーブルにマッピング
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 主キー

    @Column(nullable = false)
    private String title; // ToDoのタイトル。null不可

    private boolean done; // 完了フラグ。デフォルトはfalse

    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false) // UserEntityの主キー 'id' に対応する外部キー
    private UserEntity assignee; // このToDoの担当者

    @Column(updatable = false)
    private LocalDateTime createdAt; // 作成日時。更新不可

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public TodoEntity(int id, String title, boolean isDone){
        this.id = id;
        this.title = title;
        this.setDone(isDone);
    }

    public TodoEntity(String title, boolean done, UserEntity user) {
        this.title = title;
        this.done = done;
        this.user = user;
    }

    public TodoEntity(){ }

    // ゲッター・セッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public UserEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(UserEntity assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // createdAtは自動設定されるため、セッターは不要
}
