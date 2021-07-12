package ru.stqa.pft.sandbox;

public class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p2) {
        return Math.sqrt(Math.pow((this.x -p2.x),2) + Math.pow((this.y-p2.y),2));
    }
}
