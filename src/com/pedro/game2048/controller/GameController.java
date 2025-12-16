package com.pedro.game2048.controller;

import com.pedro.game2048.model.Board;
import com.pedro.game2048.view.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class GameController {
    private final Stage stage;
    private Board board;
    private GameView view;
    private Scene scene;

    public GameController(Stage stage) {
        this.stage = stage;
        initNewGame();
    }

    private void initNewGame() {
        board = new Board();
        view = new GameView();
        scene = new Scene(view.createRoot(), 520, 700);

        scene.setOnKeyPressed(ev -> {
            KeyCode code = ev.getCode();
            boolean moved = false;

            if (code == KeyCode.LEFT || code == KeyCode.A) moved = board.moveLeft();
            else if (code == KeyCode.RIGHT || code == KeyCode.D) moved = board.moveRight();
            else if (code == KeyCode.UP || code == KeyCode.W) moved = board.moveUp();
            else if (code == KeyCode.DOWN || code == KeyCode.S) moved = board.moveDown();

            if (moved) {
                view.animateMoves(board.getLastMoves(), () -> {
                    view.update(board);
                    checkEndConditions();
                });

                checkEndConditions();
            }
        });

        view.getRestartButton().setOnAction(e -> resetGame());
    }

    public void showMainScene() {
        stage.setTitle("2048 - JavaFX (Beta)");
        stage.setScene(scene);
        view.update(board);
        stage.show();
        scene.getRoot().requestFocus();
    }

    private void resetGame() {
        board.reset();
        view.update(board);
    }

    private void checkEndConditions() {
        if (board.hasWon()) {
            System.out.println("You reached 2048! (detected by controller)");
        } else if (!board.canMove()) {
            System.out.println("Game Over! (detected by controller)");
        }
    }

}
