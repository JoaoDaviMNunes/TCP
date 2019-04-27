package bank.business.domain;

import java.util.ArrayList;
import java.util.List;

import bank.business.AccountOperationService;
import bank.business.BusinessException;

/**
 * @author Ingrid Nunes
 * 
 */
public class Branch extends OperationLocation {

	private List<CurrentAccount> accounts;
	private String name;

	public Branch(long number) {
		super(number);
		this.accounts = new ArrayList<>();
	}

	public Branch(long number, String name) {
		this(number);
		this.name = name;
	}

	public void addAccount(CurrentAccount currentAccount) {
		accounts.add(currentAccount);
	}

	/**
	 * @return the accounts
	 */
	public List<CurrentAccount> getAccounts() {
		return accounts;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public Deposit initializeDeposit(AccountOperationService accountOperationService,long operationLocation, long branch,
			long accountNumber, long envelope, double amount) throws BusinessException{
		Deposit deposit = accountOperationService.deposit(operationLocation,branch,accountNumber,envelope,amount,"FINALIZADO");
		CurrentAccount currentAccount = accountOperationService.readCurrentAccount(branch,accountNumber);
		currentAccount.depositAmount(amount);
		return deposit;
	}

	@Override
	public String toString() {
		return name + " (" + getNumber() + ")";
	}
	

}
