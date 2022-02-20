package transactions;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Transaction {
	private User receiver;
	private User sender;
	private double amount;

	/**
	 * Create a new transaction
	 * 
	 * @param receiver
	 * @param sender
	 * @param amount
	 */
	public Transaction(User sender, User receiver, double amount) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;

		// Sender signs the transaction, to ensure that he initiated the transaction
		sender.sign(this);
	}

	public static ArrayList<Transaction> bunchOfTransactions() throws NoSuchAlgorithmException {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		// Each block is constituted of 3 transactions
		for (int i = 0; i < 3; i++) {
			User sender = new User("User" + Math.round(100 * Math.random()));
			User receiver = new User("User" + Math.round(100 * Math.random()));
			double amount = 1000 * Math.random();
			transactions.add(new Transaction(sender, receiver, amount));
		}
		return transactions;
	}

	//////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\

	/**
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ " + this.getSender().getName() + ": " + this.getAmount() + " -> " + this.getReceiver().getName()
				+ " ]");
		return sb.toString();
	}
}
