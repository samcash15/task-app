package com.task.taskmanager;

import com.task.taskmanager.task.Task;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.LocalDate;

public class TaskManagerController {
    @FXML
    private VBox mainVBox;

    @FXML
    private VBox taskInputSection;

    @FXML
    private ListView<Task> taskListView;

    @FXML
    DatePicker dueDatePicker = new DatePicker();

    /**
     * This method is called when the user selects the "Create Task" button.
     * The user will enter a Header, Description, Priority and Due Date field.
     * This creates a new instance of Task.java.
     */
    @FXML
    protected void onCreateTaskButton() {
        // Header field
        TextField headerInput = new TextField();
        headerInput.setPromptText("Enter task title");

        // Description field
        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("Enter task description");

        // Priority fields
        RadioButton highPriority = new RadioButton("High");
        highPriority.setStyle("-fx-text-fill: red;"); // Setting text color to red

        RadioButton mediumPriority = new RadioButton("Medium");
        mediumPriority.setStyle("-fx-text-fill: orange;"); // Setting text color to orange

        RadioButton lowPriority = new RadioButton("Low");
        lowPriority.setStyle("-fx-text-fill: green;"); // Setting text color to green

        ToggleGroup priorityGroup = new ToggleGroup();
        highPriority.setToggleGroup(priorityGroup);
        mediumPriority.setToggleGroup(priorityGroup);
        lowPriority.setToggleGroup(priorityGroup);

        VBox priorityVBox = new VBox(10, highPriority, mediumPriority, lowPriority);

        // Due date field
        LocalDate dueDate = dueDatePicker.getValue();
        dueDatePicker.setPromptText("Enter due date");

        Button submitButton = onSubmitButton(headerInput, descriptionInput, dueDatePicker, priorityGroup);

        taskInputSection.getChildren().addAll(headerInput, descriptionInput, dueDatePicker, priorityVBox, submitButton);
    }

    /**
     * Once the user has finished filling in the new Task, the user will press the submit button. This is
     * when the new task will be created with all the given information.
     *
     * @param headerInput TextField
     * @param descriptionInput TextField
     * @param dueDatePicker DatePicker
     * @param priorityGroup ToggleGroup
     * @return Button
     */
    private Button onSubmitButton(TextField headerInput, TextField descriptionInput, DatePicker dueDatePicker, ToggleGroup priorityGroup) {
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String header = headerInput.getText();
            String description = descriptionInput.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            RadioButton selectedPriorityRadioButton = (RadioButton) priorityGroup.getSelectedToggle();
            Task.Priority priority = Task.Priority.valueOf(selectedPriorityRadioButton.getText().toUpperCase());

            Task newTask = new Task(description, header, dueDate, priority, false);

            taskListView.getItems().add(newTask);

            taskInputSection.getChildren().clear();
        });
        return submitButton;
    }

    @FXML
    public void initialize() {
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    StringBuilder taskLabelBuilder = new StringBuilder();

                    if (task.getHeader() != null) {
                        taskLabelBuilder.append(task.getHeader());
                    }

                    if (task.getDescription() != null) {
                        if (!taskLabelBuilder.isEmpty()) {
                            taskLabelBuilder.append(": ");
                        }
                        taskLabelBuilder.append(task.getDescription());
                    }

                    if (task.getDueDate() != null) {
                        if (!taskLabelBuilder.isEmpty()) {
                            taskLabelBuilder.append(" - DUE: ");
                        }
                        taskLabelBuilder.append(task.getDueDate());
                    }

                    if (task.getPriority() != null) {
                        if (!taskLabelBuilder.isEmpty()) {
                            taskLabelBuilder.append(" - ");
                        }
                        taskLabelBuilder.append(task.getPriority());
                    }

                    // Create the label with the constructed string
                    Label taskLabel = new Label(taskLabelBuilder.toString());

                    CheckBox completeCheckBox = new CheckBox();
                    Button deleteButton = new Button("X");

                    // Bind the checkboxes selected property to the task's completion status
                    completeCheckBox.selectedProperty().bindBidirectional(task.isCompletedProperty());
                    Tooltip completeTooltip = new Tooltip("Mark task as complete");
                    Tooltip.install(completeCheckBox, completeTooltip);


                    // Bind the text fill property to the task's completion status
                    taskLabel.textFillProperty().bind(Bindings.when(task.isCompletedProperty())
                            .then(Color.GREEN)
                            .otherwise(Color.BLACK));

                    Tooltip deleteTooltip = new Tooltip("Delete task");
                    Tooltip.install(deleteButton, deleteTooltip);

                    deleteButton.setOnAction(event -> {
                        getListView().getItems().remove(task);
                    });

                    hBox.getChildren().addAll(taskLabel, completeCheckBox, deleteButton);
                    setGraphic(hBox);
                }
            }
        });
    }

}