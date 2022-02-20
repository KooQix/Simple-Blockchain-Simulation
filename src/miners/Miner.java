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
	 * @param nonce The nonce to start iterate from. A miner will iterate a given
	 *              number of times from this nonce and then return the new nonce to
	 *              pass to the next (To simulate the competition)
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
