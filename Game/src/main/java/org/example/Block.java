package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

    Block(int x, int y, int w, int h) {
        super(w, h);
        setTranslateX(x);
        setTranslateY(y);

        this.setFill(Color.BLACK);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
    }


    void setFillColor(int id) {
        Color[] c = new Color[]{
                Color.BLACK, Color.GREEN, Color.YELLOW, Color.BLUE,
                Color.PINK, Color.BROWN, Color.ORANGE, Color.PURPLE
        };

        this.setFill(c[id]);
    }

}