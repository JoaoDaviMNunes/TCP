package bank.ui.text.command;

import bank.business.AccountOperationService;
import bank.business.domain.Branch;
import bank.business.domain.CurrentAccountId;
import bank.business.domain.Transaction;
import bank.ui.text.BankTextInterface;
import java.util.Calendar;
import java.util.List;

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
		

	}

}
