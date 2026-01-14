package com.pedro.game2048.app;

import com.pedro.game2048.controller.GameController;
import com.pedro.game2048.controller.ScoreboardController;
import com.pedro.game2048.view.MenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {
    private final Stage stage;

    public AppController(Stage stage) {
        this.stage = stage;
        showMenu();
    }

    public void showMenu() {
        MenuView menuView = new MenuView();

        Scene scene = new Scene(
                menuView.createRoot(
                        this::startGame,
                        this::showScoreboard,
                        () -> stage.close()
                ),
                520, 700
        );

        stage.setTitle("2048");
        stage.setScene(scene);
        stage.show();

    }

    private void startGame(String playerName) {
        GameController gameController =
                new GameController(stage, playerName, this::showMenu);
        gameController.showMainScene();
    }

    private void showScoreboard() {
        new ScoreboardController(stage, this::showMenu).show();
    }
}
