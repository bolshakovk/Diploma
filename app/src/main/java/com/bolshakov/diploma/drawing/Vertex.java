package com.bolshakov.diploma.drawing;

public class Vertex {
    private int x;
    private int y;

    public Vertex(int x, int y) {
        setX(x);
        setY(y);
    }

    public Vertex() {
        this(100, 100);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x>=0){
            this.x = x;
        }

    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (x>=0){
            this.y = y;
        }
    }
}
