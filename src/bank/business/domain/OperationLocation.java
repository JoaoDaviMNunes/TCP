package bank.business.domain;

import bank.business.AccountOperationService;
import bank.business.BusinessException;

/**
 * @author Ingrid Nunes
 * 
 */
public abstract class OperationLocation {

	private long number;

	public OperationLocation(long number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationLocation other = (OperationLocation) obj;
		if (number != other.number)
			return false;
		return true;
	}

	/**
	 * @return the number
	 */
	public long getNumber() {
		return number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (number ^ (number >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + number;
	}
	
	abstract public Deposit initializeDeposit(AccountOperationService accountOperationService,
			long operationLocation, long branch,
			long accountNumber, long envelope, double amount) throws BusinessException;

}
