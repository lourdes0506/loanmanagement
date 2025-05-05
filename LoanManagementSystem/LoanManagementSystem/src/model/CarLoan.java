package model;

public class CarLoan extends Loan{
	private String carModel;
    private int carValue;
    
    public CarLoan(int loanId, double principalAmount, double interestRate,int loanTerm, String loanStatus, String carModel, int carValue) {
   	 super(loanId, principalAmount, interestRate, loanTerm, loanStatus);
   	 this.carModel = carModel;
   	 this.carValue = carValue;
    }

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public int getCarValue() {
		return carValue;
	}

	public void setCarValue(int carValue) {
		this.carValue = carValue;
	}
    
}
