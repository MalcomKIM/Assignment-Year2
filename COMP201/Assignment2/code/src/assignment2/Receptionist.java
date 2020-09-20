package assignment2;

public class Receptionist extends Staff {
	
	public Receptionist(String name, Garage garage) {
		super(name,garage);
	}

	public Ticket get_next_ticket() {
		return garage.get_signedoff_tickets();
	}
	
	public void openticket() {}
	
	public void call_customer() {}
	
}
