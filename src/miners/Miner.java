package miners;

import blockchain.Block;

public class Miner {
	private String name;
	private Block block;

	/**
	 * Create a new miner
	 * 
	 * @param name
	 */
	public Miner(String name) {
		this.name = name;
	}

	/**
	 * From a given nonce, iterates to find the nonce validating the block and
	 * return the new nonce
	 * To simulate the competition between the miners, each miner iterates at most a
	 * given number of times, max_it
	 * 
	 * @param nonce
	 * @return
	 */
	public long getNonce(long nonce) {
		long n = nonce;

		long max_it = Math.round(100 * Math.random());
		int i = 0;
		while (i < max_it && !Block.isValidHash(block.getHash())) {
			n++;
			this.block.setNonce(n);
			i++;
		}

		return n;
	}

	/**
	 * Verify if the given nonce gives a valid block (on its own copy of the block)
	 * 
	 * @param nonce
	 * @return
	 */
	public boolean verifyNonce(long nonce) {
		block.setNonce(nonce);
		return Block.isValidHash(block.getHash());
	}

	//////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\

	/**
	 * @param block the block to set
	 */
	public void setBlock(Block block) {
		this.block = block;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
