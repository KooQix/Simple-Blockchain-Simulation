package blockchain;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.InvalidAttributeValueException;

import miners.Miners;
import resources.Color;
import resources.ConsoleColors;
import transactions.Transaction;

public class Blockchain {

	private Block lastBlock;

	/**
	 * Create a new blockchain
	 */
	public Blockchain() {
		this.lastBlock = null;
	}

	/**
	 * Copy a blockchain
	 * 
	 * @param blockchain
	 */
	public Blockchain(Blockchain blockchain) {

		Block block = blockchain.getLastBlock();
		this.lastBlock = new Block(block.getId(), block.getData());
	}

	/**
	 * Each transaction is verified (receiver verifies that the transaction has bee
	 * signed by the sender)
	 * 
	 * @param transactions
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private boolean verifyTransactions(ArrayList<Transaction> transactions) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		int nb = 0;
		for (int i = 0; i < transactions.size(); i++) {
			if (transactions.get(i).getReceiver().verify(transactions.get(i))) {
				nb++;
			}
		}
		// If one transaction is not verified, the whole block will be rejected
		return nb == transactions.size();
	}

	/**
	 * Add a block to the blockchain
	 * 
	 * @param block
	 * @throws InvalidAttributeValueException
	 * @throws InterruptedException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public void addBlock() throws InvalidAttributeValueException, InterruptedException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// Get a bunch of transactions that needs to be processed
		ConsoleColors.print("Waiting for incoming transactions...", Color.PURPLE);
		ArrayList<Transaction> transactions = Transaction.bunchOfTransactions();
		ConsoleColors.print("Verifying their validity...", Color.PURPLE);
		Thread.sleep(2000);

		// Verify the validity of the transactions.
		// Not valid => reject the whole block
		if (!verifyTransactions(transactions))
			return;

		// Transactions have been validated, proceed to adding the block to the
		// blockchain
		String data = transactions.toString();

		if (lastBlock != null && data.equals(lastBlock.getData())) {
			throw new InvalidAttributeValueException("ERROR: Block is a duplicate");
		}
		int id = this.lastBlock == null ? 0 : this.lastBlock.getId();

		// Create a new block with these transactions
		Block block = new Block(id + 1, data);

		// Link new last block with old last block
		block.setPreviousBlock(this.lastBlock);
		ConsoleColors.print("\n\n\n\n##### New block to add #####", Color.BLUE);
		System.out.println(block);

		// Add the block to the blockchain
		Miners.mine(block);

		// Since we're here, the block should give a valid hash
		if (!Block.isValidHash(block.getHash())) {
			System.err.println("Block not valid, not added");
			return;
		}
		// Block is valid, add it to the blockchain
		ConsoleColors.print("Adding block to the blockchain", Color.GREEN);
		this.lastBlock = block;
	}

	//////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\

	/**
	 * @return the lastBlock
	 */
	public Block getLastBlock() {
		return lastBlock;
	}

	/**
	 * Get the block where block.id == blockID on the blockchain
	 * 
	 * @param blockId
	 * @return
	 */
	public Block getBlock(int blockId) {
		if (blockId < 1) {
			return null;
		}

		Block block = this.lastBlock;
		int i = 1;
		while (this.lastBlock.getId() - i >= blockId) {
			block = block.getPreviousBlock();
			i++;
		}
		return blockId == block.getId() ? block : null;

	}

	@Override
	public String toString() {
		if (this.lastBlock == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("\nBlockchain:\n");

		Block block = this.lastBlock;
		while (block != null) {
			sb.append(block);
			block = block.getPreviousBlock();
		}
		return sb.toString() + "\n\n";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Blockchain)) {
			return false;
		}

		// Since it's a linked list, we solely need to check the last element
		Blockchain blockchain = (Blockchain) o;
		return this.getLastBlock().equals(blockchain.getLastBlock());
	}

}
