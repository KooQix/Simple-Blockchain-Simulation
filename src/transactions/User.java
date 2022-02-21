package transactions;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import resources.RSAKeyPairGenerator;

public class User {
	private String name;
	private PrivateKey privateAddress;
	private PublicKey publicAddress;

	/**
	 * Create a new user
	 * 
	 * @param name
	 * @throws NoSuchAlgorithmException
	 */
	public User(String name) throws NoSuchAlgorithmException {
		this.name = name;

		// Generates a private and public key for this user
		RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
		this.publicAddress = generator.getPublicKey();
		this.privateAddress = generator.getPrivateKey();
	}

	/**
	 * Takes a transaction hashed message and encrypt it with its private key
	 * 
	 * @param transaction
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] sign(Transaction transaction)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, this.privateAddress);

		return cipher.doFinal(transaction.getHashedMessage().getBytes());
	}

	/**
	 * Verifies that the sender has signed the transaction, and not someone else
	 * 
	 * @param transaction
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public boolean verify(Transaction transaction)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, transaction.getSender().getPublicAddress());
		String decrypted_hash = new String(cipher.doFinal(transaction.getSignature()));
		return decrypted_hash.equals(transaction.getHashedMessage());
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
	public PublicKey getPublicAddress() {
		return publicAddress;
	}

}
