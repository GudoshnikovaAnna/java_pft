package ru.stqa.pft.sandbox;


public class MyFirstProgram {
	public static void main(String[] args) {
		System.out.println("Hello, World!");
		Square s = new Square(5);
		System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

		Rectangle r = new Rectangle(5,6);
		System.out.println(r.area());

		Point p1 = new Point();
		Point p2 = new Point();
		p1.x = 2;
		p1.y = 2;
		p2.x = 4;
		p2.y = 1;
		System.out.println("Расстояние между точкой (2,2) и (4,1) = " + distance(p1, p2));

		Point p3 = new Point();
		Point p4 = new Point();
		p3.x = 1;
		p3.y = -2;
		p4.x = 2;
		p4.y = -2;
		System.out.println("Расстояние между точкой (1,2) и (2,2) = " + distance(p3, p4));
	}

	public static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow((p1.x -p2.x),2) + Math.pow((p1.y-p2.y),2));
	}


}