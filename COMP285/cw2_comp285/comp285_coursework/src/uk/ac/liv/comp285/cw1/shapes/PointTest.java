/**
 * 
 */
package uk.ac.liv.comp285.cw1.shapes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Malcom
 *
 */
public class PointTest {

	Point p1;
	Point p2;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// p1 is at (0,0)
		p1=new Point(0,0);
		
		// p2 is at (-1,0)
		p2=new Point(-1,0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		//release p1 and p2
		p1=null;
		p2=null;
	}

	/**
	 * Test method for {@link uk.ac.liv.comp285.cw1.shapes.Point#getX()}.
	 */
	@Test
	public void testGetX() {
		// test getting the right x value of p1
		assertEquals(0,p1.getX(),0);
	}

	/**
	 * Test method for {@link uk.ac.liv.comp285.cw1.shapes.Point#setX(float)}.
	 */
	@Test
	public void testSetX() {
		// set p1'x to 1
		p1.setX(1);
		
		// test getting the right x value of p1
		assertEquals(1,p1.getX(),0);
	}

	/**
	 * Test method for {@link uk.ac.liv.comp285.cw1.shapes.Point#getY()}.
	 */
	@Test
	public void testGetY() {
		// test getting the right y value of p1
		assertEquals(0,p1.getY(),0);
	}

	/**
	 * Test method for {@link uk.ac.liv.comp285.cw1.shapes.Point#setY(float)}.
	 */
	@Test
	public void testSetY() {
		// set p1'y to 1
		p1.setY(1);
		
		// test getting the right y value of p1
		assertEquals(1,p1.getY(),0);
	}

	/**
	 * Test method for {@link uk.ac.liv.comp285.cw1.shapes.Point#subtract(uk.ac.liv.comp285.cw1.shapes.Point)}.
	 */
	@Test
	public void testSubtract() {
		// sub is the result of p1 - p2
		Point sub=p1.subtract(p2);
		
		// test getting the right x and y value of sub
		assertEquals(1,sub.getX(),0);
		assertEquals(0,sub.getY(),0);
	}

	/**
	 * Test method for {@link uk.ac.liv.comp285.cw1.shapes.Point#add(uk.ac.liv.comp285.cw1.shapes.Point)}.
	 */
	@Test
	public void testAdd() {
		// add is the result of p1 + p2
		Point add=p1.add(p2);
		
		// test getting the right x and y value of add
		assertEquals(-1,add.getX(),0);
		assertEquals(0,add.getY(),0);
	}

	/**
	 * Test method for {@link uk.ac.liv.comp285.cw1.shapes.Point#rotate(uk.ac.liv.comp285.cw1.shapes.Point, double)}.
	 */
	@Test
	public void testRotate() {
		// r is the distance between p1 and p2
		double r=1;
		
		// test if rotation origin is null
		Point p3=p2.rotate(null, 2*Math.PI);
		assertEquals(p2.getX(),p3.getX(),0);
		assertEquals(p2.getY(),p3.getY(),0);
		
		// test if rotation angle is 0
		p3=p2.rotate(p1, 0);
		assertEquals(p2.getX(),p3.getX(),0);
		assertEquals(p2.getY(),p3.getY(),0);
		
		// test if rotation origin is the point itself
		p3=p2.rotate(p2, Math.PI);
		assertEquals(p2.getX(),p3.getX(),0);
		assertEquals(p2.getY(),p3.getY(),0);
		
		// test rotation from 0 to 360, rotate 30 more degrees each loop
		for(int i=1;i<=12;i++) {
			p3= p2.rotate(p1, Math.PI/6*i);
			assertEquals(r*Math.cos(-Math.PI+Math.PI/6*i),p3.getX(),0.001);
			assertEquals(r*Math.sin(-Math.PI+Math.PI/6*i),p3.getY(),0.001);
		}
	}

}
