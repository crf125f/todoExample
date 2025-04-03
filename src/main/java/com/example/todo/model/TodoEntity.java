package com.example.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import com.example.todo.constant.*;

/**
 * Todo DBデータ取得用
 * Controller や View では使用せず、DBとのやり取りのみに使用する
 */
@Entity
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = MessageConstants.TITLE_REQUIRED)    //タイトルは必須です。
    private String title;

    private boolean done;

    public TodoEntity(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public TodoEntity(){
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return done; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDone(boolean done) { this.done = done; }
}
