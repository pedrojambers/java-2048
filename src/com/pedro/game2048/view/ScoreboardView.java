package com.pedro.game2048.view;

import com.pedro.game2048.model.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;

public class ScoreboardView {

    private final TableView<Score> table = new TableView<>();
    private final Button backbutton = new Button("Back");

    public VBox createRoot(List<Score> scores) {

        Label title = new Label("SCOREBOARD");
        title.setStyle("-fx-font-size: 68px; -fx-font-weight: bold;");

        TableColumn<Score, String> nameCol = new TableColumn<>("Player");
        nameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getPlayerName()
                )
        );
        nameCol.setPrefWidth(200);

        TableColumn<Score, Number> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getScore()
                )
        );
        scoreCol.setPrefWidth(120);

        table.getColumns().addAll(nameCol, scoreCol);
        table.setMaxWidth(480);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Score> items =
                FXCollections.observableArrayList(scores);
        table.setItems(items);

        VBox root = new VBox(20, title, table, backbutton);
        root.setStyle("-fx-background-color: #faf8ef");
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(520, 700);

        return root;
    }

    public Button getBackbutton() {
        return backbutton;
    }
}
