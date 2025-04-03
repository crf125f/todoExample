package com.example.todo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.todo.constant.MessageConstants;

/**
 * 入力Form用オブジェクト
 */
public class TodoForm {

    private Integer id;

    @NotBlank(message = MessageConstants.TITLE_REQUIRED)
    @Size(max = 100, message = MessageConstants.TITLE_MAX_LENGTH)
    private String title;

    private boolean done;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}