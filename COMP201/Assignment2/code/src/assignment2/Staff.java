package assignment2;

public abstract class Staff extends Person{
	protected Garage garage;
	public Staff(String name,Garage garage){
		super(name);
		this.garage=garage;
	}
	
	public void update_status(Ticket ticket) {}
	
	abstract Ticket get_next_ticket();
}
