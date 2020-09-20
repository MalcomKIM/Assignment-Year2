package assignment2;

public class Mechanic extends Staff {
	public Mechanic(String name, Garage garage) {
		super(name,garage);
	}
	
	public Ticket get_next_ticket() {
		return garage.get_waiting_tickets();
	}
	
	public void perform_work() {}
	
	public void update_price() {}
}
