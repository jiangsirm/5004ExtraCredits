package com.example.extracredits;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HelloController {
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

    @FXML
    public void initialize() {
        //set up default value in combo boxes
        complete.setValue(false);
        priority.setValue(Priority.LOW);


        //set up values in column
        TaskLog tasklog = new TaskLog();
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
                        return LocalDate.of(0, 0, 0);
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
        addText.setText("Task Added!");
        Duration textShow = Duration.seconds(1.5);
        Timeline timeline = new Timeline(new KeyFrame(textShow, event -> addText.setText("")));
        timeline.play();
    }
}