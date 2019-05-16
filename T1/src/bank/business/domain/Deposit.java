package bank.business.domain;

import java.math.BigDecimal;

/**
 * @author Ingrid Nunes
 * 
 */
public class Deposit extends Transaction {

	private long envelope;
	
	public static final BigDecimal verification_amount = BigDecimal.valueOf(100.00);

	public Deposit(OperationLocation location, CurrentAccount account,
			long envelope, double amount,String status) {
		super(location, account, amount,status);
		this.envelope = envelope;
		
	}

	/**
	 * @return the envelope
	 */
	public long getEnvelope() {
		return envelope;
	}

}
