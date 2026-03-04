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

        Label subtitle = new Label("Combine the numbers \nuntil you reach...");
        subtitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label title = new Label("2048");
        title.setStyle("-fx-font-size: 88px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Type your name");
        nameField.setMaxWidth(200);
        nameField.setMinHeight(30);

        Button playButton = new Button("PLAY");
        playButton.setPrefWidth(200);
        playButton.disableProperty()
                .bind(nameField.textProperty().isEmpty());
        playButton.getStyleClass().add("menu-button");
        playButton.setOnAction(e ->
                onPlay.accept(nameField.getText().trim())
        );

        Button scoreboardButton = new Button("SCOREBOARD");
        scoreboardButton.setPrefWidth(200);
        scoreboardButton.getStyleClass().add("menu-button");
        scoreboardButton.setOnAction(e -> onScoreboard.run());

        Button exitButton = new Button("EXIT");
        exitButton.setPrefWidth(200);
        exitButton.getStyleClass().add("quit-button");
        exitButton.setOnAction(e -> onExit.run());

        VBox root = new VBox(10,
                subtitle,
                title,
                nameField,
                playButton,
                scoreboardButton,
                exitButton
        );

        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #faf8ef");
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        return root;
    }
}
