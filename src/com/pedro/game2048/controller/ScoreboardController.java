package com.pedro.game2048.controller;

import com.pedro.game2048.model.Score;
import com.pedro.game2048.service.ScoreService;
import com.pedro.game2048.view.ScoreboardView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Comparator;
import java.util.List;

public class ScoreboardController {

    private final Stage stage;
    private final Runnable onBack;
    private final ScoreService scoreService = new ScoreService();

    public ScoreboardController(Stage stage, Runnable onBack) {
        this.stage = stage;
        this.onBack = onBack;
    }

    public void show() {
        List<Score> scores = scoreService.loadScores();

        scores.sort(Comparator
                .comparingInt(Score::getScore)
                .reversed()
        );

        ScoreboardView view = new ScoreboardView();
        Scene scene = new Scene(view.createRoot(scores));

        view.getBackbutton().setOnAction(e -> onBack.run());

        stage.setScene(scene);
    }
}
