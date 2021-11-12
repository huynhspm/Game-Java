package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Enemy extends Rectangle {
    static final int[] LIFE = new int[]{0, 5, 10, 15, 20};
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

    Enemy(int x, int y, GameOOP.Direction direct) {
        super(WIDTH, HEIGHT);
        setTranslateX(x);
        setTranslateY(y);

        this.direct = direct;
        this.life = LIFE[GameOOP.level];
        this.setFillColor();
    }


    public void move() {
        setTranslateX(getTranslateX() + GameOOP.directTable[getDirect().ordinal()][0] * STEP);
        setTranslateY(getTranslateY() + GameOOP.directTable[getDirect().ordinal()][1] * STEP);
    }

    public EnemyBullet shoot() {
        int x = (int) (this.getTranslateX() + (WIDTH - EnemyBullet.WIDTH[GameOOP.level]) / 2);
        int y = (int) (this.getTranslateY() + HEIGHT);

        GameOOP.Direction direction = GameOOP.Direction.DOWN;

        if (GameOOP.level == 2 || GameOOP.level == 3) {
            int rand = Math.abs(new Random().nextInt()) % 2;
            if (rand == 0) {
                direction = GameOOP.Direction.LEFT_DOWN;
            } else {
                direction = GameOOP.Direction.RIGHT_DOWN;
            }
        }

        return new EnemyBullet(x, y, direction);
    }

    public void setFillColor() {
        Color[] colorTable = new Color[]{
                Color.WHITE, Color.BLACK, Color.BROWN, Color.PURPLE, Color.DARKMAGENTA
        };
        this.setFill(colorTable[GameOOP.level]);
    }
}
