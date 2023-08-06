package com.example.extracredits;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class HelloController {
    @FXML
    private Label addText;

    @FXML
    protected void onAddButtonClick() {
        addText.setText("Task Added!");
        Duration textShow = Duration.seconds(1.5);
        Timeline timeline = new Timeline(new KeyFrame(textShow, event -> addText.setText("")));
        timeline.play();
    }
}