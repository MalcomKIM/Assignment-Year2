package comp222;


import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.awt.geom.Point2D;


public class MinhaoJin201447766 extends Robot {

	boolean peek; 
	double moveAmount; // How much to move
	Point2D.Double myLoc; // Record the current location
	int clockwise=1; // moving direction
	String lastHitByBullet=null;
	String lastBulletHit=null;
	
	

	/**
	 * run: Move around the walls
	 */
	public void run() {
		// Get the width of the battle field
		final double WIDTH=getBattleFieldWidth();
		// Get the height of the battle field
		final double HEIGHT=getBattleFieldHeight();
		
		// Initialize moveAmount to the maximum possible for this battlefield.
		moveAmount = Math.max(WIDTH, HEIGHT);
		// Initialize peek to false
		peek = false;
		
		// Set colors
		initColors();
		
		// Get my current location
		myLoc=getMyLoc();
		
		// Find the nearest wall
		int shortest_index=getNearestWall();

		// Turn to face the nearest wall.
		turnRight(getMoveAngle(360-getHeading() + shortest_index*90));
		
		// Move to the nearest wall
		ahead(moveAmount);
		
		// Turn the gun to right 90 degrees
		turnGunRight(90);
		// Turn the body to right 90 degrees
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
		// If enemy is in front of us, set back 100 pixels
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			ahead(-100);
		} // else enemy is in back of us, set ahead 100 pixels
		else {
			ahead(100);
		}
		clockwise*=-1;
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// if there is no energy left
		if(getEnergy()<=10&&getEnergy()>=0.5) {
			// set bullet power to 0.1
			fire(0.1);
		}
		else if(getEnergy()>10 || getOthers()>1) {
			// set bullet power according to enemy's distance
			smartFire(e.getDistance());
		}

		if (peek) {
			scan();
		}
	}
	
	/**
	 * onHitByBullet: Start dodging
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		if(getEnergy()<60 && getOthers()>1) {
			// If 2 bullets come from the same enemy
			if(e.getName()==lastHitByBullet) {
				// start dodging
				if (e.getBearing() > -90 && e.getBearing() < 90) {
					if(getHeading()%90==0) {
						if(clockwise==1) {
							turnRight(90);
						}
						else if(clockwise==-1) {
							turnRight(-90);
						}
						ahead(-moveAmount);
					}
				} 
				else {
					if(getHeading()%90==0) {
						if(clockwise==1) {
							turnRight(90);
						}
						else if(clockwise==-1) {
							turnRight(-90);
						}
						ahead(moveAmount);
					}
				}
				
			}
		}
		
		// update the name of bullet owner
		lastHitByBullet=e.getName();
	}
	
	/**
	 * OnBulletHit: fire harder
	 * @param e
	 */
	public void OnBulletHit(BulletHitEvent e)
	{
		// Update the target name
		lastBulletHit=e.getName();
		
		// If the target is also firing at me
		if(lastBulletHit.equals(lastHitByBullet)) {
			// fire harder
			fire(3);
		}
	}
	
	/**
	 * initColors: initialize colors
	 */
	private void initColors() {
		setBodyColor(Color.BLUE);
		setGunColor(Color.WHITE);
		setRadarColor(Color.WHITE);
		setBulletColor(Color.WHITE);
		setScanColor(Color.BLUE);
	}
	
	/**
	 * getMyLoc: get the current location
	 * @return
	 */
	private Point2D.Double getMyLoc(){
		return new Point2D.Double(getX(),getY());
	}
	
	/**
	 * getMoveAngle: normalize the angle to turn
	 * @param moveAngle
	 * @return
	 */
	private double getMoveAngle(double moveAngle) {
		if(Math.abs(moveAngle) > 180) {
			moveAngle=moveAngle-360;
		}
		return moveAngle;
	}
	
	/**
	 * smartFire: fire according to distance
	 * @param robotDistance
	 */
	private void smartFire(double robotDistance) {
		if (robotDistance > 200 ) {
			fire(2);
		}
		else{
			fire(3);
		}
	}
	
	/**
	 * getNearestWall: return the index of nearest wall
	 * @return
	 */
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
