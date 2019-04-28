package bank.ui.text.command;

import bank.business.AccountOperationService;
import bank.business.domain.Branch;
import bank.business.domain.CurrentAccountId;
import bank.business.domain.Transaction;
import bank.business.domain.Deposit;
import bank.ui.text.BankTextInterface;
import bank.ui.text.UIUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingDepositsCommand extends Command {

	private final AccountOperationService accountOperationService;
	

	public PendingDepositsCommand(BankTextInterface bankInterface,
			AccountOperationService accountOperationService) {
		super(bankInterface);
		this.accountOperationService = accountOperationService;
	}
	@Override
	public void execute() throws Exception {
		Long branch = bankInterface.readBranchId();
		Long accountNumber = bankInterface.readCurrentAccountNumber();
		
		List<Transaction> transactions = accountOperationService.getDepositByStatus(branch, accountNumber, Transaction.pending_status);
		new StatementCommand(bankInterface, accountOperationService).printStatement(new CurrentAccountId(new Branch(branch), accountNumber),
				transactions);
		
		
		
		
		String formatted_message = getTextManager().getText("message.select.deposit", new String[] {"1", Integer.toString(transactions.size())} );
		int item = UIUtils.INSTANCE.readInteger(formatted_message,1,transactions.size());
		
		String choice = UIUtils.INSTANCE.readString("message.accept.refuse.deposit");
		
		Deposit selected_deposit = (Deposit) transactions.get(item-1);
		
		
		Map<String,String> status_map = status_map_fill();
		Map<String,Double> balance_variation =  balance_map_fill(selected_deposit);
		
		System.out.println(item);
		System.out.println(choice);
		System.out.println(status_map.get(choice));
		System.out.println(balance_variation.get(status_map.get(choice)));		
		
		Deposit updated_deposit = accountOperationService.updateDeposits(branch, accountNumber, selected_deposit, status_map.get(choice), balance_variation.get(status_map.get(choice)));
		
		System.out.println("Status: " + updated_deposit.getStatus());
		System.out.println(getTextManager().getText("deposit") + ": "
				+ updated_deposit.getAmount());
		//CurrentAccount currentAccount = accountOperationService.readCurrentAccount(branch, accountNumber);
		//currentAccount.updateDeposit(selected_deposit, status_map.get(choice), balance_variation.get(status_map.get(choice)));
		

	}
	
	private Double AmountToDebit(Double amount) {
		BigDecimal debit_amount = BigDecimal.valueOf(amount);
		return (debit_amount.compareTo(Deposit.verification_amount) <= 0) ? (-1.0 * amount) : 0.0;
		
	}
	
	private Map<String,String> status_map_fill(){
		Map<String,String> status_map = new HashMap<>();
		
		status_map.put("A", Transaction.accepted_status);
		status_map.put("0", Transaction.accepted_status);
		status_map.put("R", Transaction.refused_status);
		status_map.put("1", Transaction.refused_status);
		
		return status_map;
		
		
	}
	
	private Map<String,Double> balance_map_fill(Deposit selected_deposit) {
		Map<String,Double> balance_variation = new HashMap<>();
		balance_variation.put(Transaction.accepted_status, selected_deposit.getAmount());
		balance_variation.put(Transaction.refused_status, AmountToDebit(selected_deposit.getAmount()));
		
		return balance_variation;
	}

}
