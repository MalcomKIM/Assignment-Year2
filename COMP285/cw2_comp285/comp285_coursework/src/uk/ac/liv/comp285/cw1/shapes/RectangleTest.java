package uk.ac.liv.comp285.cw1.shapes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RectangleTest {
	Rectangle r;

	@Before
	public void setUp() throws Exception {
		// initialize a rectangle whose left lower point is at (0,0)
		// width and height are both 1
		r=new Rectangle(0,0,1,1);
	}

	@After
	public void tearDown() throws Exception {
		// release r
		r=null;
	}

	@Test
	public void testGetArea() {
		// compare the expected area with the actual, tolerance is 0
		assertEquals(1*1,r.getArea(),0);
	}

	@Test
	public void testGetLowerLeftPoint() {
		// test getting lower left point with no rotation
		assertEquals(0,r.getLowerLeftPoint().getX(),0);
		assertEquals(0,r.getLowerLeftPoint().getY(),0);
		
		// test getting lower left point with rotation
		for(int i=0;i<=12;i++) {
			// set rotation origin to (0,0)
			Point rotationOrigin=new Point(0,0);
			
			// rotate 30 more degrees for each loop
			r.setRotation(rotationOrigin, Math.PI/6*i);
			
			// min_x is the x of the lower left point
			float min_x=Float.MAX_VALUE;
			
			// min_y is the y of the lower left point
			float min_y=Float.MAX_VALUE;
			
			// find min_x and min_y by iterating vertices
			for(Point p: r.vertices) {
				min_x=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getX(), min_x);
				min_y=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getY(), min_y);
			}
			
			// test getting lower left point from the new rectangle
			assertEquals(min_x,r.getLowerLeftPoint().getX(),0);
			assertEquals(min_y,r.getLowerLeftPoint().getY(),0);
		}
	}

	@Test
	public void testGetUpperRightPoint() {
		// test getting upper right point with no rotation
		assertEquals(1,r.getUpperRightPoint().getX(),0);
		assertEquals(1,r.getUpperRightPoint().getY(),0);
		
		// test getting upper right point with rotation
		for(int i=0;i<=12;i++) {
			// set rotation origin to (0,0)
			Point rotationOrigin=new Point(0,0);
			
			// rotate 30 more degrees for each loop
			r.setRotation(rotationOrigin, Math.PI/6*i);
			
			// max_x is the x of the upper right point
			float max_x=Float.MIN_VALUE;
			
			// max_y is the y of the upper right point
			float max_y=Float.MIN_VALUE;
			
			// find max_x and max_y by iterating vertices
			for(Point p: r.vertices) {
				max_x=Math.max(p.rotate(rotationOrigin, Math.PI/6*i).getX(), max_x);
				max_y=Math.max(p.rotate(rotationOrigin, Math.PI/6*i).getY(), max_y);
			}
			
			// test getting upper right point from the new rectangle
			assertEquals(max_x,r.getUpperRightPoint().getX(),0);
			assertEquals(max_y,r.getUpperRightPoint().getY(),0);
		}
	}
}
