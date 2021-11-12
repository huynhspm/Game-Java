package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class GameSapXep extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private GridPane root;
    private Button[][] box;

    private final Color colorYes = Color.BLACK;
    private final Color colorNo = Color.YELLOW;
    private final Color colorBlank = Color.PINK;
    private final Color colorNumber = Color.GREEN;
    private int X, Y, N, size;

    private Parent createContent(int level) {
        root = new GridPane();
        N = level;
        size = 100;
        X = N;
        Y = N;
        box = new Button[N + 1][N + 1];

        root.setHgap(5);
        root.setVgap(5);
        root.setStyle("-fx-background-color: grey;");

        for (int i = 1; i <= N; ++i) {
            for (int j = 1; j <= N; ++j) {

                if (i == X && j == Y) box[i][j] = new Button("");
                else box[i][j] = new Button(String.valueOf(N * (j - 1) + i));

                box[i][j].setPrefSize(size, size);
                box[i][j].setFont(new Font("Time New Roman", 40));

                root.add(box[i][j], i, j);
            }
        }

        int i1, j1, i2, j2;
        for (int k = 1; k <= 2 * N * N; k++) {
            do {
                i1 = (int) (Math.round((N - 1) * Math.random() + 1));
                j1 = (int) (Math.round((N - 1) * Math.random() + 1));
            } while (i1 == N && j1 == N);

            do {
                i2 = (int) (Math.round((N - 1) * Math.random() + 1));
                j2 = (int) (Math.round((N - 1) * Math.random() + 1));
            } while ((i2 == N && j2 == N) || (i2 == i1 && j2 == j1));

            swap(box[i1][j1], box[i2][j2]);
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateColor();
                if (checkWin()) {
                    createContent(N + 1);
                }
            }
        };
        timer.start();

        return root;
    }

    private void updateColor() {
        for (int i = 1; i <= N; ++i) {
            for (int j = 1; j <= N; ++j) {
                if (box[i][j].getText() != "") {
                    if (Integer.parseInt(box[i][j].getText()) == N * (j - 1) + i) {
                        box[i][j].setStyle("-fx-background-color: black");
                    } else {
                        box[i][j].setStyle("-fx-background-color: yellow");
                    }
                } else {
                    box[i][j].setStyle("-fx-background-color: pink");
                }
                box[i][j].setTextFill(colorNumber);
            }
        }
    }

    private boolean checkWin() {
        for (int i = 1; i <= N; ++i) {
            for (int j = 1; j <= N; ++j) {
                if (box[i][j].getText() != "") {
                    if (Integer.parseInt(box[i][j].getText()) != N * (j - 1) + i) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void swap(Button button1, Button button2) {
        String text = button1.getText();
        button1.setText(button2.getText());
        button2.setText(text);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent(3));
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case D:
                    if (X > 1) {
                        swap(box[X][Y], box[X - 1][Y]);
                        X--;
                    }
                    break;

                case A:
                    if (X < N) {
                        swap(box[X][Y], box[X + 1][Y]);
                        X++;
                    }
                    break;

                case S:
                    if (Y > 1) {
                        swap(box[X][Y], box[X][Y - 1]);
                        Y--;
                    }
                    break;

                case W:
                    if (Y < N) {
                        swap(box[X][Y], box[X][Y + 1]);
                        Y++;
                    }
                    break;
            }
        });
        primaryStage.setTitle("Game Basic");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
