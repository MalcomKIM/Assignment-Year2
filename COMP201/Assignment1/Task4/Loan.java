
/*
 * COMP201 Assignment 1
 * Loan.java
 * NAME: Minhao Jin
 * STUDENT ID: 201447766
 * COMPUTER USERNAME: sgmjin2
 */

import java.math.BigDecimal;

public class Loan extends Account {
	// Default constructor for Loan account - sets initial balance to 0
	public Loan() {
		super();
		setBalance(0.0);
	}

	public Loan(double amount, Customer theCustomer) throws AccountException {
		if (amount > 0.0) {
			throw new AccountException("Cannot open a Loan account with positive balance: " + amount);
		}

		// TODO: Complete the opening of this Loan account
		setBalance(amount);
		holder = theCustomer;
		holder.setAccount(this);

		System.out.println("Loan account opened for " + holder.name + " with balance of " + getBalance());
	}

	public void close() throws AccountException {
		if (getBalance() < 0.0) {
			throw new AccountException("Cannot close a Savings account with negative balance: " + getBalance());
		} else if (getBalance() > 0.0) {
			throw new AccountException(
					"Cannot close a loan account with positive balance, this should not have happened, so an error has occured! Balance is: £"
							+ getBalance());
		} else {
			System.out.println("Closing the account...");
			System.out.println("Balance at account closure is now: £" + getBalance());
		}
	}

	/**
	 * Makes Payment into the loan account
	 * 
	 * @return The new balance
	 * @param amount
	 *            the amount to make payment of
	 */
	public double payment(double amount) {
		// TODO: Complete this method inline with reqs

		if (amount <= 0) {
			// deal with non-positive payment
			System.out.println("Amount cannot be negative");
		} else if (amount + getBalance() > 0) {
			// deal with extra payment
			System.out.println("Extra money is payed");
		} else {
			//convert double to BigDecimal for preventing accuracy loss
			BigDecimal balance = new BigDecimal(Double.toString(getBalance()));
			BigDecimal payment = new BigDecimal(Double.toString(amount));
			setBalance(balance.add(payment).doubleValue());
			System.out.println("New balance: £" + getBalance());
		}

		return getBalance();
	}

	public void withdraw(double amount) throws AccountException {
		throw new AccountException("...");
	}
};
