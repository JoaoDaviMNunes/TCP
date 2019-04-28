package bank.business.domain;
import java.math.BigDecimal;

import bank.business.AccountOperationService;
import bank.business.BusinessException;
/**
 * @author Ingrid Nunes
 * 
 */
public class ATM extends OperationLocation {

	public ATM(long number) {
		super(number);
	}
	
	public Deposit initializeDeposit(AccountOperationService accountOperationService,long operationLocation, long branch,
			long accountNumber, long envelope, double amount) throws BusinessException{
		
		
		//BigDecimal verification_amount = BigDecimal.valueOf(100.00);
		BigDecimal deposit_amount = BigDecimal.valueOf(amount);
		
		Deposit deposit = accountOperationService.deposit(operationLocation,branch,accountNumber,envelope,amount,"PENDENTE");
		CurrentAccount currentAccount = accountOperationService.readCurrentAccount(branch,
				accountNumber);
		
		//BigDecimal dá maior segurança na comparação de valores fracionários comparado
		//com uma comparação ==.
		//compareTo retorna -1 se menor, 0 se igual e 1 se maior.
		if( deposit_amount.compareTo(Deposit.verification_amount) <= 0) {//menor ou igual 
			currentAccount.depositAmount(amount);
			
		}
		
		return deposit;
		
	}
	

}
