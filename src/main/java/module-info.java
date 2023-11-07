module com.task.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.task.taskmanager to javafx.fxml;
    exports com.task.taskmanager;
}