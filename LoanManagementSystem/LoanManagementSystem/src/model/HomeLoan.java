package model;

public class HomeLoan extends Loan{
     private String propertyAddress;
     private int propertyValue;
     
     public HomeLoan(int loanId, double principalAmount, double interestRate,int loanTerm, String loanStatus, String propertyAddress, int propertyValue) {
    	 super(loanId, principalAmount, interestRate, loanTerm, loanStatus);
    	 this.propertyAddress = propertyAddress;
    	 this.propertyValue = propertyValue;
     }

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public int getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}
     
}
