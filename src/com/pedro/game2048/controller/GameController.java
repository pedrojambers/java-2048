package com.pedro.game2048.controller;

import com.pedro.game2048.app.AppController;
import com.pedro.game2048.model.Board;
import com.pedro.game2048.view.*;
import com.pedro.game2048.model.Score;
import com.pedro.game2048.service.ScoreService;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class GameController {
    private final Stage stage;
    private final String playerName;
    private final Runnable onExitToMenu;
    private final ScoreService scoreService = new ScoreService();
    private Board board;
    private GameView view;
    private Scene scene;
    private boolean gameEnded = false;

    public GameController(Stage stage, String playerName, Runnable onExitToMenu) {
        this.stage = stage;
        this.playerName = playerName;
        this.onExitToMenu = onExitToMenu;
        initNewGame();
    }

    private void initNewGame() {
        board = new Board();
        view = new GameView();
        scene = new Scene(view.createRoot(), 520, 700);

        scene.setOnKeyPressed(ev -> {
            if (gameEnded) return;

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

    private enum EndState {
        NONE, WIN, LOSE
    }

    private EndState endState = EndState.NONE;

    private void checkEndConditions() {
        if(gameEnded) return;

        if (board.hasWon()) {
            gameEnded = true;
            endState = EndState.WIN;
            view.showWin(board.getScore());
            bindOverlayActions();
            SaveGame("You reached 2048!");

        } else if (!board.canMove()) {
            gameEnded = true;
            endState = EndState.LOSE;
            view.showGameOver(board.getScore());
            bindOverlayActions();
            SaveGame("Game Over!");
        }
    }

    private void SaveGame(String message) {
        System.out.println(message);

        scoreService.saveScore(
                new Score(playerName, board.getScore())
        );
    }

    private void bindOverlayActions() {
        view.getOverlay().getPrimaryButton().setOnAction(e -> {
            view.getOverlay().setVisible(false);

            if (endState == EndState.LOSE) {
                gameEnded = false;
                board.reset();
                view.update(board);
            }
            else if (endState == EndState.WIN) {
                gameEnded = false;
            }

            endState = EndState.NONE;
        });

        view.getOverlay().getSecondaryButton().setOnAction(e -> {
            new AppController(stage).showMenu();
        });
    }

}
