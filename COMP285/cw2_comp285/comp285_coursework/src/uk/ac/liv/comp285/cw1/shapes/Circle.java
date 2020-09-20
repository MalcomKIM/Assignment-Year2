package uk.ac.liv.comp285.cw1.shapes;

import java.awt.Graphics;

import uk.ac.liv.comp285.cw1.Shape;

public class Circle extends Shape {
	
	public Circle(float x, float y,float radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	private float x,y,radius;	// x,y are centre of the circle

	@Override
	public float getArea() {
		return((float)(Math.PI*radius*radius));
	}

	@Override
	public Point getLowerLeftPoint() {
		if(rotationOrigin==null||angle==0) {
			// if there's no rotation point or rotation angle equals 0,
			// then return the current lower left point
			return(new Point(x-radius,y-radius)); 
		}
		else {
			// find the new center point after rotation
			Point newCenter=new Point(x,y).rotate(rotationOrigin, angle);
			
			// then return the lower left point of the new circle
			return new Point(newCenter.getX()-radius,newCenter.getY()-radius);
		}
	}

	@Override
	public Point getUpperRightPoint() {
		if(rotationOrigin==null||angle==0) {
			// if there's no rotation point or rotation angle equals 0,
			// then return the current upper right point
			return(new Point(x+radius,y+radius)); 
		}
		else {
			
			// find the new center point after rotation
			Point newCenter=new Point(x,y).rotate(rotationOrigin, angle);
			// then return the upper right point of the new circle
			return new Point(newCenter.getX()+radius,newCenter.getY()+radius);
		}
	}

	@Override
	public void render(Graphics g) {
		//invert y axis when drawing the circle
		g.drawArc((int)(this.getLowerLeftPoint().getX()),(int)(g.getClipRect().getHeight()-this.getLowerLeftPoint().getY()-radius*2), (int)(radius*2), (int)(radius*2),0,360);
	}


}
