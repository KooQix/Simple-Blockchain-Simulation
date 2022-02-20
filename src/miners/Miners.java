package miners;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import blockchain.Block;

public class Miners {
	private static Set<Miner> miners = new HashSet<>();
	private static int nbMiners = 5;

	/**
	 * Initializes a set of nbMiners miners
	 */
	private static void init() {
		for (int i = 0; i < nbMiners; i++) {
			miners.add(new Miner("Miner" + i));
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
		System.out.println("Sharing block to miners...");
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
	 */
	private static void findNonce(Block block) {
		long nonce = 0;

		// Getting nonce
		// Simulating the competition between miners to get the nonce
		nonce = getMiner().getNonce(nonce);
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
			System.out.println(m.getName() + " has found a nonce!");
			System.out.println("Verifying nonce...");
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
		System.out.println("Miners are mining block " + block.getId() + "...");
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
