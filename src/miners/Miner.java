package miners;

import blockchain.Block;

public class Miner {
	private String name;
	private Block block;

	public Miner(String name) {
		this.name = name;
	}

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
