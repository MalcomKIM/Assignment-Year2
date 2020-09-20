
/*
 * COMP201 2019-20
 * Assignment 1
 * Customer.java
 * NAME: Minhao Jin
 * STUDENT ID: 201447766
 * COMPUTER USERNAME: sgmjin2
 */

public class Customer extends Person {
	public Account myAccount; // The customers account

	public Customer() {
		super();
	}

	public Customer(String theName) {
		super(theName);
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setAccount(Account theAccount) {
		myAccount = theAccount;
	}

	public void makeWithdrawal(double amount) {
		// TODO: Make sure this only happens for a Savings Account

		// ensure this customer's account is a saving account
		if (!(myAccount instanceof Savings)) {
			System.out.println("This is not a saving account");
			return;
		}

		try {
			// try withdrawing money from this account
			myAccount.withdraw(amount);
		} catch (AccountException e) {
			// TODO:: Handle gracefully
			System.out.println("Account Exception caught whilst " + this.name + " withdrawing a saving account");
		}
	}

	public double makePayment(double amount) {
		System.out.println("Customer " + this.name + " pays £" + amount + " to his account");

		if (amount <= 0) {
			// deal with non-positive payment
			System.out.println("Invalid payment.");
			return 0;
		}

		if (myAccount == null) {
			// deal with customer without an account
			System.out.println("this customer does not have an account");
			return 0;
		} else {
			myAccount.payment(amount);
		}

		return myAccount.getBalance();
	}
};
