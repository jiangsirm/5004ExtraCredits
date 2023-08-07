package com.example.extracredits;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.time.LocalDate;

public class HelloController {

    @FXML
    DatePicker date;

    @FXML
    TextField taskText;

    @FXML
    TextField category;

    @FXML
    private Label addText;

    @FXML
    TableView<Task> tableView;

    @FXML
    TableColumn<Task, Integer> idColumn;

    @FXML
    TableColumn<Task, String> textColumn;

    @FXML
    TableColumn<Task, Boolean> statusColumn;

    @FXML
    TableColumn<Task, LocalDate> dueColumn;

    @FXML
    TableColumn<Task, Priority> priorColumn;

    @FXML
    TableColumn<Task, String> catColumn;

    @FXML
    ComboBox<Boolean> complete;

    @FXML
    ComboBox<Priority> priority;

    private TaskLog tasklog = new TaskLog();

    @FXML
    public void initialize() {
        //set up default value in combo boxes
        date.setValue(LocalDate.now());
        complete.setValue(false);
        priority.setValue(Priority.LOW);


        //set up values in column
        ObservableList<Task> tasks = FXCollections.observableArrayList(tasklog.log);

        tableView.setItems(tasks);

        // Set up cell value factories to populate data in the columns
        idColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> tasklog.log.indexOf(cellData.getValue()) + 1
        ));

        textColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> cellData.getValue().getText()
                ));

        statusColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> cellData.getValue().isCompleted()
                ));

        dueColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> {
                    if (cellData.getValue().getDue().getYear() == -1){
                        return LocalDate.of(1, 1, 1);
                    }
                    return cellData.getValue().getDue();
                }
        ));

        priorColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> cellData.getValue().getPriority()
        ));

        catColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(
                () -> cellData.getValue().getCategory()
        ));

    }
    @FXML
    protected void onAddButtonClick() {
        String text = taskText.getText();
        boolean completed = complete.getValue();
        LocalDate dueDate = date.getValue();
        Priority taskPriority = priority.getValue();
        String taskCategory = category.getText();

        if (text.isBlank()) {
            addText.setText("Must Enter Text!");
            Duration textShow = Duration.seconds(1.5);
            Timeline timeline = new Timeline(new KeyFrame(textShow, event -> addText.setText("")));
            timeline.play();
        } else {

            Task addTo = new Task(text, dueDate, completed, taskPriority, taskCategory);
            tasklog.addTask(addTo);
            ObservableList<Task> tasks = FXCollections.observableArrayList(tasklog.log);
            tableView.setItems(tasks);
            tasklog.writeToFile();

            addText.setText("Task Added!");
            Duration textShow = Duration.seconds(1.5);
            Timeline timeline = new Timeline(new KeyFrame(textShow, event -> addText.setText("")));
            timeline.play();
        }

        taskText.clear();
        complete.setValue(false);
        date.getEditor().clear();
        priority.setValue(Priority.LOW);
        category.clear();
    }

    @FXML
    private void handleTableRowClicked() {
        // Get the selected row index
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        // Perform some action based on the selected row index (optional)
        tasklog.completeTask(selectedIndex);
        ObservableList<Task> tasks = FXCollections.observableArrayList(tasklog.log);
        tasklog.writeToFile();
        tableView.refresh();
    }
}