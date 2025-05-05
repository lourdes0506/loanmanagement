package myexceptions;

public class InValidLoanException extends Exception {
	public InValidLoanException(String inValidLoanExceptionMsg){
		super(inValidLoanExceptionMsg);
	}

}

