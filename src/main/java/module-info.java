module Java2048 {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.pedro.game2048;
    exports com.pedro.game2048.view;
    exports com.pedro.game2048.controller;
    exports com.pedro.game2048.model;

    opens com.pedro.game2048 to javafx.fxml;
    opens com.pedro.game2048.view to javafx.fxml;
}