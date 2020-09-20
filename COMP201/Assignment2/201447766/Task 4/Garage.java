/*
 * COMP201 Assignment 2
 * Garage.java
 * NAME: Minhao Jin
 * STUDENT ID: 201447766
 * COMPUTER USERNAME: sgmjin2
 * 
 */
import java.util.Vector;

public class Garage {
	public Vector<Staff> staff;
	public Vector<Ticket> tickets;
	
	public void perform_inspection_task() {}
	public void perform_repair_task() {}
	public void perform_maintenance_task() {}

	public Ticket get_waiting_tickets() {
		return tickets.firstElement();
	}

	public Ticket get_check_tickets() {
		return tickets.firstElement();
	}

	public Ticket get_signedoff_tickets() {
		return tickets.firstElement();
	}
}
