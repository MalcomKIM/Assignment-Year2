package assignment1;
/*
 * COMP201 2019-20
 * Assignment 1
 * CreditUnionUser.java
 * NAME: Minhao Jin
 * STUDENT ID: 201447766
 * COMPUTER USERNAME: sgmjin2:
 */
 
 
 //TODO: You must go through ALL CODE to ensure it meets requirements!!!
 
 public class CreditUnionUser{

	 public static void main(String[] args)
	 {
			//Create new Clerk
			Clerk clerk = new Clerk("Donald");
			
			//Create some new customers
			Customer customer1 = new Customer("Thomas");
			Customer customer2 = new Customer("Richard");
			Customer customer3 = new Customer("Harry");
			Customer customer4 = new Customer("Sally");
			
			//Create the credit union with opening capital
			CreditUnion cu = new CreditUnion(100000.00, clerk);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");

			//TODO: Write code that tests the program. Use the code below to prompt your testing
			
			//Create some saving accounts (The Clerk must do this on behalf of the customer)
			clerk.openSavings(customer1, 1000.0);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			clerk.openSavings(customer2, -1000.0);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			clerk.openSavings(customer3, 0);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			
			//Add balances to saving accounts (The customer does this)
			customer1.makePayment(200);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			customer2.makePayment(200);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			customer3.makePayment(-200);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			
			
			//Make some withdrawals from saving accounts (The customer does this)
			customer1.makeWithdrawal(200);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			customer2.makeWithdrawal(200);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			customer3.makeWithdrawal(200);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			
			//Process a loan application (The clerk does this on behalf of the customer)
			clerk.openLoan(customer2, -1000);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			
			clerk.applyInterest();
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			
			//Make a payment to loan account (The customer does this)
			customer2.makePayment(1000.1);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");

			//Close loan account (The clerk does this)
			clerk.rmAccount(customer2.myAccount);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			
			//Close saving accounts (The clerk does this)
			clerk.rmAccount(customer1.myAccount);
			System.out.println("Credit Union Capital is: " + cu.getTotalCapital(clerk)+"\n");
			
			
	 } 
	 
};
