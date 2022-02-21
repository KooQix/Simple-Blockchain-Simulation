package miners;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import blockchain.Block;
import resources.Color;
import resources.ConsoleColors;

public class Miners {
	private static Set<Miner> miners = new HashSet<>();
	private static int nbMiners = 5;

	private Miners() {
	}

	/**
	 * Initializes a set of nbMiners miners
	 */
	private static void init() {
		for (int i = 0; i < nbMiners; i++) {
			miners.add(new Miner("Miner" + Math.round(1000 * Math.random())));
		}
	}

	/**
	 * Get a miner from the set
	 * 
	 * @return
	 */
	private static Miner getMiner() {
		Iterator<Miner> it = getMiners().iterator();
		return it.next();
	}

	/**
	 * Share the block to add to the blockchain to the miners
	 * 
	 * @param block
	 */
	private static void shareBlock(Block block) {
		ConsoleColors.print("Sharing block to miners...", Color.CYAN);
		Iterator<Miner> it = getMiners().iterator();

		Miner m;
		while (it.hasNext()) {
			m = it.next();
			// Share a copy of the block, to each miner
			m.setBlock(new Block(block));
		}
	}

	/**
	 * Simulating the competition between miners to find the nonce of the given
	 * block
	 * 
	 * @param block
	 * @throws InterruptedException
	 */
	private static void findNonce(Block block) throws InterruptedException {

		// Getting nonce
		// Simulating the competition between miners to get the nonce
		long nonce = getMiner().getNonce(0);
		block.setNonce(nonce);

		Miner m = null;
		while (!Block.isValidHash(block.getHash())) {
			Iterator<Miner> it = getMiners().iterator();
			while (it.hasNext() && !Block.isValidHash(block.getHash())) {
				m = it.next();
				nonce = m.getNonce(nonce);
				block.setNonce(nonce);
			}
		}

		if (m != null) {
			ConsoleColors.print(m.getName() + " has found a nonce!", Color.GREEN_BOLD);
			Thread.sleep(2000);
			System.out.println("Verifying nonce...");
			Thread.sleep(1000);
		}
	}

	/**
	 * Once the nonce has been found by one miner, each miner verify if the found
	 * nonce gives a valid hash on its own copy of the block
	 * 
	 * @param nonce
	 * @return
	 */
	private static boolean verifyBlock(long nonce) {
		int nb = 0;

		Iterator<Miner> it = getMiners().iterator();
		while (it.hasNext()) {
			Miner m = it.next();
			nb = m.verifyNonce(nonce) ? nb + 1 : nb;
		}

		// The majority of miners have approved the nonce
		return nb / nbMiners > 1 / 2;
	}

	/**
	 * Simulating the mining process eg sharing the block to the miners, finding the
	 * nonce of the block and then verifying the found nonce
	 * 
	 * @param block
	 * @throws InterruptedException
	 */
	public static void mine(Block block) throws InterruptedException {
		shareBlock(block);
		ConsoleColors.print("Miners are mining block " + block.getId() + "...", Color.CYAN);
		Thread.sleep(4000);
		while (!verifyBlock(block.getNonce())) {
			findNonce(block);
		}

		// Since we're here, a miner has found a nonce that gives a valid hash.
		// verifyBlock: every miner checks if its block, with the nonce found, gives a
		// valid hash (verify if data hasn't been tempered while finding the nonce)

	}

	//////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\

	/**
	 * @return the miners
	 */
	public static Set<Miner> getMiners() {
		if (miners.isEmpty())
			init();

		return miners;
	}

}
