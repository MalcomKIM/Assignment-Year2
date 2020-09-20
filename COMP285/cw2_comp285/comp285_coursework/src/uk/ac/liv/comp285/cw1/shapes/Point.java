package uk.ac.liv.comp285.cw1.shapes;


public class Point {
	
	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	private float x,y;

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Returns a point which is a subtraction of this Point away from another
	 * @param otherPoint
	 * @return
	 */
	Point subtract(Point otherPoint) {
		return(new Point(x-otherPoint.x,y-otherPoint.y));
	}
	/**
	 * Returns a point which is the addition of this point and another point
	 * @param otherPoint
	 * @return 
	 */
	Point add(Point otherPoint) {
		return(new Point(x+otherPoint.x,y+otherPoint.y));
    }

	/**
	 * Rotates a point around the origin given by an angle in radians and returns the resulting Point
	 * @param p
	 * @param origin
	 * @param angle
	 * @return
	 */
	public Point rotate(Point origin,double angle) {
		
		// Optimisations
		if (origin==null) return(this);		// origin not set so don't rotate
		
		if (angle==0) return(this);			// don't bother to rotate if angle = 0
		
		// else, get the new point after rotation using rotation matrix
		float newX = (float) (origin.getX() + (this.x-origin.getX())*Math.cos(angle) - (this.y-origin.getY())*Math.sin(angle));
		float newY = (float) (origin.getY() + (this.x-origin.getX())*Math.sin(angle) + (this.y-origin.getY())*Math.cos(angle));
		
		// return the new point after rotation
		return new Point(newX,newY);
	}
	

}
