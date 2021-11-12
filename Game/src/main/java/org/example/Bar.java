package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle {
    Bar(int x, int y, int w, int h, Color color){
        super(w, h);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setFill(color);
    }
}

