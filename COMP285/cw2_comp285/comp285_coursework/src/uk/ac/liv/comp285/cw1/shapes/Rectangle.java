package uk.ac.liv.comp285.cw1.shapes;

import java.awt.Graphics;
import java.util.ArrayList;

import uk.ac.liv.comp285.cw1.Shape;

public class Rectangle extends Shape {
	// for storing 4 vertices of the rectangle
	public ArrayList <Point> vertices;
	
	public Rectangle(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// initialize 4 vertices of the rectangle
		Point p1=new Point(x,y);
		Point p2=new Point(x+width,y);
		Point p3=new Point(x+width,y+height);
		Point p4=new Point(x,y+height);
		
		// add them to vertices
		vertices=new ArrayList<Point>();
		vertices.add(p1);
		vertices.add(p2);
		vertices.add(p3);
		vertices.add(p4);
	}

	private float x,y,width,height;

	@Override
	public float getArea() {
		return(width*height);
	}

	@Override
	public Point getLowerLeftPoint() {
		// min_x is the x of the lower left point
		float min_x=Float.MAX_VALUE;
		
		// min_y is the y of the lower left point
		float min_y=Float.MAX_VALUE;
		
		// find min_x and min_y by iterating vertices
		for(Point p: vertices) {
			min_x=Math.min(p.rotate(rotationOrigin, angle).getX(), min_x);
			min_y=Math.min(p.rotate(rotationOrigin, angle).getY(), min_y);
		}
		
		// return the lower left point
		return new Point(min_x,min_y);
	}

	@Override
	public Point getUpperRightPoint() {
		// max_x is the x of the upper right point
		float max_x=Float.MIN_VALUE;
		
		// max_y is the y of the upper right point
		float max_y=Float.MIN_VALUE;
		
		// find max_x and max_y by iterating vertices
		for(Point p: vertices) {
			max_x=Math.max(p.rotate(rotationOrigin, angle).getX(), max_x);
			max_y=Math.max(p.rotate(rotationOrigin, angle).getY(), max_y);
		}
		
		// return the upper right point
		return new Point(max_x,max_y);
	}

	@Override
	public void render(Graphics g) {
		// iterate vertices of the rectangle
		for(int i=0;i<vertices.size();i++) {
			if(i==vertices.size()-1) {
				// if this is the last point, line it with the first point
				Point a=vertices.get(i).rotate(rotationOrigin, angle);
				Point b=vertices.get(0).rotate(rotationOrigin, angle);
				
				//invert y axis when drawing polygon
				g.drawLine((int)a.getX(),(int)(g.getClipRect().getHeight()-a.getY()),(int)b.getX(),(int)(g.getClipRect().height-b.getY()));
			}
			else {
				// else, line the current point with next one
				Point a=vertices.get(i).rotate(rotationOrigin, angle);
				Point b=vertices.get(i+1).rotate(rotationOrigin, angle);
				
				//invert y axis when drawing polygon
				g.drawLine((int)a.getX(),(int)(g.getClipRect().getHeight()-a.getY()),(int)b.getX(),(int)(g.getClipRect().height-b.getY()));
			}
		}
	}
	
	

}
