package com.pedro.game2048.view;

import com.pedro.game2048.model.Board;
import com.pedro.game2048.model.Tile;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameView {
    private static final int SIZE = 4;
    private static final int TILE_SIZE = 100;
    private static final int GAP = 12;
    private static final int PADDING = 18;

    private static final int GRID_SIZE =
            SIZE * TILE_SIZE + (SIZE - 1) * GAP;

    private static final int BOARD_SIZE =
            GRID_SIZE + PADDING * 2;

    private final Label scoreLabel = new Label("Score: 0");
    private final Button restartButton = new Button("Restart");

    private final StackPane boardContainer = new StackPane();
    private final Pane tileLayer = new Pane();

    private final Map<Integer, TileView> tileViews = new HashMap<>();
    private final GameOverlayView overlay = new GameOverlayView();

    public GameView() {
        Rectangle boardBg = new Rectangle(BOARD_SIZE, BOARD_SIZE);
        boardBg.setArcWidth(20);
        boardBg.setArcHeight(20);
        boardBg.setFill(Color.web("#bbada0"));

        tileLayer.setMaxSize(BOARD_SIZE, BOARD_SIZE);
        tileLayer.setMouseTransparent(true);

        StackPane.setAlignment(tileLayer, Pos.CENTER);

        boardContainer.getChildren().addAll(boardBg, tileLayer, overlay);
        boardContainer.setAlignment(Pos.CENTER);
    }

    public VBox createRoot() {
        VBox root = new VBox(14);
        root.setAlignment(Pos.CENTER);

        scoreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

        root.getChildren().addAll(scoreLabel, boardContainer, restartButton);
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        return root;
    }

    public void update(Board board) {
        scoreLabel.setText("Score: " + board.getScore());

        Map<Integer, Boolean> stillAlive = new HashMap<>();

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Tile tile = board.getTile(r, c);
                if (tile == null) continue;

                stillAlive.put(tile.getId(), true);

                TileView tv = tileViews.get(tile.getId());

                if (tv == null) {
                    tv = new TileView();
                    tv.updateValue(tile.getValue());
                    tileViews.put(tile.getId(), tv);
                    tileLayer.getChildren().add(tv);

                    positionTile(tv, r, c);
                    tv.playSpawnAnimation();
                } else {
                    tv.updateValue(tile.getValue());
                }
            }
        }

        tileViews.entrySet().removeIf(entry -> {
            if (!stillAlive.containsKey(entry.getKey())) {
                tileLayer.getChildren().remove(entry.getValue());
                return true;
            }
            return false;
        });
    }

    public void animateSlide(
            Tile tile,
            int fromR, int fromC,
            int toR, int toC,
            Runnable onFinished
    ) {
        TileView tv = tileViews.get(tile.getId());
        if (tv == null) {
            onFinished.run();
            return;
        }

        double fromX = tileX(fromC);
        double fromY = tileY(fromR);
        double toX   = tileX(toC);
        double toY   = tileY(toR);

        TranslateTransition tt =
                new TranslateTransition(Duration.millis(140), tv);

        tt.setFromX(0);
        tt.setFromY(0);
        tt.setToX(toX - fromX);
        tt.setToY(toY - fromY);

        tt.setOnFinished(e -> {
            tv.setTranslateX(0);
            tv.setTranslateY(0);
            tv.setLayoutX(toX);
            tv.setLayoutY(toY);
            onFinished.run();
        });

        tt.play();
    }

    public void animateMoves(
            List<Board.Move> moves,
            Runnable onFinished
    ) {
        if (moves.isEmpty()) {
            onFinished.run();
            return;
        }

        final int[] remaining = { moves.size() };

        for (Board.Move move : moves) {
            animateSlide(
                    move.tile,
                    move.fromR,
                    move.fromC,
                    move.toR,
                    move.toC,
                    () -> {
                        remaining[0]--;
                        if (remaining[0] == 0) {
                            onFinished.run();
                        }
                    }
            );
        }
    }

    private void positionTile(TileView tv, int row, int col) {
        tv.setLayoutX(tileX(col));
        tv.setLayoutY(tileY(row));
    }

    private double tileX(int col) {
        return PADDING + col * (TILE_SIZE + GAP);
    }

    private double tileY(int row) {
        return PADDING + row * (TILE_SIZE + GAP);
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public void showGameOver(int score) {
        overlay.showGameOver(score);
    }

    public void showWin(int score) {
        overlay.showWin(score);
    }

    public GameOverlayView getOverlay() {
        return overlay;
    }

}
