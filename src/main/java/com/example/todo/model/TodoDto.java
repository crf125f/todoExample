package com.example.todo.model;

/**
 * ビジネスロック用のオブジェクト
 * 本サンプルでは表示用も兼ねる
 */
public class TodoDto {
    private int id;
    private String title;
    private boolean done;

    public TodoDto(){}
    public TodoDto(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

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
