package uk.ac.liv.comp285.cw1.test;

import uk.ac.liv.comp285.cw1.CanvasFrame;
import uk.ac.liv.comp285.cw1.shapes.Circle;
import uk.ac.liv.comp285.cw1.shapes.Point;
import uk.ac.liv.comp285.cw1.shapes.Rectangle;
import uk.ac.liv.comp285.cw1.shapes.RegularPolygon;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CanvasFrame.showShapes();
		
		System.out.println("Checking out shapes");
		
		// draw 2 circles on the canvas
		Circle circle1=new Circle(200,400,100);
		Circle circle2=new Circle(200,400,100);
		circle2.setRotation(circle1.getLowerLeftPoint(), Math.PI/6);
		System.out.println("Collision between 2 circles is "+circle1.doesCollide(circle2));
		
		// draw 2 rectangles on the canvas
		Rectangle rectangle1=new Rectangle(400,400,200,200);
		Rectangle rectangle2=new Rectangle(400,400,200,200);
		rectangle2.setRotation(rectangle1.getLowerLeftPoint(),Math.PI/6);
		System.out.println("Collision between 2 rectangles is "+rectangle1.doesCollide(rectangle2));
		
		// draw 2 polygons on the canvas
		RegularPolygon regularPolygon1=new RegularPolygon(7,new Point(1000,400),100);
		RegularPolygon regularPolygon2=new RegularPolygon(6,new Point(1000,400),100);
		regularPolygon1.setRotation(regularPolygon2.getLowerLeftPoint(), -Math.PI);
		System.out.println("Collision between 2 polygons is "+regularPolygon1.doesCollide(regularPolygon2));
		
	}

}
