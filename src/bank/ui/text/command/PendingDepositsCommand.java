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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PendingDepositsCommand extends Command {

	private final AccountOperationService accountOperationService;
	
	private final List<String> acceptList = new ArrayList<String>(Arrays.asList("A","0","ACEITAR","CONFIRMAR","FINALIZAR"));
	private final List<String> rejectList = new ArrayList<String>(Arrays.asList("R","1","REJEITAR","CANCELAR","RECUSAR"));
	

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
		
		System.out.println(acceptList + " : " + "Aceitar");
		System.out.println(rejectList + " : " + "Rejeitar");
		
		String choice = UIUtils.INSTANCE.readString("message.accept.refuse.deposit").toUpperCase();
		
		Deposit selected_deposit = (Deposit) transactions.get(item-1);
		
		
		Map<String,String> status_map = status_map_fill();
		Map<String,Double> balance_variation =  balance_map_fill(selected_deposit);
		
	
		
		Deposit updated_deposit = accountOperationService.updateDeposits(branch, accountNumber, selected_deposit, status_map.get(choice), balance_variation.get(status_map.get(choice)));
		
		System.out.println("Status: " + updated_deposit.getStatus());
		System.out.println(getTextManager().getText("deposit") + ": "
				+ updated_deposit.getAmount());
		

	}
	
	private Double AmountToDebit(Double amount) {
		//BigDecimal valueOf recebe um valor e retorna um novo objeto do tipo Bigdecimal com o valor convertido
		//CompareTo retorna -1 se o valor for menor, 0 se igual e 1 se maior. <= significa, então, menor ou igual ao valor de comparação
		BigDecimal DebitAmount = BigDecimal.valueOf(amount);
		return (DebitAmount.compareTo(Deposit.verification_amount) <= 0) ? (-1.0 * amount) : 0.0;
		
	}
	
	private Double AmountToCredit(Double amount) {
		BigDecimal CreditAmount = BigDecimal.valueOf(amount);
		return (CreditAmount.compareTo(Deposit.verification_amount) <= 0) ? (0.0) : amount;
		
	}
	
	private Map<String,String> status_map_fill(){
		Map<String,String> status_map = new HashMap<>();
		
		for(String key : acceptList) {
			status_map.put(key, Transaction.accepted_status);
		}
		
		for(String key : rejectList) {
			status_map.put(key, Transaction.refused_status);
		}
		
		return status_map;
		
		
	}
	
	private Map<String,Double> balance_map_fill(Deposit selected_deposit) {
		Map<String,Double> balance_variation = new HashMap<>();
		balance_variation.put(Transaction.accepted_status, AmountToCredit(selected_deposit.getAmount()));
		balance_variation.put(Transaction.refused_status, AmountToDebit(selected_deposit.getAmount()));
		
		return balance_variation;
	}

}
