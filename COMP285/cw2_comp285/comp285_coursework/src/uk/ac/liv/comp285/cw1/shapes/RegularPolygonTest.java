package uk.ac.liv.comp285.cw1.shapes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegularPolygonTest {
	
	RegularPolygon rp;
	
	@Before
	public void setUp() throws Exception {
		// initialize a polygon with 4 sides
		// center point is at (0,0)
		// radius = 1
		rp=new RegularPolygon(4,new Point(0,0),1);
	}

	@After
	public void tearDown() throws Exception {
		// release rp
		rp=null;
	}

	@Test
	public void testGetArea() {
		// compare the expected area with the actual, tolerance is 0.001
		assertEquals(2,rp.getArea(),0.001);
	}

	@Test
	public void testGetLowerLeftPoint() {
		// test getting lower left point with no rotation
		assertEquals(-1,rp.getLowerLeftPoint().getX(),0);
		assertEquals(-1,rp.getLowerLeftPoint().getY(),0);
		
		// set rotation origin to the center of the polygon
		Point rotationOrigin=new Point(0,0);
		// test getting lower left point with rotation
		for(int i=0;i<=12;i++) {
			// rotate 30 more degrees for each loop
			rp.setRotation(rotationOrigin, Math.PI/6*i);
			
			// min_x is the x of the lower left point
			float min_x=Float.MAX_VALUE;
			
			// min_y is the y of the lower left point
			float min_y=Float.MAX_VALUE;
			
			// find min_x and min_y by iterating vertices
			for(Point p: rp.vertices) {
				min_x=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getX(), min_x);
				min_y=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getY(), min_y);
			}
			
			// test getting lower left point from the new polygon
			assertEquals(min_x,rp.getLowerLeftPoint().getX(),0);
			assertEquals(min_y,rp.getLowerLeftPoint().getY(),0);
		}
		
		// set rotation origin to (2,2), different from the polygon center
		rotationOrigin=new Point(2,2);
		
		// test getting lower left point with rotation
		for(int i=0;i<=12;i++) {
			// rotate 30 more degrees for each loop
			rp.setRotation(rotationOrigin, Math.PI/6*i);
			
			// min_x is the x of the lower left point
			float min_x=Float.MAX_VALUE;
			
			// min_y is the y of the lower left point
			float min_y=Float.MAX_VALUE;
			
			// find min_x and min_y by iterating vertices
			for(Point p: rp.vertices) {
				min_x=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getX(), min_x);
				min_y=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getY(), min_y);
			}
			
			// test getting lower left point from the new polygon
			assertEquals(min_x,rp.getLowerLeftPoint().getX(),0);
			assertEquals(min_y,rp.getLowerLeftPoint().getY(),0);
		}
	}

	@Test
	public void testGetUpperRightPoint() {
		// test getting upper right point with no rotation
		assertEquals(1,rp.getUpperRightPoint().getX(),0);
		assertEquals(1,rp.getUpperRightPoint().getY(),0);
		
		// set rotation origin to the center of the polygon
		Point rotationOrigin=new Point(0,0);
		
		// test getting upper right point with rotation
		for(int i=0;i<=12;i++) {
			// rotate 30 more degrees for each loop
			rp.setRotation(rotationOrigin, Math.PI/6*i);
			
			// max_x is the x of the upper right point
			float max_x=Float.MIN_VALUE;
			
			// max_y is the y of the upper right point
			float max_y=Float.MIN_VALUE;
			
			// find max_x and max_y by iterating vertices
			for(Point p: rp.vertices) {
				max_x=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getX(), max_x);
				max_y=Math.min(p.rotate(rotationOrigin, Math.PI/6*i).getY(), max_y);
			}
			
			// test getting upper right point from the new polygon
			assertEquals(max_x,rp.getLowerLeftPoint().getX(),0);
			assertEquals(max_y,rp.getLowerLeftPoint().getY(),0);
		}
		
		// set rotation origin to (2,2), different from the polygon center
		rotationOrigin=new Point(2,2);
		
		// test getting upper right point with rotation
		for(int i=0;i<=12;i++) {
			// rotate 30 more degrees for each loop
			rp.setRotation(rotationOrigin, Math.PI/6*i);
			
			// max_x is the x of the upper right point
			float max_x=Float.MIN_VALUE;
			
			// max_y is the y of the upper right point
			float max_y=Float.MIN_VALUE;
			
			// find max_x and max_y by iterating vertices
			for(Point p: rp.vertices) {
				max_x=Math.max(p.rotate(rotationOrigin, Math.PI/6*i).getX(), max_x);
				max_y=Math.max(p.rotate(rotationOrigin, Math.PI/6*i).getY(), max_y);
			}
			
			// test getting upper right point from the new polygon
			assertEquals(max_x,rp.getUpperRightPoint().getX(),0);
			assertEquals(max_y,rp.getUpperRightPoint().getY(),0);
		}
		
	}

}
