package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

    @Test
    public void testArea() {
        Square s = new Square(5);
        Assert.assertEquals(s.area(), 20.0);
    }

    @Test
    public void testPointDistancePositive1() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(2,2);
        Assert.assertEquals( p1.distance(p2), 1.0, "Расстояние между точкой (1,2) и (2,2) считается неверно");
    }

    @Test
    public void testPointDistancePositive2() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,4);
        Assert.assertEquals( p1.distance(p2), 4.0, "Расстояние между точкой (0,0) и (0,4) считается неверно");
    }

    @Test
    public void testPointDistancePositive3() {
        Point p1 = new Point(0,2);
        Point p2 = new Point(4,-2);
        Assert.assertEquals( p1.distance(p2), Math.sqrt(32), "Расстояние между точкой (0,2) и (4,-2) считается неверно");
    }

    @Test
    public void testPointDistancePositive4() {
        Point p1 = new Point(7,4);
        Point p2 = new Point(-5,-4);
        Assert.assertEquals( p1.distance(p2), Math.sqrt(208), "Расстояние между точкой (7,4) и (-5,-4) считается неверно");
    }

    @Test
    public void testPointDistancePositive5() {
        Point p1 = new Point(-7,-4);
        Point p2 = new Point(-5,-4);
        Assert.assertEquals( p1.distance(p2), 2.0, "Расстояние между точкой (-7,-4) и (-5,-4) считается неверно");
    }
}
