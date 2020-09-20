package uk.ac.liv.comp285.cw1.shapes;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CircleTest {
	Circle c;
	@Before
	public void setUp() throws Exception {
		// initialize a circle with center at (1,1) and radius=1
		c=new Circle(1,1,1);
	}

	@After
	public void tearDown() throws Exception {
		// release c
		c=null;
	}


	@Test
	public void testGetArea() {
		// calculate the expected value of the area
		float expectedArea=(float) Math.PI*1*1;
		
		// compare the expected value with the actual, tolerance is 0.001
		assertEquals(expectedArea,c.getArea(),0.001);
	}

	@Test
	public void testGetLowerLeftPoint() {
		// test getting lower left point with no rotation
		assertEquals(0,c.getLowerLeftPoint().getX(),0);
		assertEquals(0,c.getLowerLeftPoint().getY(),0);
		
		// test getting lower left point with rotation
		for(int i=0;i<=12;i++) {
			// set rotation origin to the center of the circle
			Point rotationOrigin=new Point(1,1);
			
			// rotate 30 more degrees for each loop
			c.setRotation(rotationOrigin, Math.PI/6*i);
			
			//Since the rotation origin is the center of the circle, the lower left point will not change
			assertEquals(0,c.getLowerLeftPoint().getX(),0);
			assertEquals(0,c.getLowerLeftPoint().getY(),0);
		}
		
		// test getting lower left point with rotation
		for(int i=0;i<=12;i++) {
			// set rotation origin to (0,0), different from the circle center
			Point rotationOrigin=new Point(0,0);
			
			// rotate 30 more degrees for each loop
			c.setRotation(rotationOrigin, Math.PI/6*i);
			
			// original center
			Point center=new Point(1,1);
			
			// new center of the circle after rotation
			Point newCenter=center.rotate(rotationOrigin, Math.PI/6*i);
			
			// test getting lower left point from the new circle
			assertEquals(newCenter.getX()-1,c.getLowerLeftPoint().getX(),0);
			assertEquals(newCenter.getY()-1,c.getLowerLeftPoint().getY(),0);
		}
	}

	@Test
	public void testGetUpperRightPoint() {
		// test getting upper right point with no rotation
		assertEquals(2,c.getUpperRightPoint().getX(),0);
		assertEquals(2,c.getUpperRightPoint().getY(),0);
		
		// test getting upper right point with rotation
		for(int i=0;i<=12;i++) {
			// set rotation origin to the center of the circle
			Point rotationOrigin=new Point(1,1);
			
			// rotate 30 more degrees for each loop
			c.setRotation(rotationOrigin, Math.PI/6*i);
			
			//Since the rotation origin is the center of the circle, the upper right point will not change
			assertEquals(2,c.getUpperRightPoint().getX(),0);
			assertEquals(2,c.getUpperRightPoint().getY(),0);
		}
		
		// test getting upper right point with rotation
		for(int i=0;i<=12;i++) {
			// set rotation origin to (0,0), different from the circle center
			Point rotationOrigin=new Point(0,0);
			
			// rotate 30 more degrees for each loop
			c.setRotation(rotationOrigin, Math.PI/6*i);
			
			// original center
			Point center=new Point(1,1);
			
			// new center of the circle after rotation
			Point newCenter=center.rotate(rotationOrigin, Math.PI/6*i);
			
			// test getting upper right point from the new circle
			assertEquals(newCenter.getX()+1,c.getUpperRightPoint().getX(),0);
			assertEquals(newCenter.getY()+1,c.getUpperRightPoint().getY(),0);
		}
	}


}
