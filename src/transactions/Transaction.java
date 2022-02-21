package transactions;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import resources.SHA256Hash;

public class Transaction {
	private User receiver;
	private User sender;
	private double amount;
	private String hashedMessage;
	private byte[] signature;

	/**
	 * Create a new transaction
	 * 
	 * @param receiver
	 * @param sender
	 * @param amount
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public Transaction(User sender, User receiver, double amount)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;

		// Hash from message sender, receiver, amount
		this.hashedMessage = SHA256Hash.hash(this.toString());

		// Sender signs the transaction, to ensure that he initiated it
		// Eg encrypt hashed message with sender private key
		this.signature = sender.sign(this);
	}

	/**
	 * Create an array of 3 transactions
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public static ArrayList<Transaction> bunchOfTransactions() throws NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		// Each block is constituted of 3 transactions
		for (int i = 0; i < 3; i++) {
			// Create a new transaction
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

	/**
	 * @return the hashedMessage
	 */
	public String getHashedMessage() {
		return hashedMessage;
	}

	/**
	 * @return the signature
	 */
	public byte[] getSignature() {
		return signature;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ " + this.getSender().getName() + ": " + this.getAmount() + " -> " + this.getReceiver().getName()
				+ " ]");
		return sb.toString();
	}
}
