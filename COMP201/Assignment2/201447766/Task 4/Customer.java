/*
 * COMP201 Assignment 2
 * Customer.java
 * NAME: Minhao Jin
 * STUDENT ID: 201447766
 * COMPUTER USERNAME: sgmjin2
 * 
 */
public class Customer extends Person {
	private String telephone;
	private String[] vehicles;

	public Customer(String name, String telephone, String[] vehicles) {
		super(name);
		this.telephone = telephone;
		this.vehicles = vehicles;
	}
	
	public String get_telephone() {
		return telephone;
	}
	
	public String[] get_vehicles() {
		return vehicles;
	}

}
