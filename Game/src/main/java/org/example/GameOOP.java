package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameOOP extends Application {

    private final Pane root = new Pane();
    static final int SCREEN_WIDTH = 810;
    static final int SCREEN_HEIGHT = 590;
    static int level = 0, t = 0;

    private Plane plane;
    private Enemy boss;
    private Bar lifeBoss;
    private Bar bar;

    protected enum Direction {
        STATIC, UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN
    }

    static int[][] directTable = new int[][]{{0, 0},
            {0, -1}, {0, 1}, {-1, 0}, {1, 0},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initUI(primaryStage);
    }

    private void initUI(Stage stage) {
        Scene scene = new Scene(createContent());
        AnimationTimer timer = new MyTimer_GameOOP();
        timer.start();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    if (plane.getTranslateX() >= Plane.STEP) {
                        plane.setDirect(Direction.LEFT);
                    }
                    break;
                case D:
                    if (plane.getTranslateX() + plane.getWidth() + Plane.STEP <= SCREEN_WIDTH) {
                        plane.setDirect(Direction.RIGHT);
                    }
                    break;
                case W:
                    if (plane.getTranslateY() >= Plane.STEP) {
                        plane.setDirect(Direction.UP);
                    }
                    break;
                case S:
                    if (plane.getTranslateY() + plane.getHeight() + Plane.STEP <= SCREEN_HEIGHT) {
                        plane.setDirect(Direction.DOWN);
                    }
                    break;
            }
            plane.move();
            plane.setDirect(Direction.STATIC);
        });

        stage.setScene(scene);
        stage.setTitle("GAME by HUYNHSPM");
        stage.show();
    }

    private Parent createContent() {
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.setStyle("-fx-background-color: grey;");
        nextLevel();
        return root;
    }


    class MyTimer_GameOOP extends AnimationTimer {

        @Override
        public void handle(long l) {
            doHandle();
        }

        private void doHandle() {
            if (t % 8 == 0) {
                updateGame();
            }
            t++;
        }
    }

    private void updateGame() {
        Move_Boss();
        Move_Bullet();
        Solve_Collision();
        if (boss.getLife() <= 0) nextLevel();
        Shoot();

        root.getChildren().remove(lifeBoss);
        int w = (int) (boss.getWidth() * boss.getLife()) / Enemy.LIFE[level];
        lifeBoss = new Bar((int) boss.getTranslateX(), 2, w, 5, Color.RED);
        root.getChildren().add(lifeBoss);
    }

    private void Shoot() {
        if (plane.getLife() > 0 && t % 16 == 0) root.getChildren().add(plane.shoot());
        if (boss.getLife() > 0) {
            if ((GameOOP.level == 4 && t % 40 == 0) ||
                    (GameOOP.level == 3 && t % 100 == 0) ||
                    (GameOOP.level <= 2 && t % 20 == 0)) {
                root.getChildren().add(boss.shoot());
            }
        }
    }

    private void Move_Boss() {
        if (boss.getTranslateX() <= 0) boss.setDirect(Direction.RIGHT);
        else if (boss.getTranslateX() + boss.getWidth() >= SCREEN_WIDTH) boss.setDirect(Direction.LEFT);
        else if (level >= 3) {
            int rand = Math.abs(new Random().nextInt()) % 5;
            if (rand < 2) {
                boss.setDirect(Direction.RIGHT);
            } else {
                boss.setDirect(Direction.LEFT);
            }
        }
        boss.move();
    }

    private void nextLevel() {
        enemyBullets().forEach(enemyBullet -> root.getChildren().remove(enemyBullet));

        planeBullets().forEach(planeBullet -> root.getChildren().remove(planeBullet));

        root.getChildren().removeAll(plane, boss);
        level++;
        plane = new Plane(500, 500, Direction.STATIC);
        boss = new Enemy(400, 10, Direction.RIGHT);
        root.getChildren().addAll(plane, boss);

        if (level == 3) {
            int y = (int) (boss.getTranslateY() + boss.getHeight());
            bar = new Bar(0, y, SCREEN_WIDTH, EnemyBullet.HEIGHT[level], Color.GREEN);
            root.getChildren().add(bar);
        } else if (level == 4) {
            root.getChildren().remove(bar);
        }
    }

    private List<PlaneBullet> planeBullets() {
        return root.getChildren().stream().filter((e -> e instanceof PlaneBullet)).map(n -> (PlaneBullet) n).collect(Collectors.toList());
    }

    private List<EnemyBullet> enemyBullets() {
        return root.getChildren().stream().filter((e -> e instanceof EnemyBullet)).map(n -> (EnemyBullet) n).collect(Collectors.toList());
    }

    private void Move_Bullet() {
        enemyBullets().forEach(enemyBullet -> {
            enemyBullet.move();

            if (level == 3) {
                int rand = Math.abs(new Random().nextInt()) % 3;
                if (enemyBullet.getTranslateX() < 0) {
                    if (rand == 0) {
                        enemyBullet.setDirect(Direction.RIGHT_UP);
                    } else if (rand == 1) {
                        enemyBullet.setDirect(Direction.RIGHT_DOWN);
                    } else {
                        enemyBullet.setDirect(Direction.RIGHT);
                    }

                    enemyBullet.move();
                    enemyBullet.move();
                } else if (enemyBullet.getTranslateX() + enemyBullet.getWidth() > SCREEN_WIDTH) {
                    if (rand == 0) {
                        enemyBullet.setDirect(Direction.LEFT_UP);
                    } else if (rand == 1) {
                        enemyBullet.setDirect(Direction.LEFT_DOWN);
                    } else {
                        enemyBullet.setDirect(Direction.LEFT);
                    }

                    enemyBullet.move();
                    enemyBullet.move();
                } else if (enemyBullet.getTranslateY() + enemyBullet.getHeight() > SCREEN_HEIGHT) {
                    if (rand == 0) {
                        enemyBullet.setDirect(Direction.LEFT_UP);
                    } else if (rand == 1) {
                        enemyBullet.setDirect(Direction.RIGHT_UP);
                    } else {
                        enemyBullet.setDirect(Direction.UP);
                    }

                    enemyBullet.move();
                    enemyBullet.move();
                } else if (enemyBullet.getTranslateY() < bar.getTranslateY() + bar.getHeight()) {
                    if (rand == 0) {
                        enemyBullet.setDirect(Direction.LEFT_DOWN);
                    } else if (rand == 1) {
                        enemyBullet.setDirect(Direction.RIGHT_DOWN);
                    } else {
                        enemyBullet.setDirect(Direction.DOWN);
                    }

                    enemyBullet.move();
                    enemyBullet.move();
                }
            } else if (enemyBullet.getTranslateY() + enemyBullet.getHeight() > SCREEN_HEIGHT) {
                root.getChildren().remove(enemyBullet);
            } else if (level == 2) {
                if (enemyBullet.getTranslateX() < 0) {
                    enemyBullet.setDirect(Direction.RIGHT_DOWN);

                    enemyBullet.move();
                    enemyBullet.move();
                } else if (enemyBullet.getTranslateX() + enemyBullet.getWidth() > SCREEN_WIDTH) {
                    enemyBullet.setDirect(Direction.LEFT_DOWN);

                    enemyBullet.move();
                    enemyBullet.move();
                }
            }

        });

        planeBullets().forEach(planeBullet -> {
            planeBullet.move();
            if (planeBullet.getTranslateY() < 0) {
                root.getChildren().remove(planeBullet);
            }
        });
    }

    private void Solve_Collision() {
        enemyBullets().forEach(enemyBullet -> {

            if (enemyBullet.getBoundsInParent().intersects(plane.getBoundsInParent())) {
                plane.setLife(plane.getLife() - 1);
                root.getChildren().remove(enemyBullet);
            } else {
                planeBullets().forEach(planeBullet -> {
                    if (enemyBullet.getBoundsInParent().intersects(planeBullet.getBoundsInParent())) {
                        root.getChildren().remove(planeBullet);
                    }
                });
            }
        });

        planeBullets().forEach(planeBullet -> {
            if (planeBullet.getBoundsInParent().intersects(boss.getBoundsInParent())) {
                boss.setLife(boss.getLife() - 1);
                root.getChildren().remove(planeBullet);
            }
        });
    }
}

