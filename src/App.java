
public class App {
	public static void main(String[] args) throws Exception {

		//////////////////// Creating a blockchain and adding blocks
		//////////////////// \\\\\\\\\\\\\\\\\\\\

		System.out.println("\tAdding blocks to the blockchain...\n\n");
		Blockchain chain = new Blockchain();

		chain.addBlock("First block data");
		System.out.println("\n" + chain);
		Thread.sleep(2000);

		chain.addBlock("Second block data");
		System.out.println("\n" + chain);
		Thread.sleep(2000);

		chain.addBlock("Third block data");
		System.out.println("\n" + chain);
		Thread.sleep(2000);

		chain.addBlock("Forth block data");
		System.out.println("\n" + chain);

		// Create a copy of the blockchain
		Blockchain copy = new Blockchain(chain);

		//////////////////// Tempering the first blockchain \\\\\\\\\\\\\\\\\\\\

		System.out.println("\n\nModifying the blockchain...");

		Block block = chain.getBlock(2);

		block.setData("Tempered data");
		System.out.println("\n\nTempered Blockchain:\n" + chain);

		//////////////////// Peer to peer verification \\\\\\\\\\\\\\\\\\\\

		System.out.println("\n\nVerifying blockchains");
		System.out.println(chain.equals(copy) ? "All good! :)" : "Blockchain has been tempered with!!!");
	}
}
