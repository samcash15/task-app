package com.task.taskmanager.task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

import java.time.LocalDate;

public class Task extends Node {
    public static final System.Logger LOGGER = System.getLogger(Task.class.getName());

    private final StringProperty header = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private LocalDate dueDate;
    private final BooleanProperty isCompleted = new SimpleBooleanProperty();


    public Task(String description, String header, LocalDate dueDate, Priority priority, boolean isCompleted) {
        this.description.set(description);
        this.header.set(header);
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted.set(isCompleted);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BooleanProperty isCompletedProperty() {
        return isCompleted;
    }

    public boolean getIsCompleted() {
        return isCompleted.get();
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted.set(isCompleted);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty headerProperty() {
        return header;
    }

    public String getHeader() {
        return header.get();
    }

    public void setHeader(String header) {
        this.header.set(header);
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private Priority priority;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
