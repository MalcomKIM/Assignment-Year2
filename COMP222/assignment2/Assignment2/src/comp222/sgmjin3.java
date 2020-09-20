package comp222;


import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.awt.geom.Point2D;


public class sgmjin3 extends Robot {

	boolean peek; // Don't turn if there's a robot there
	double moveAmount; // How much to move
	Point2D.Double myLoc;
	int clockwise=1;
	String lastHitByBullet=null;
	String lastBulletHit=null;
	
	

	/**
	 * run: Move around the walls
	 */
	public void run() {
		final double WIDTH=getBattleFieldWidth();
		final double HEIGHT=getBattleFieldHeight();
		
		// Initialize moveAmount to the maximum possible for this battlefield.
		moveAmount = Math.max(WIDTH, HEIGHT);
		// Initialize peek to false
		peek = false;
		
		// Set colors
		initColors();
		
		myLoc=getMyLoc();
		int shortest_index=getNearestWall();

		// turnLeft to face a wall.
		// getHeading() % 90 means the remainder of
		// getHeading() divided by 90.
		turnRight(getMoveAngle(360-getHeading() + shortest_index*90));
		ahead(moveAmount);
		// Turn the gun to turn right 90 degrees.
		turnGunRight(90);
		turnRight(90);

		while (true) {
			// Look before we turn when ahead() completes.
			peek = true;
			// Move up the wall
			ahead(moveAmount*clockwise);
			// Don't look now
			peek = false;
			// Turn to the next wall
			turnRight(90*clockwise);
		}
	}

	/**
	 * onHitRobot:  Move away a bit.
	 */
	public void onHitRobot(HitRobotEvent e) {
		// If he's in front of us, set back up a bit.
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			ahead(-100);
		} // else he's in back of us, so set ahead a bit.
		else {
			ahead(100);
		}
		clockwise*=-1;
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if(getEnergy()<=10&&getEnergy()>=0.5) {
			fire(0.1);
		}
		else if(getEnergy()>10 || getOthers()>1) {
			smartFire(e.getDistance());
		}
		// Note that scan is called automatically when the robot is moving.
		// By calling it manually here, we make sure we generate another scan event if there's a robot on the next
		// wall, so that we do not start moving up it until it's gone.
		if (peek) {
			scan();
		}
	}
	
//	public void onHitByBullet(HitByBulletEvent e) {
//		
//		if(e.getName()==lastHitByBullet) {
//			//peek=false;
//			if (e.getBearing() > -90 && e.getBearing() < 90) {
//				if(clockwise==1) {
//					turnRight(90);
//				}
//				else if(clockwise==-1) {
//					turnRight(-90);
//				}
//				ahead(-moveAmount);
//			} // else he's in back of us, so set ahead a bit.
//			else {
//				if(clockwise==1) {
//					turnRight(90);
//				}
//				else if(clockwise==-1) {
//					turnRight(-90);
//				}
//				ahead(moveAmount);
//			}
//			clockwise*=-1;
//			lastHitByBullet=e.getName();
//		}
//	}
	
	public void OnBulletHit(BulletHitEvent e)
	{
		lastBulletHit=e.getName();
		if(lastBulletHit.equals(lastHitByBullet)) {
			fire(3);
		}
	}
	
	private void initColors() {
		setBodyColor(Color.BLUE);
		setGunColor(Color.WHITE);
		setRadarColor(Color.WHITE);
		setBulletColor(Color.WHITE);
		setScanColor(Color.BLUE);
	}
	
	private Point2D.Double getMyLoc(){
		return new Point2D.Double(getX(),getY());
	}
	
	private double getMoveAngle(double moveAngle) {
		if(Math.abs(moveAngle) > 180) {
			moveAngle=moveAngle-360;
		}
		return moveAngle;
	}
	
	public void smartFire(double robotDistance) {
		if (robotDistance > 200 ) {
			fire(2);
		}
		else{
			fire(3);
		}
	}
	
	private int getNearestWall() {
		myLoc=getMyLoc();
		double[] distances=new double[4];
		distances[3]=myLoc.x;
		distances[0]=getBattleFieldWidth()-myLoc.y;
		distances[1]=getBattleFieldHeight()-myLoc.x;
		distances[2]=myLoc.y;
		
		double shortest_dis=distances[0];
		int shortest_index=0;
		for(int i=1;i<distances.length;i++) {
			if(distances[i]<shortest_dis) {
				shortest_index=i;
				shortest_dis=distances[i];
			}
		}
		return shortest_index;
	}
	
}
