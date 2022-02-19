import java.util.Objects;

public class Block {
	private int id;
	private int nonce;
	private String data;
	private String hash;
	private Block previousBlock;
	private String NO_PREVIOUS = "0000000000000000000000000000000000000000000000000000000000000000";

	/**
	 * Create a new block
	 * 
	 * @param id
	 * @param data
	 */
	public Block(int id, String data) {
		this.id = id;
		this.data = data;
		this.nonce = 0;
		this.previousBlock = null;
		this.hash = this.getHash();
	}

	/**
	 * Concatenate id, nonce, data and previous block hash
	 * 
	 * @return
	 */
	private String concatBlock() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.id);
		sb.append(this.nonce);
		sb.append(this.data);

		if (this.previousBlock != null)
			sb.append(this.previousBlock.getHash());

		else
			sb.append(NO_PREVIOUS);
		return sb.toString();
	}

	/**
	 * Check if hash is valid (here, starts by 0000)
	 * 
	 * @return boolean
	 */
	private boolean isValidHash(String hash) {
		if (hash == null || hash.length() == 0) {
			return false;
		}
		char c = this.getHash().charAt(0);

		return (c == '0') && (c == hash.charAt(1)) && (c == hash.charAt(2)) && (c == hash.charAt(3));
	}

	/**
	 * Get nonce that gets a valid hash
	 */
	public void mine() {
		System.out.println("Mining block " + this.getId() + "...");
		if (!isValidHash(this.hash))
			this.nonce = 0;

		while (!isValidHash(this.hash)) {
			this.nonce++;
			this.hash = this.getHash();
		}
	}

	/**
	 * Check if block is valid
	 * 
	 * @return
	 */
	public boolean isValid() {
		return this.isValidHash(this.hash);
	}

	/**
	 * Check if block has a previous block
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		return this.getPreviousBlock() != null;
	}

	//////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Brute force nonce until hash is valid and return the nonce
	 */
	public int getNonce() {
		return nonce;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Re calculated each time, to avoid tempered data
	 * 
	 * @return the hash
	 */
	public String getHash() {
		return SHA256Hash.hash(this.concatBlock());
	}

	/**
	 * @return the previousBlock
	 */
	public Block getPreviousBlock() {
		return previousBlock;
	}

	/**
	 * @param previousBlock the previousBlock to set
	 */
	public void setPreviousBlock(Block previousBlock) {
		this.previousBlock = previousBlock;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\tid: " + getId() + "\n");
		sb.append("\tnonce: " + getNonce() + "\n");
		sb.append("\tdata: " + getData() + "\n");

		String previous = getPreviousBlock() != null ? getPreviousBlock().getHash() : NO_PREVIOUS;
		sb.append("\tprevious: " + previous + "\n");
		sb.append("\thash: " + getHash() + "\n},\n");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Block)) {
			return false;
		}
		Block block = (Block) o;
		return nonce == block.getNonce() && Objects.equals(data, block.data)
				&& Objects.equals(hash, block.hash) && Objects.equals(previousBlock, block.previousBlock);
	}

}
