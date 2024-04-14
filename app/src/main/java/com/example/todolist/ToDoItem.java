package com.example.todolist;

public class ToDoItem {
    private String name;
    private boolean isCompleted; // Indicates whether the task is completed or ongoing

    public ToDoItem(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
