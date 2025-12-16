package com.pedro.game2048.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class TileView extends StackPane {
    private static final int TILE_SIZE = 100;
    private final Rectangle bg;
    private final Label label;
    private int value = 0;

    public TileView() {
        setPrefSize(TILE_SIZE, TILE_SIZE);
        setMinSize(TILE_SIZE, TILE_SIZE);
        setMaxSize(TILE_SIZE, TILE_SIZE);
        setAlignment(Pos.CENTER);

        bg = new Rectangle(TILE_SIZE, TILE_SIZE);
        bg.setArcWidth(12);
        bg.setArcHeight(12);
        bg.setStroke(Color.rgb(200, 200, 200, 0.0));

        label = new Label("");
        label.setFont(Font.font(50));
        label.setWrapText(false);

        setStyleForValue(0);

        DropShadow ds = new DropShadow();
        ds.setRadius(4);
        ds.setOffsetY(2);
        ds.setColor(Color.rgb(0, 0, 0, 0.15));
        setEffect(ds);

        getChildren().addAll(bg, label);
    }

    public void updateValue(int newValue){
        if (newValue == value) return;
        int old = value;
        value = newValue;

        if (old != 0 && newValue > old) {
            playMergeAnimation();
        }

        if (value == 0) {
            label.setText("");
        } else {
            label.setText(String.valueOf(value));
        }
        setStyleForValue(value);
    }

    private void setStyleForValue(int v){
        String bgColor;
        String textColor = "#776e65";
        int fontSize = 50;

        switch (v) {
            case 0:  bgColor = "#cdc1b4"; break;
            case 2:  bgColor = "#eee4da"; break;
            case 4:  bgColor = "#ede0c8"; break;
            case 8:  bgColor = "#f2b179"; textColor = "#f9f6f2"; fontSize = 50; break;
            case 16: bgColor = "#f59563"; textColor = "#f9f6f2"; fontSize = 50; break;
            case 32: bgColor = "#f67c5f"; textColor = "#f9f6f2"; fontSize = 48; break;
            case 64: bgColor = "#f65e3b"; textColor = "#f9f6f2"; fontSize = 48; break;
            case 128:bgColor = "#edcf72"; textColor = "#f9f6f2"; fontSize = 46; break;
            case 256:bgColor = "#edcc61"; textColor = "#f9f6f2"; fontSize = 46; break;
            case 512:bgColor = "#edc850"; textColor = "#f9f6f2"; fontSize = 44; break;
            case 1024:bgColor = "#edc53f"; textColor = "#f9f6f2"; fontSize = 42; break;
            case 2048:bgColor = "#edc22e"; textColor = "#f9f6f2"; fontSize = 42; break;
            default: bgColor = "#3c3a32"; textColor = "#f9f6f2"; fontSize = 42; break;
        }

        bg.setFill(Color.web(bgColor));
        label.setTextFill(Color.web(textColor));
        label.setFont(Font.font(fontSize));

        label.setMinWidth(USE_PREF_SIZE);
        label.setMinHeight(USE_PREF_SIZE);
        label.setMaxWidth(USE_PREF_SIZE);
        label.setMaxHeight(USE_PREF_SIZE);
        label.setTextOverrun(OverrunStyle.CLIP);
    }

    public void playSpawnAnimation(){
        ScaleTransition st = new ScaleTransition(Duration.millis(240), this);
        st.setFromX(0.2);
        st.setFromY(0.2);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setAutoReverse(true);
        st.play();
    }

    public void playMergeAnimation() {
        ScaleTransition st = new ScaleTransition(new Duration(100), this);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    public void resetTranslate() {
        setTranslateX(0);
        setTranslateY(0);
    }
}
