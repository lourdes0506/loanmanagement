package dao;

import java.util.List;

import model.Loan;
import myexceptions.InValidLoanException;

public interface ILoanRepository {
	boolean applyLoan(Loan loan);
	double calculateInterest(int loanId) throws InValidLoanException;
	void loanStatus(int loanId);
	double calculateEMI(int loadId) throws InValidLoanException;
	void loanRepayment(int loadId, double amount) throws InValidLoanException;
	List<Loan> getAllLoan() throws Exception;
	List<Loan>  getLoanById(int loanId) throws InValidLoanException;
}