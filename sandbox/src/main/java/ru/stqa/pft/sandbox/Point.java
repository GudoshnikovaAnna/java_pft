package ru.stqa.pft.sandbox;

public class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.x -p2.x),2) + Math.pow((p1.y-p2.y),2));
    }
}
