package assignment2;

public class Manager extends Mechanic{
	public Manager(String name, Garage garage) {
		super(name,garage);
	}
	
	public Ticket get_next_ticket() {
		return garage.get_check_tickets();
	}
	
	public boolean check_work() {
		return true;
	}
	
	
}
