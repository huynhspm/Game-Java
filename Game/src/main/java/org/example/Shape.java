package org.example;

import java.util.Random;

public class Shape {
    protected enum Tetrominoe {
        NoShape, ZShape, SShape, LineShape,
        TShape, SquareShape, LShape, MirroredLShape
    }

    private Tetrominoe pieceShape;
    private final int [][] coords;
    private int coordX, coordY; // tọa độ ô trung tâm (0,0)
    private int shadowX, shadowY;

    public void setCoordX(int coordX) { this.coordX = coordX; }

    public void setCoordY(int coordY) { this.coordY = coordY; }

    public void setShadowY(int shadowY) { this.shadowY = shadowY; }

    public void setShadowX(int shadowX) { this.shadowX = shadowX; }

    public int getShadowX() { return shadowX; }

    public int getShadowY() { return shadowY; }

    public int getCoordX() { return coordX; }

    public int getCoordY() { return coordY; }

    public void moveDown(){
        coordY ++;
    }

    public void moveUp(){
        coordY --;
    }

    public void moveLeft(){ coordX --; }

    public void moveRight(){
        coordX ++;
    }

    public Shape(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;

        this.shadowX = coordX;
        this.shadowY = coordY;

        coords = new int[4][2];
        setShape(Tetrominoe.NoShape);
    }

    public void setShape(Tetrominoe shape){
        int[][][] coordsTable = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, -1}},
                {{0, 0}, {1, 0}, {0, -1}, {1, -1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
        };

        System.arraycopy(coordsTable[shape.ordinal()], 0, coords, 0, 4);
        pieceShape = shape;
    }

    Tetrominoe getShape() { return pieceShape; }

    private void setX(int index, int x){  coords[index][0] = x; }

    private void setY(int index, int y){  coords[index][1] = y; }

    public int getX(int index) {  return coords[index][0]; }

    public int getY(int index) {  return coords[index][1]; }

    public void setRandomShape() {
        var random = new Random();
        int x = Math.abs(random.nextInt()) % 7 + 1;

        Tetrominoe[] values = Tetrominoe.values();
        setShape(values[x]);
    }

    public void rotate() {
        if (pieceShape == Tetrominoe.SquareShape) { return; }

        for (int i = 0; i < 4; i++) {
            int newY = -getX(i);
            setX(i, getY(i));
            setY(i, newY);
        }
    }

    public void backRotate() {
        if (pieceShape == Tetrominoe.SquareShape) { return; }

        for (int i = 0; i < 4; i++) {
            setX(i, -getY(i));
            setY(i, getX(i));
        }
    }
}
