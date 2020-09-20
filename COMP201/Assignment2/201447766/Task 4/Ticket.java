/*
 * COMP201 Assignment 2
 * Ticket.java
 * NAME: Minhao Jin
 * STUDENT ID: 201447766
 * COMPUTER USERNAME: sgmjin2
 * 
 */
import java.util.Date;

public class Ticket {
	private Customer customer;
	private String vehicle;
	private String work;
	private Date deadline;
	private double price;
	private String status;

	public Ticket(Customer customer,String vehicle, String work, Date deadline, double price, String status) {
		this.customer = customer;
		this.vehicle=vehicle;
		this.work = work;
		this.deadline = deadline;
		this.price = price;
		this.status = status;
	}

	public String get_status() {
		return status;
	}
	
	public void set_status(String new_status) {

	}
	
	public double get_price() {
		return price;
	}

	public void set_price(double new_price) {

	}
}
