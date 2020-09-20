package assignment1;

import java.math.BigDecimal;

/*
 * COMP201 2019-20
 * Assignment 1
 * Clerk.java
 * NAME: Minhao Jin
 * STUDENT ID: 201447766
 * COMPUTER USERNAME: sgmjin2
 */

public class Clerk extends Person {
	private CreditUnion union;

	public Clerk() {
		super();
	}

	public Clerk(CreditUnion theUnion) {
		super();
		union = theUnion;
	}

	public Clerk(String theName) {
		super(theName);
	}

	public void setUnion(CreditUnion theUnion) {
		union = theUnion;
	}

	public void addAccount(Account theAccount) {

		union.accounts.add(theAccount);

	}

	public void rmAccount(Account theAccount) {
		// TODO: Complete this method as opposite of addAccount(Account, Person)

		//Judge whether this customer has an account or not
		if(theAccount==null) {
			System.out.println("The customer has no account.");
			return;
		}
		// Call the method closeAccount(Customer theCustomer), if the account can be
		// closed successfully then remove the from Credit Union
		if (closeAccount(theAccount.holder)) {
			union.accounts.remove(theAccount);
			System.out.println("Close the account successfully.");
			System.out.println("Remove the account from Credit Union successfully.");
		} else {
			System.out.println("Unable to remove the account from Credit Union.");
		}

	}

	public void addCustomer(Customer c) {
		union.people.add(c);
		System.out.println("Customer " + c.name + " is registered with the Credit Union");
	}

	public void rmCustomer(Customer c) {

	}

	public boolean openSavings(Customer theCustomer, double openAmount) {
		// Check the Customer is registered with the union
		if (!(union.people.contains(theCustomer))) {
			addCustomer(theCustomer);
		}

		// Check we are opening the account with a valid amount
		if (openAmount >= 0.0) {
			try {
				// Create a Saving account for the customer with the openAmount
				Account theAccount = new Savings(openAmount, theCustomer);
				addAccount(theAccount);
				return true;
			} catch (AccountException e) {
				System.out.println("Account Exception caught whilst opening a savings account");
			}

		} else {
			System.out
					.println(this.name + "is opening the saving account with a invalid amount which is " + openAmount);
		}

		return false;
	}

	public boolean openLoan(Customer theCustomer, double theAmount) {
		// TODO: Complete this method - use the Process Loan method to help
		// Check the Customer is registered with the union
		if (!(union.people.contains(theCustomer))) {
			addCustomer(theCustomer);
		}

		// Check we are opening the account with a valid amount
		if (union.processLoanApplication(this, theAmount) && -theAmount <= 5000) {
			try {
				// Create a loan account for the customer with the openAmount
				Account theAccount = new Loan(theAmount, theCustomer);
				addAccount(theAccount);
				return true;
			} catch (AccountException e) {
				System.out.println("Account Exception caught whilst opening a loan account");
			}
		} else {
			System.out.println("Account Exception caught whilst opening a loan account");
		}

		return false;
	}

	public boolean closeAccount() {
		// TODO: Complete this method
		return closeAccount(new Customer());

	}

	// rewrite the method closeAccount()
	public boolean closeAccount(Customer theCustomer) {

		// judge whether theCustomer is contained in the list of Credit Union
		if (!union.people.contains(theCustomer)) {
			System.out.println("No such customer");
			return false;
		}

		// Extract the account from its holder
		Account theAccount = theCustomer.myAccount;
		if (theAccount instanceof Account) {
			try {
				// try closing the account
				theAccount.close();
			} catch (AccountException e) {
				System.out.println(e.getMessage());
				return false;
			}
		} else {
			System.out.println("No such account");
		}
		return true;
	}

	public boolean applyInterest() { // TODO: Complete this method
		
		//Find all the loan accounts and apply interest
		for (Account a : union.accounts) {
			if (a instanceof Loan) {
				
				//convert double to BigDecimal for preventing accuracy loss
				BigDecimal balance=new BigDecimal(a.getBalance());
				BigDecimal rate=new BigDecimal(1.0001);
				a.setBalance(balance.multiply(rate).doubleValue());

				System.out.println("Now the new balance of "+a.getHolder().name+" is "+a.getBalance());
			}
		}
		System.out.println("Apply interest successfully.");
		return true;
	}
};
