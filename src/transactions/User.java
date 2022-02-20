package transactions;

import java.security.NoSuchAlgorithmException;

import resources.RSAKeyPairGenerator;

public class User {
	private String name;
	private String privateAddress;
	private String publicAddress;

	public User(String name) throws NoSuchAlgorithmException {
		this.name = name;

		RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
		this.publicAddress = generator.getPublicKey().toString();
		this.privateAddress = generator.getPrivateKey().toString();
	}

	public void sign(Transaction transaction) {
	}

	//////////////////// Getters & Setters \\\\\\\\\\\\\\\\\\\\

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the publicAddress
	 */
	public String getPublicAddress() {
		return publicAddress;
	}

}
