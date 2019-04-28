package bank.business.domain;

import java.util.Date;

/**
 * @author Ingrid Nunes
 * 
 */
public abstract class Transaction {
	
	public static String pending_status = "PENDENTE";

	private CurrentAccount account;
	private double amount;
	private Date date;
	private OperationLocation location;
	private String status = "INDEFINIDO"; //Pendente, cancelado, finalizado,indefinido

	protected Transaction(OperationLocation location, CurrentAccount account,
			double amount,String status) {
		this.location = location;
		this.date = new Date(System.currentTimeMillis());
		this.account = account;
		this.amount = amount;
		this.status = status;
	}
	
	protected Transaction(OperationLocation location, CurrentAccount account,
			double amount) {
		this.location = location;
		this.date = new Date(System.currentTimeMillis());
		this.account = account;
		this.amount = amount;
	}

	/**
	 * @return the account
	 */
	public CurrentAccount getAccount() {
		return account;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	//retorna o status atual da transação
	public String getStatus() {
		return status;
	}

	/**
	 * @return the location
	 */
	public OperationLocation getLocation() {
		return location;
	}

	/**
	 * This method is here for initializing the database.
	 * 
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	//altera o status atual da transação. TODO: Validar status
	public void setStatus(String status) {
		this.status = status;
	}

}
