package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Vector;

public class Game2048 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public GridPane root;
    private Box[][] box;
    int[][] last_box;
    private final Color colorNumber = Color.GREEN;
    public Vector<Integer> v = new Vector<>();

    public Vector<String> color = new Vector<>();
    int N, size_Box;

    private Parent createContent(int level, int size_Box) {
        color.add("-fx-background-color: white");
        color.add("-fx-background-color: salmon");
        color.add("-fx-background-color: turquoise");
        color.add("-fx-background-color: yellow");
        color.add("-fx-background-color: skyblue");
        color.add("-fx-background-color: purple");
        color.add("-fx-background-color: orange");
        color.add("-fx-background-color: brown");
        color.add("-fx-background-color: pink");
        color.add("-fx-background-color: black");
        color.add("-fx-background-color: red");
        color.add("-fx-background-color: navy");

        root = new GridPane();
        this.N = level;
        this.size_Box = size_Box;
        box = new Box[N][N];
        last_box = new int[N][N];


        root.setHgap(5);
        root.setVgap(5);
        root.setStyle("-fx-background-color: grey;");

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                box[i][j] = new Box(new Button(""), 0);

                box[i][j].button.setPrefSize(size_Box, size_Box);
                box[i][j].button.setFont(new Font("Time New Roman", 30));
                root.add(box[i][j].button, j, i);
            }
        }

        createRandom();
        createRandom();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateColor();
            }
        };
        timer.start();

        return root;
    }

    private int cal(int value) {
        int cnt = 0;
        while (value > 1) {
            value /= 2;
            cnt++;
        }
        return cnt;
    }

    private void updateColor() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                int value = box[i][j].value;

                box[i][j].button.setTextFill(colorNumber);
                box[i][j].button.setStyle(color.get(cal(value)));

                if (value > 0) {
                    box[i][j].button.setText(String.valueOf(value));
                } else {
                    box[i][j].button.setText("");
                }
            }
        }
    }

    private void createRandom() {
        int x, y;
        do {
            x = (int) (Math.round((N - 1) * Math.random()));
            y = (int) (Math.round((N - 1) * Math.random()));
        } while (box[x][y].value != 0);

        box[x][y].value = (int) (Math.round((1) * Math.random()) + 1) * 2;
    }

    private void update_last_box() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                last_box[i][j] = box[i][j].value;
            }
        }
    }

    private void solve() {
        for (int j = 0; j < v.size(); ++j) {
            if (j < v.size() - 1 && v.get(j).equals(v.get(j + 1))) {
                v.set(j, v.get(j) * 2);
                v.remove(j + 1);
            }
        }
    }

    private void move_Left() {
        boolean check = false;
        for (int i = 0; i < N; ++i) {
            v.clear();
            for (int j = 0; j < N; ++j) {
                if (box[i][j].value != 0) {
                    v.add(box[i][j].value);
                }
            }

            solve();
            if (v.size() == 0) continue;

            int pos = 0;
            for (int j = 0; j < N; ++j) {
                if (pos < v.size()) {
                    if (box[i][j].value != v.get(pos)) {
                        if (!check) {
                            update_last_box();
                            check = true;
                        }
                    }
                    box[i][j].value = v.get(pos++);
                } else {
                    box[i][j].value = 0;
                }
            }
        }

        if (!check) return;
        createRandom();
        updateColor();
    }

    private void move_Right() {
        boolean check = false;
        for (int i = 0; i < N; ++i) {
            v.clear();
            for (int j = N - 1; j >= 0; --j) {
                if (box[i][j].value != 0) {
                    v.add(box[i][j].value);
                }
            }

            solve();
            if (v.size() == 0) continue;

            int pos = 0;
            for (int j = N - 1; j >= 0; --j) {
                if (pos < v.size()) {
                    if (box[i][j].value != v.get(pos)) {
                        if (!check) {
                            update_last_box();
                            check = true;
                        }
                    }
                    box[i][j].value = v.get(pos++);
                } else {
                    box[i][j].value = 0;
                }
            }
        }

        if (!check) return;
        createRandom();
        updateColor();
    }

    private void move_Down() {
        boolean check = false;
        for (int j = 0; j < N; ++j) {
            v.clear();
            for (int i = N - 1; i >= 0; --i) {
                if (box[i][j].value != 0) {
                    v.add(box[i][j].value);
                }
            }

            solve();
            if (v.size() == 0) continue;

            int pos = 0;
            for (int i = N - 1; i >= 0; --i) {
                if (pos < v.size()) {
                    if (box[i][j].value != v.get(pos)) {
                        if (!check) {
                            update_last_box();
                            check = true;
                        }
                    }
                    box[i][j].value = v.get(pos++);
                } else {
                    box[i][j].value = 0;
                }
            }
        }

        if (!check) return;
        createRandom();
        updateColor();
    }

    private void move_Up() {
        boolean check = false;
        for (int j = 0; j < N; ++j) {
            v.clear();
            for (int i = 0; i < N; ++i) {
                if (box[i][j].value != 0) {
                    v.add(box[i][j].value);
                }
            }

            solve();
            if (v.size() == 0) continue;

            int pos = 0;
            for (int i = 0; i < N; ++i) {
                if (pos < v.size()) {
                    if (box[i][j].value != v.get(pos)) {
                        if (!check) {
                            update_last_box();
                            check = true;
                        }
                    }
                    box[i][j].value = v.get(pos++);
                } else {
                    box[i][j].value = 0;
                }
            }
        }

        if (!check) return;
        createRandom();
        updateColor();
    }

    private void move_Undo() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                box[i][j].value = last_box[i][j];
            }
        }
        updateColor();
    }

    private static class Box {
        Button button;
        int value;

        Box(Button button, int value) {
            this.button = button;
            this.value = value;
        }
    }

    @Override
    public void start(Stage primaryStage) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        gridPane.add(new Label("YOUR FULL NAME : "), 0, 0);
        gridPane.add(new Label("CHOOSE LEVEL : "), 0, 1);

        TextField inputName = new TextField();
        inputName.setPromptText("INPUT NAME");
        gridPane.add(inputName, 1, 0);

        TextField inputLevel = new TextField();
        inputLevel.setPromptText("INPUT LEVEL < 7");
        gridPane.add(inputLevel, 1, 1);

        Button button = new Button("CONFIRM");
        gridPane.add(button, 1, 2);

        Scene scene1 = new Scene(gridPane);


        button.setOnAction(event -> {
            String Name = inputName.getText();
            String level = inputLevel.getText();

            if (!level.trim().isEmpty() && !Name.trim().isEmpty()) {
                Scene scene2 = new Scene(createContent(Integer.parseInt(level), 100));

                scene2.setOnKeyPressed(e -> {
                    switch (e.getCode()) {
                        case A:
                            move_Left();
                            break;

                        case D:
                            move_Right();
                            break;

                        case W:
                            move_Up();
                            break;

                        case S:
                            move_Down();
                            break;

                        case U:
                            move_Undo();
                            break;
                    }
                });

                box[0][0].button.setOnAction(event1 -> {
                    primaryStage.setScene(scene1);
                    primaryStage.setTitle("GAME_2048 BY SPM");
                });

                primaryStage.setTitle("Welcome " + Name + " to GAME_2048 --- SPM !!! ");
                primaryStage.setScene(scene2);
            }
        });

        primaryStage.setScene(scene1);
        primaryStage.setTitle("GAME_2048 BY SPM");
        primaryStage.show();
    }
}
