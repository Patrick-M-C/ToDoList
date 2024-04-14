package com.example.todolist;

// Class representing a ToDo item
public class ToDoItem {
    private String name; // Name of the ToDo item
    private boolean isCompleted; // Indicates whether the task is completed or ongoing

    // Constructor to initialize a ToDoItem with a name and completion status
    public ToDoItem(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    // Getter method for retrieving the name of the ToDo item
    public String getName() {
        return name;
    }

    // Setter method for setting the name of the ToDo item
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for retrieving the completion status of the ToDo item
    public boolean isCompleted() {
        return isCompleted;
    }

    // Setter method for setting the completion status of the ToDo item
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
