import blockchain.Block;
import blockchain.Blockchain;
import resources.Color;
import resources.ConsoleColors;

public class App {
	public static void main(String[] args) throws Exception {

		//////////////////// Creating a blockchain and adding blocks
		//////////////////// \\\\\\\\\\\\\\\\\\\\

		Blockchain chain = new Blockchain();

		chain.addBlock();
		ConsoleColors.print(chain.toString(), Color.YELLOW);
		Thread.sleep(2000);

		chain.addBlock();
		ConsoleColors.print(chain.toString(), Color.YELLOW);
		Thread.sleep(2000);

		chain.addBlock();
		ConsoleColors.print(chain.toString(), Color.YELLOW);
		Thread.sleep(2000);

		chain.addBlock();
		ConsoleColors.print(chain.toString(), Color.YELLOW);

		// Create a copy of the blockchain
		Blockchain copy = new Blockchain(chain);

		//////////////////// Tempering the first blockchain \\\\\\\\\\\\\\\\\\\\

		Thread.sleep(8000);
		ConsoleColors.print("##### Modifying the blockchain... #####", Color.RED_BOLD);

		Block block = chain.getBlock(2);

		block.setData("Tempered data");
		ConsoleColors.print("\n\nTempered Blockchain:\n" + chain, Color.RED);

		//////////////////// Peer to peer verification \\\\\\\\\\\\\\\\\\\\

		System.out.println("\n\nVerifying blockchains");
		if (chain.equals(copy))
			ConsoleColors.print("All good! :)", Color.GREEN);
		else
			ConsoleColors.print("Blockchain has been tempered with!!!", Color.RED_BOLD);
	}
}
