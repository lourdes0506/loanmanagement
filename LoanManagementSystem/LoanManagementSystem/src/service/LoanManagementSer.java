package service;
import java.util.List;
import java.util.Scanner;

import dao.ILoanRepositoryImpl;

import model.Loan;
import myexceptions.InValidLoanException;
public class LoanManagementSer {
	Scanner scanner;
	ILoanRepositoryImpl loanRepoImp;
	Loan loan = new Loan();
	public LoanManagementSer() {
	   scanner = new Scanner(System.in);
	   loanRepoImp = new ILoanRepositoryImpl();
	}
	
	public void applyLoan() {
		
		System.out.println("Enter principal Amount: ");
		loan.setPrincipalAmount(scanner.nextDouble());
		scanner.nextLine();
		
		System.out.println("Enter Intrest Rate: ");
		loan.setInterestRate(scanner.nextDouble());
		scanner.nextLine();
		
		System.out.println("Enter Loan Term: ");
		loan.setLoanTerm(scanner.nextInt());
		scanner.nextLine();
		
		System.out.println("Enter Loan Type(homeLoan/carloan): ");
		loan.setLoanType(scanner.nextLine());
		
		
		System.out.println("Confirm application? (Yes/No)");
        String confirmation = scanner.nextLine();
        
		if (confirmation.equalsIgnoreCase("Yes")) {
            boolean success = loanRepoImp.applyLoan(loan);
            if (success) {
                System.out.println("Loan application submitted successfully!");
                System.out.println("Your loan ID is: " + loan.getLoanId());
            } else {
                System.out.println("Failed to submit loan application");
            }
        } else {
            System.out.println("Sorry, Loan application cancelled");
        } 
	}

	public void calculateEMI() throws InValidLoanException{
		System.out.println("Enter Loan ID: ");
		int loanId = scanner.nextInt();
		
		loanRepoImp.calculateEMI(loanId);
	}
	
	public void loanRepayment() throws InValidLoanException{
		System.out.println("Enter Loan ID: ");
		int loanId = scanner.nextInt();
		
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		
		loanRepoImp.loanRepayment(loanId, amount);
	}
    
    public void getAllLoan() throws Exception{
	    List<Loan> loanLs = loanRepoImp.getAllLoan();
	    if (loanLs.isEmpty()) {
	        System.out.println("No loan found");
	    } else {
	        System.out.println("Loan Details:");
	        for (Loan l : loanLs) {
	        	System.out.println("---------------------------");
	            System.out.println("Loan ID: " + l.getLoanId());
	            System.out.println("Customer ID: " + l.getCustomerId());
	            System.out.println("Principal Amount: " + l.getPrincipalAmount());
	            System.out.println("Interest Rate: " + l.getInterestRate());
	            System.out.println("Loan Term: " + l.getLoanTerm());
	            System.out.println("Loan Type: " + l.getLoanType());
	            System.out.println("Loan Status: " + l.getLoanStatus());
	            System.out.println("---------------------------");
	        }
	    }
	}
    
    public void getLoanById() throws InValidLoanException{
    	System.out.println("Enter Loan ID:");
    	int loanId = scanner.nextInt();
    	List<Loan> loanIdLs = loanRepoImp.getLoanById(loanId);
 	    if (loanIdLs.isEmpty()) {
 	        System.out.println("No loan found");
 	    } else {
 	        System.out.println("Loan Details:");
 	        for (Loan l : loanIdLs) {
 	        	System.out.println("---------------------------");
 	            System.out.println("Loan ID: " + l.getLoanId());
 	            System.out.println("Customer ID: " + l.getCustomerId());
 	            System.out.println("Principal Amount: " + l.getPrincipalAmount());
 	            System.out.println("Interest Rate: " + l.getInterestRate());
 	            System.out.println("Loan Term: " + l.getLoanTerm());
 	            System.out.println("Loan Type: " + l.getLoanType());
 	            System.out.println("Loan Status: " + l.getLoanStatus());
 	            System.out.println("---------------------------");
 	        }
 	    }

	}
    
    public void loanStatus() {
    	System.out.println("Enter Loan ID:");
    	int loanId = scanner.nextInt();
    	
    	loanRepoImp.loanStatus(loanId);
    }

}
