package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EnemyBullet extends Rectangle {
    static final int[] WIDTH = new int[]{0, 6, 6, 6, 30};
    static final int[] HEIGHT = new int[]{0, 6, 6, 6, 530};
    static final int STEP = 20;

    private GameOOP.Direction direct;

    public void setDirect(GameOOP.Direction direct) {
        this.direct = direct;
    }

    public GameOOP.Direction getDirect() {
        return direct;
    }

    EnemyBullet(int x, int y, GameOOP.Direction direct) {
        super(WIDTH[GameOOP.level], HEIGHT[GameOOP.level]);
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
                Color.WHITE, Color.MEDIUMVIOLETRED, Color.CHARTREUSE, Color.DARKGREEN, Color.ORANGE
        };

        this.setFill(colorTable[GameOOP.level]);

    }
}
