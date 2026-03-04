package com.pedro.game2048.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameOverlayView extends StackPane {

    private final Label title = new Label();
    private final Label scoreLabel = new Label();
    private final Button primaryButton = new Button();

    public GameOverlayView() {
        Rectangle bg = new Rectangle();
        bg.setFill(Color.rgb(238, 228, 218, 0.75));
        bg.widthProperty().bind(widthProperty());
        bg.heightProperty().bind(heightProperty());

        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        scoreLabel.setStyle("-fx-font-size: 18px");

        VBox box = new VBox(16, title, scoreLabel, primaryButton);
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        getChildren().addAll(bg, box);
        setAlignment(Pos.CENTER);
        setVisible(false);
    }

    public void showGameOver(int score) {
        title.setText("GAME OVER!");
        scoreLabel.setText("YOUR SCORE: " + score);
        primaryButton.setText("PLAY AGAIN");
        primaryButton.getStyleClass().add("menu-button");

        setVisible(true);
    }

    public void showWin(int score) {
        title.setText("YOU WIN!");
        scoreLabel.setText("YOUR SCORE: " + score);
        primaryButton.setText("CONTINUE PLAYING?");
        primaryButton.getStyleClass().add("menu-button");

        setVisible(true);
    }

    public Button getPrimaryButton() {
        return primaryButton;
    }

}
