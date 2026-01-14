package com.pedro.game2048.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class MenuView {

    public Parent createRoot(
            Consumer<String> onPlay,
            Runnable onScoreboard,
            Runnable onExit
    ) {
        Label title = new Label("2048");
        title.setStyle("-fx-font-size: 88px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setMaxWidth(200);
        nameField.deselect();

        Button playButton = new Button("Play");
        playButton.setPrefWidth(200);;
        playButton.disableProperty()
                .bind(nameField.textProperty().isEmpty());

        playButton.setOnAction(e ->
                onPlay.accept(nameField.getText().trim())
        );

        Button scoreboardButton = new Button("Scoreboard");
        scoreboardButton.setPrefWidth(200);
        scoreboardButton.setOnAction(e -> onScoreboard.run());

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(e -> onExit.run());

        VBox root = new VBox(20,
                title,
                nameField,
                playButton,
                scoreboardButton,
                exitButton
        );

        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #faf8ef");

        return root;
    }
}
