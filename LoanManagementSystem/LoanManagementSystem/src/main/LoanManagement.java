package main;
import java.sql.Connection;
import java.util.Scanner;

import service.LoanManagementSer;
import util.DBUtil;
public class LoanManagement {
	static {
		try {
			Connection connection = DBUtil.getConnection();
			if(connection!=null)
				System.out.println("Database connected successfully...");
		}
		catch(Exception e) {
			System.out.println("Error during DB operation "+ e.getMessage());
		}
	}
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		LoanManagementSer loanManagementSer = new LoanManagementSer();
		System.out.println("WELCOME TO LOAN MANAGEMENT SYSTEM");
		System.out.println("----------------------------------");
		boolean flag = true;
		do {
			System.out.println("Choose the Option");
			System.out.println("1. Apply Loan");
			System.out.println("2. Get all Loan");
			System.out.println("3. Get Loan By ID");
			System.out.println("4. Loan Repayment");
			System.out.println("5. Check Loan status");
			System.out.println("6. Exit");
			System.out.println("----------------------------------");
			System.out.println("Enter your Option(1-6):");
			int opt = scanner.nextInt();
			switch(opt) {
			case 1:{
			    loanManagementSer.applyLoan();
			}break;
			
			case 2:{
				loanManagementSer.getAllLoan();
			}break;
			
			case 3:{
				loanManagementSer.getLoanById();
			}break;
			
			case 4:{
				loanManagementSer.loanRepayment();
			}break;
			
			case 5:{
				loanManagementSer.loanStatus();
			}break;
			
			case 6:{
				flag = false;
				System.out.println("Thank you, you are exiting now...");
			}break;
			
			default:{
				System.out.println("Invalid Option :( Try again");
			}
			}
		}while(flag);
		
	}
}