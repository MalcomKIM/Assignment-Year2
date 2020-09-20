package uk.ac.liv.comp285.cw1.shapes;

import java.awt.Graphics;
import java.util.ArrayList;

import uk.ac.liv.comp285.cw1.Shape;

public class RegularPolygon extends Shape {
	// for storing vertices of the polygon
	public ArrayList <Point> vertices;
	
	public RegularPolygon(int sides, Point centre, float radius) {
		super();
		this.sides = sides;
		this.centre = centre;
		this.radius = radius;
		
		vertices=new ArrayList<Point>();
		
		for(int i=0;i<sides;i++) {
			// initialize vertices of the polygon
			float x=(float)(centre.getX()+radius*Math.cos(2*Math.PI/sides*i));
			float y=(float)(centre.getY()+radius*Math.sin(2*Math.PI/sides*i));
			Point p=new Point(x,y);
			
			// add vertices to the arraylist
			vertices.add(p);
		}
	}
	/**
	 * How many sides to the polygon
	 */
	private int sides=0;
	/**
	 * Centre of polygon, this represents the centre of the smallest circle which would contain the polygon 
	 */
	private Point centre;
	/**
	 * Size of the polygon defined as the radius of the centre of the smallest circle which would contain the polygon
	 */
	
	private float radius;
	@Override
	public float getArea() {
		// TODO Auto-generated method stub
		
		// separate the polygon to n triangle,
		// each triangle area is 0.5*radius*radius*Math.sin(2*Math.PI/sides)
		return (float)(0.5*radius*radius*Math.sin(2*Math.PI/sides)*sides);
	}
	@Override
	public Point getLowerLeftPoint() {
		// TODO Auto-generated method stub
		
		// min_x is the x of the lower left point
		float min_x=Float.MAX_VALUE;
		
		// min_y is the y of the lower left point
		float min_y=Float.MAX_VALUE;
		
		// find min_x and min_y by iterating vertices
		for(Point p:vertices) {
			min_x=Math.min(p.rotate(rotationOrigin, angle).getX(), min_x);
			min_y=Math.min(p.rotate(rotationOrigin, angle).getY(), min_y);
		}
		
		// return the lower left point
		return new Point(min_x,min_y);
	}
	@Override
	public Point getUpperRightPoint() {
		// TODO Auto-generated method stub
		
		// max_x is the x of the upper right point
		float max_x=Float.MIN_VALUE;
		
		// max_y is the y of the upper right point
		float max_y=Float.MIN_VALUE;
		
		// find max_x and max_y by iterating vertices
		for(Point p:vertices) {
			max_x=Math.max(p.rotate(rotationOrigin, angle).getX(), max_x);
			max_y=Math.max(p.rotate(rotationOrigin, angle).getY(), max_y);
		}
		
		// return the upper right point
		return new Point(max_x,max_y);
	}
	
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		// iterate vertices of the polygon
		for(int i=0;i<vertices.size();i++) {
			if(i==vertices.size()-1) {
				// if this is the last point, line it with the first point
				Point a=vertices.get(i).rotate(rotationOrigin, angle);
				Point b=vertices.get(0).rotate(rotationOrigin, angle);
				
				//invert y axis when drawing rectangle
				g.drawLine((int)a.getX(),(int)(g.getClipRect().getHeight()-a.getY()),(int)b.getX(),(int)(g.getClipRect().getHeight()-b.getY()));
			}
			else {
				// else, line the current point with next one
				Point a=vertices.get(i).rotate(rotationOrigin, angle);
				Point b=vertices.get(i+1).rotate(rotationOrigin, angle);
				
				//invert y axis when drawing rectangle
				g.drawLine((int)a.getX(),(int)(g.getClipRect().getHeight()-a.getY()),(int)b.getX(),(int)(g.getClipRect().getHeight()-b.getY()));
			}
		}
	}
	
	

}
