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
    private final Button secondaryButton = new Button();

    public GameOverlayView() {
        Rectangle bg = new Rectangle();
        bg.setFill(Color.rgb(238, 228, 218, 0.75));
        bg.widthProperty().bind(widthProperty());
        bg.heightProperty().bind(heightProperty());

        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        scoreLabel.setStyle("-fx-font-size: 18px");

        VBox box = new VBox(16, title, scoreLabel, primaryButton, secondaryButton   );
        box.setAlignment(Pos.CENTER);

        getChildren().addAll(bg, box);
        setAlignment(Pos.CENTER);
        setVisible(false);
    }

    public void showGameOver(int score) {
        title.setText("Game Over!");
        scoreLabel.setText("Your score: " + score);
        primaryButton.setText("Play Again");
        secondaryButton.setText("Exit");
        setVisible(true);
    }

    public void showWin(int score) {
        title.setText("You Win!");
        scoreLabel.setText("Your score: " + score);
        primaryButton.setText("Continue");
        secondaryButton.setText("Exit");
        setVisible(true);
    }

    public Button getPrimaryButton() {
        return primaryButton;
    }

    public Button getSecondaryButton() {
        return secondaryButton;
    }
}
