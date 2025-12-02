package com.pedro.game2048.view;

import com.pedro.game2048.model.Board;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameView {
    private final int TILE_SIZE = 100;
    private final GridPane gridPane = new GridPane();
    private final Label scoreLabel = new Label("Score: 0");
    private final Button restartButton = new Button("Restart");

    private final StackPane[][] cellPanes = new StackPane[4][4];

    public GameView() {
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Rectangle bg = new Rectangle(TILE_SIZE, TILE_SIZE);
                bg.getStyleClass().add("title-bg");
                Text num = new Text("");
                num.setFont(Font.font(20));
                StackPane sp = new StackPane(bg, num);
                sp.setPrefSize(TILE_SIZE, TILE_SIZE);
                cellPanes[r][c] = sp;
                gridPane.add(sp, c, r);
            }
        }
    }

    public VBox createRoot() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        scoreLabel.setFont(Font.font(18));
        root.getChildren().addAll(scoreLabel, gridPane, restartButton);
        return root;
    }

    public void update(Board board) {
        scoreLabel.setText(("Score: " + board.getScore()));
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                int val = board.getTile(r, c);
                StackPane sp = cellPanes[r][c];
                Text txt = (Text) sp.getChildren().get(1);
                Rectangle rect = (Rectangle) sp.getChildren().get(0);
                if (val == 0) {
                    txt.setText("");
                    rect.setStyle("-fx-fill: #cdc1b4; -fx-arc-width: 10; -fx-arc-height: 10;");
                } else {
                    txt.setText(String.valueOf(val));
                    rect.setStyle("-fx-fill: #eee4da; -fx-arc-width: 10; -fx-arc-height: 10;");
                }
            }
        }
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
