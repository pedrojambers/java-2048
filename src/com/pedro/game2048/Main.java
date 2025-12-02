package com.pedro.game2048;

import com.pedro.game2048.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GameController controller = new GameController(stage);
        controller.showMainScene();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
