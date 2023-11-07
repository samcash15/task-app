package com.task.taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TaskManagerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("task-manager.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000 , 750);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/task/taskmanager/styles.css")).toExternalForm());
        stage.setTitle("Task App");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}