package resources;

import java.security.*;

public class RSAKeyPairGenerator {
	private PrivateKey privateKey;
	private PublicKey publicKey;

	/**
	 * Generate private and public keys
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public RSAKeyPairGenerator() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(4096);

		// Generate key pairs
		KeyPair pair = keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	/**
	 * Get private key
	 * 
	 * @return PrivateKey
	 */
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	/**
	 * Get public key from
	 * 
	 * @return PublicKey
	 */
	public PublicKey getPublicKey() {
		return this.publicKey;
	}
}
