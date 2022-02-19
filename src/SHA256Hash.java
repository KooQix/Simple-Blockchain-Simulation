import java.security.MessageDigest;

public class SHA256Hash {
	private SHA256Hash() {
	}

	/**
	 * Hash the given data
	 * 
	 * @param data
	 * @return
	 */
	public static String hash(String data) {
		String res = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(data.getBytes("UTF-8"));
			return bytesToHex(hash);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Converts a bytes array into a hex string
	 * 
	 * @param hash
	 * @return
	 */
	private static String bytesToHex(byte[] hash) {
		StringBuilder sb = new StringBuilder();
		for (byte aByte : hash) {
			sb.append(String.format("%02x", aByte));
		}
		return sb.toString();
	}
}
