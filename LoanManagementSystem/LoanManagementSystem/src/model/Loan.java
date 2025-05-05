package model;

public class Loan extends Customer{
	private int loanId;
	private double principalAmount;
	private Customer customer;
	private double interestRate;
	private int loanTerm;
	private String loanType;
	private String loanStatus;
    
	public Loan() {
		
	}
	
	public Loan(int loanId, double principalAmount, double interestRate, int loanTerm, String loanStatus) {
		this.loanId = loanId;
		this.principalAmount = principalAmount;
		this.loanTerm = loanTerm;
		this.loanStatus = loanStatus;
	}
	public Loan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, String loanType, String loanStatus) {
		this.loanId = loanId;
		this.customer = customer;
		this.principalAmount = principalAmount;
		this.interestRate = interestRate;
		this.loanTerm = loanTerm;
		this.setLoanType(loanType);
		this.setLoanStatus(loanStatus);
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(int loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
			this.loanType = loanType;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
			this.loanStatus = loanStatus;
	}

}
