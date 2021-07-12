package ru.stqa.pft.sandbox;


public class MyFirstProgram {
	public static void main(String[] args) {
		System.out.println("Hello, World!");
		Square s = new Square(5);
		System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

		Rectangle r = new Rectangle(5,6);
		System.out.println(r.area());

		Point p1 = new Point(2,2);
		Point p2 = new Point(4,1);
		System.out.println("Расстояние между точкой (2,2) и (4,1) = " + p1.distance(p2));

		Point p3 = new Point(1,-2);
		Point p4 = new Point(2, -2);
		System.out.println("Расстояние между точкой (1,2) и (2,2) = " + p3.distance(p4));
	}




}