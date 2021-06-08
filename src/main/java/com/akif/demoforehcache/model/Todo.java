package com.akif.demoforehcache.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "todo")
public class Todo implements Serializable {
    @Id
    private Long id;
    @Column
    private String todo;
    @Column
    private boolean is_todo_complete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isIs_todo_complete() {
        return is_todo_complete;
    }

    public void setIs_todo_complete(boolean is_todo_complete) {
        this.is_todo_complete = is_todo_complete;
    }
}
