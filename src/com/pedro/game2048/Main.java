package com.pedro.game2048;

import com.pedro.game2048.app.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        new AppController(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
