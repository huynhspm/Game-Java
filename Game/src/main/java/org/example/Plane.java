package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Plane extends Rectangle {
    static final int LIFE = 5;
    static final int WIDTH = 50;
    static final int HEIGHT = 50;
    static final int STEP = 20;

    private int life;
    private GameOOP.Direction direct;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setDirect(GameOOP.Direction direct) {
        this.direct = direct;
    }

    public GameOOP.Direction getDirect() {
        return direct;
    }

    Plane(int x, int y, GameOOP.Direction direct) {
        super(WIDTH, HEIGHT);
        setTranslateX(x);
        setTranslateY(y);

        this.direct = direct;
        this.life = LIFE;
        this.setFillColor();
    }

    public void move() {
        setTranslateX(getTranslateX() + GameOOP.directTable[this.getDirect().ordinal()][0] * STEP);
        setTranslateY(getTranslateY() + GameOOP.directTable[this.getDirect().ordinal()][1] * STEP);
    }

    public PlaneBullet shoot() {
        int x = (int) (this.getTranslateX() + (WIDTH - PlaneBullet.WIDTH) / 2);
        int y = (int) (this.getTranslateY() - PlaneBullet.HEIGHT);
        return new PlaneBullet(x, y, GameOOP.Direction.UP);
    }

    public void setFillColor() {
        Color[] colorTable = new Color[]{
                Color.WHITE, Color.GREEN, Color.LIGHTSKYBLUE, Color.YELLOWGREEN, Color.BLUEVIOLET
        };
        this.setFill(colorTable[GameOOP.level]);
    }
}
