package dao;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Loan;
import myexceptions.InValidLoanException;
import util.DBUtil;

public class ILoanRepositoryImpl implements ILoanRepository {
     Loan loan = new Loan();
     Scanner scanner = new Scanner(System.in);
     PreparedStatement statement;
 	 Connection connection = DBUtil.getConnection();
	 
	@Override
	public boolean applyLoan(Loan loan) {
		System.out.println("Enter Customer ID: ");
		int customerId = scanner.nextInt();
		String findCustQuery = "select credit_score from customer where customer_id =?";
		try(PreparedStatement statement1 = connection.prepareStatement(findCustQuery)){
			statement1.setInt(1,customerId);
			ResultSet rs = statement1.executeQuery();
			if(rs.next() && rs.getInt("credit_Score")>650) {
				String insertQuery = "insert into loan(customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) values(?, ?, ?, ?, ?, ?)";
				try(PreparedStatement statement2 = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)){
				    loan.setCustomerId(customerId);
					statement2.setInt(1, loan.getCustomerId());
					statement2.setDouble(2, loan.getPrincipalAmount());
					statement2.setDouble(3, loan.getInterestRate());
					statement2.setInt(4, loan.getLoanTerm());
					statement2.setString(5, loan.getLoanType());
					statement2.setString(6, "Approved");
					int rowsInserted = statement2.executeUpdate();
					 if (rowsInserted > 0) {
		                    try (ResultSet generatedKeys = statement2.getGeneratedKeys()) {
		                        if (generatedKeys.next()) {
		                            loan.setLoanId(generatedKeys.getInt(1));
		                        }
		                    }
		                    System.out.println("Loan applied successfully");
		                    System.out.println("Your Loan ID: " + loan.getLoanId());
		                    return true;
		                }
				}
				catch(Exception e) {
					System.out.println("Error occured :" + e.getMessage());
				}
			}
		}
		catch(Exception e) {
			System.out.println("Credit score of the Customer ID "+ loan.getCustomerId() +" is low to apply loan ");
		}
		return false;
	}

	@Override
	public double calculateInterest(int loanId) throws InValidLoanException {
		String loanQuery = "select principal_amount, interest_rate, loan_term from loan where loan_id = ?";
		try(PreparedStatement statement = connection.prepareStatement(loanQuery)){
			statement.setInt(1, loan.getLoanId());
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				double interest = 0.0;
				double principal_amount = loan.getPrincipalAmount();
				double interest_rate = loan.getInterestRate();
				int loan_term = loan.getLoanTerm();
				interest = (principal_amount * interest_rate * loan_term) / 12 ;
				return interest;
			}
			else {
				throw new InValidLoanException("Error calculating interest");
			}
		}
		catch(Exception e) {
			System.out.println("Error in calculating interest: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public void loanStatus(int loanId) {
		String loanStatusQuery ="select loan_status from loan where loan_id =?";
		try(PreparedStatement statement = connection.prepareStatement(loanStatusQuery)){
			statement.setInt(1, loanId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				String output = rs.getString("loan_status");
				System.out.println("Loan Status: "+ output);
				if(null != output && output.equalsIgnoreCase("approved")) {
					System.out.println("You loan is approved");
				}
			}
			else 
				System.out.println("Your credit score is low to apply loan");
		}
		catch(Exception e) {
			System.out.println("Error in checking loan status: "+e.getMessage());
		}
		
	}

	@Override
	public double calculateEMI(int loanId) throws InValidLoanException {
	    String query = "select principal_amount, interest_rate, loan_term from loan where loan_id = ?";
	    
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        
	    	statement.setInt(1, loanId);
	        ResultSet rs = statement.executeQuery();
	        
	        if (rs.next()) {
	            double principal = rs.getDouble("principal_amount");
	            double annualRate = rs.getDouble("interest_rate");
	            int termMonths = rs.getInt("loan_term");
	            double monthlyRate = annualRate / 12 / 100;
	            double factor = Math.pow(1 + monthlyRate, termMonths);
	            double emi = (principal * monthlyRate * factor) / (factor - 1);
	           
	            return emi;
	            } 
	        else {
	            System.out.println(" No loan found for Loan ID "+ loanId);
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error in calculating EMI: " + e.getMessage());
	    }
	    return 0;
	}


	@Override
	public void loanRepayment(int loanId, double amount) throws InValidLoanException {
	    try {
	        double emiAmount = calculateEMI(loanId);
	        
	        if (emiAmount <= 0) {
	            throw new InValidLoanException("EMI calculation failed for Loan ID: " + loanId);
	        }
	        
	        if (amount < emiAmount) {
	            System.out.println("Payment rejected, Amount "+amount+" is less than "+emiAmount);
	        }
	        else { 
	            int numberOfEMIs = (int) (amount / emiAmount);
	            double remainingAmount = amount - (numberOfEMIs * emiAmount);
	            System.out.println("Loan ID: "+loanId);
	            System.out.printf("EMI Amount: %.2f%n ",emiAmount);
	            System.out.println("Payment Amount: "+ amount);
	            System.out.println("Number of EMIs that can be paid: "+numberOfEMIs);
	            
	            if (remainingAmount > 0) {
	            	System.out.printf("Remaining amount after payment: %.2f%n", remainingAmount);
	            }
	        }
	    }
	    catch(InValidLoanException e) {
	        System.out.println("Error in Loan Repayment: "+e.getMessage());
	        throw e;
	    }
	    catch(Exception e) {
	        System.out.println("Unexpected error: "+e.getMessage());
	    }
	}
	
	@Override
	public List<Loan> getAllLoan() throws Exception {
		List<Loan> loanLs = new ArrayList<>();
		String query = "select * from loan";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Loan l = new Loan();
			    l.setLoanId(rs.getInt("loan_id"));
	            l.setCustomerId(rs.getInt("customer_id"));
	            l.setPrincipalAmount(rs.getDouble("principal_amount"));
	            l.setInterestRate(rs.getDouble("interest_rate"));
	            l.setLoanTerm(rs.getInt("loan_term"));
	            l.setLoanType(rs.getString("loan_type"));
	            l.setLoanStatus(rs.getString("loan_status"));
	            
	            loanLs.add(l);
			}
		}
		catch(Exception e) {
			System.out.println("Error in getting all loans "+e.getMessage());
		}
		return loanLs;
	}

	@Override
	public List<Loan> getLoanById(int loanId) throws InValidLoanException {
		List<Loan> loanLs = new ArrayList<>();
		String query = "select * from loan where loan_id =?";
		try(PreparedStatement statement = connection.prepareStatement(query)) { 
			statement.setInt(1, loanId);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Loan l = new Loan();
				l.setLoanId(loanId);
			    l.setCustomerId(rs.getInt(2));
			    l.setPrincipalAmount(rs.getDouble(3));
			    l.setInterestRate(rs.getDouble(4));
			    l.setLoanTerm(rs.getInt(5));
			    l.setLoanType(rs.getString(6));
			    l.setLoanStatus(rs.getString(7));
				loanLs.add(l);
			}
		}
		catch(Exception e) {
			System.out.println("Something Went Wrong :( ");
		}
		return loanLs;
}
}
