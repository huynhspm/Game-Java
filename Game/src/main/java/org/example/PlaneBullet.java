package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlaneBullet extends Rectangle {
    static final int WIDTH = 6;
    static final int HEIGHT = 6;
    static final int STEP = 20;

    private final GameOOP.Direction direct;

    public GameOOP.Direction getDirect() {
        return direct;
    }

    PlaneBullet(int x, int y, GameOOP.Direction direct) {
        super(WIDTH, HEIGHT);
        setTranslateX(x);
        setTranslateY(y);

        this.direct = direct;
        setFillColor();
    }

    public void move() {
        setTranslateX(getTranslateX() + GameOOP.directTable[this.getDirect().ordinal()][0] * STEP);
        setTranslateY(getTranslateY() + GameOOP.directTable[this.getDirect().ordinal()][1] * STEP);
    }

    public void setFillColor() {
        Color[] colorTable = new Color[]{
                Color.WHITE, Color.YELLOW, Color.PINK, Color.GOLD, Color.CYAN
        };

        this.setFill(colorTable[GameOOP.level]);

    }
}
