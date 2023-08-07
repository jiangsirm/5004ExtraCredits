package com.example.extracredits;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

    public class HelloController {

        @FXML
        private TableView<Task> tableView;
        @FXML
        private TextField taskText;
        @FXML
        private ComboBox<String> complete;
        @FXML
        private DatePicker date;
        @FXML
        private ComboBox<String> priority;
        @FXML
        private TextField category;
        @FXML
        private Label addText;

        private ObservableList<Task> taskList = FXCollections.observableArrayList();

        @FXML
        private void initialize() {
            TableColumn<Task, Integer> idColumn = new TableColumn<>("ID:");
            TableColumn<Task, String> textColumn = new TableColumn<>("Description:");
            TableColumn<Task, Boolean> completedColumn = new TableColumn<>("Completed?");
            TableColumn<Task, LocalDate> dueColumn = new TableColumn<>("Due:");
            TableColumn<Task, Priority> priorityColumn = new TableColumn<>("Priority:");
            TableColumn<Task, String> categoryColumn = new TableColumn<>("Category:");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
            completedColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));
            dueColumn.setCellValueFactory(new PropertyValueFactory<>("due"));
            priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

            tableView.getColumns().addAll(idColumn, textColumn, completedColumn, dueColumn, priorityColumn, categoryColumn);

            complete.getItems().addAll("Completed", "Not Completed");
            priority.getItems().addAll("HIGH", "MEDIUM", "LOW");
        }

        @FXML
        private void onAddButtonClick() {
            String text = taskText.getText();

            boolean completed = complete.getValue().equals("Yes");
            LocalDate dueDate = date.getValue();
            Priority taskPriority = Priority.valueOf(priority.getValue());
            String taskCategory = category.getText();

            Task newTask = new Task(text, dueDate, completed, taskPriority, taskCategory);
            taskList.add(newTask);
            tableView.setItems(taskList);

            addText.setText("Task Added!");
            Duration textShow = Duration.seconds(1.5);
            Timeline timeline = new Timeline(new KeyFrame(textShow, event -> addText.setText("")));
            timeline.play();
            taskText.clear();
            complete.getSelectionModel().clearSelection();
            date.getEditor().clear();
            priority.getSelectionModel().clearSelection();
            category.clear();
            }
        }