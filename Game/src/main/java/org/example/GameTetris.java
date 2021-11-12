package org.example;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.Duration;

public class GameTetris extends Application {
    public static final int distance = 3;
    public static final int SIZE = 25;
    public static final int step = SIZE + distance;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 620;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initUI(primaryStage);
    }

    private final Pane root = new Pane();
    private Block[][] board, a;
    private int time = 0;
    private Shape shape, nextShape;

    private Parent createContent() {
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.setStyle("-fx-background-color: grey;");

        Label label = new Label("Next Shape");
        label.setTextFill(Color.LIGHTGREEN);
        label.setFont(new Font("Time New Roman", 25));
        label.setTranslateX(400);
        label.setTranslateY(70);

        a = new Block[4][4];
        for (int x = 0; x < 4; ++x)
            for (int y = 0; y < 4; ++y) {
                a[y][x] = new Block(400 + x * step + distance, 100 + y * step + distance, SIZE, SIZE);
                root.getChildren().add(a[y][x]);
            }

        board = new Block[22][11];
        for (int x = 0; x < 11; ++x) {
            for (int y = 0; y < 22; ++y) {
                board[y][x] = new Block(x * step + distance, y * step + distance, SIZE, SIZE);
                root.getChildren().add(board[y][x]);
            }
        }

        root.getChildren().add(label);

        return root;
    }

    private void initUI(Stage stage) {
        Scene scene = new Scene(createContent());
        createRandom();
        createNextShape();

        AnimationTimer timer = new MyTimer();
        timer.start();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case D:
                    delShape();
                    delShadow();
                    shape.moveRight();
                    if (checkShape(0, 0)) {
                        findShadow();
                    } else {
                        shape.moveLeft();
                    }
                    addShadow();
                    addShape();
                    break;

                case A:
                    delShape();
                    delShadow();
                    shape.moveLeft();
                    if (checkShape(0, 0)) {
                        findShadow();
                    } else {
                        shape.moveRight();
                    }
                    addShadow();
                    addShape();
                    break;

                case W:
                    delShape();
                    delShadow();
                    shape.rotate();
                    if (checkRotate()) {
                        findShadow();
                    } else {
                        shape.backRotate();
                    }
                    addShadow();
                    addShape();
                    break;

                case S:
                    delShape();
                    delShadow();
                    shape.moveDown();
                    if (checkShape(0, 0)) {
                        findShadow();
                    } else {
                        shape.moveUp();
                    }
                    addShadow();
                    addShape();
                    break;
                case ENTER:
                    delShadow();
                    delShape();
                    shape.setCoordX(shape.getShadowX());
                    shape.setCoordY(shape.getShadowY());
                    addShape();
                    break;

                case SPACE:
                    delShape();
                    delShadow();
                    createNextShape();
                    addShape();
                    addShadow();
                    break;

                default:
                    break;
            }
        });

        stage.setScene(scene);
        stage.setTitle("TeTris by HUYNHSPM");
        stage.show();
    }

    private class MyTimer extends AnimationTimer {

        @Override
        public void handle(long l) {
            doHandle();
        }

        private void doHandle() {
            if (++time % 30 == 0) solveGame();
        }
    }

    private void solveGame() {
        if (!checkDown()) {
            solveBoard();
            delShadow();
            createNextShape();
        } else {
            shape.moveDown();
            addShape();
        }
    }


    private boolean checkDown() {
        delShape();
        for (int i = 0; i < 4; ++i) {
            int x = shape.getCoordX() + shape.getX(i);
            int y = shape.getCoordY() + shape.getY(i);

            if (y >= 21 || board[y + 1][x].getFill() != Color.BLACK) {
                addShape();
                return false;
            }
        }
        return true;
    }

    private boolean checkShape(int dx, int dy) {
        for (int i = 0; i < 4; ++i) {
            int x = shape.getCoordX() + shape.getX(i) + dx;
            int y = shape.getCoordY() + shape.getY(i) + dy;

            if (x < 0 || x > 10 || y < 0 || y > 21) return false;
            if (board[y][x].getFill() != Color.BLACK) return false;
        }

        shape.setCoordX(shape.getCoordX() + dx);
        shape.setCoordY(shape.getCoordY() + dy);
        return true;
    }

    private boolean checkRotate() {
        if (checkShape(0, 0)) return true;
        if (checkShape(1, 0)) return true;
        if (checkShape(-1, 0)) return true;
        if (checkShape(0, 1)) return true;
        return checkShape(0, -1);
    }

    private void createRandom() {
        if (nextShape != null) {
            delNextShape();
        }

        nextShape = new Shape(1, 1);
        nextShape.setRandomShape();

        addNextShape();
    }

    private void delNextShape() {
        for (int i = 0; i < 4; ++i) {
            int x = nextShape.getCoordX() + nextShape.getX(i);
            int y = nextShape.getCoordY() + nextShape.getY(i);

            a[y][x].setFillColor(0);
        }
    }

    private void addNextShape() {
        for (int i = 0; i < 4; ++i) {
            int x = nextShape.getCoordX() + nextShape.getX(i);
            int y = nextShape.getCoordY() + nextShape.getY(i);

            a[y][x].setFillColor(nextShape.getShape().ordinal());
        }
    }

    private void createNextShape() {
        shape = new Shape(5, 1);
        shape.setShape(nextShape.getShape());
        createRandom();

        findShadow();

        addShadow();
        addShape();
    }

    private void findShadow() {
        int x = shape.getCoordX();
        int y = shape.getCoordY();

        while (checkDown()) {
            shape.moveDown();
        }
        delShape();

        shape.setShadowX(shape.getCoordX());
        shape.setShadowY(shape.getCoordY());

        shape.setCoordX(x);
        shape.setCoordY(y);
    }

    private void solveBoard() {
        for (int y = 0; y < 22; ++y) {
            boolean check = false;

            for (int x = 0; x < 11; ++x) {
                if (board[y][x].getFill() == Color.BLACK) {
                    check = true;
                    break;
                }
            }

            if (check) continue;

            for (int j = y; j > 0; --j) {
                for (int x = 0; x < 11; ++x) {
                    board[j][x].setFill(board[j - 1][x].getFill());
                }
            }
        }
    }

    private void delShadow() {
        for (int i = 0; i < 4; ++i) {
            int x = shape.getShadowX() + shape.getX(i);
            int y = shape.getShadowY() + shape.getY(i);
            board[y][x].setStroke(Color.BLACK);
        }
    }

    private void delShape() {
        for (int i = 0; i < 4; ++i) {
            int x = shape.getCoordX() + shape.getX(i);
            int y = shape.getCoordY() + shape.getY(i);
            board[y][x].setFillColor(0);
        }
    }

    private void addShadow() {
        for (int i = 0; i < 4; ++i) {
            int x = shape.getShadowX() + shape.getX(i);
            int y = shape.getShadowY() + shape.getY(i);
            board[y][x].setStroke(Color.RED);
            board[y][x].setStrokeWidth(2);
        }
    }

    private void addShape() {
        for (int i = 0; i < 4; ++i) {
            int x = shape.getCoordX() + shape.getX(i);
            int y = shape.getCoordY() + shape.getY(i);
            board[y][x].setFillColor(shape.getShape().ordinal());
        }
    }

}
