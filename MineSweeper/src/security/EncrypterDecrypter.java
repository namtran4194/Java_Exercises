package security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Order for encrypt: <i>getBytes, encrypt, encode, toString</i>
 * <p>
 * Order for decrypt: <i>getBytes, decode, decrypt, toString</i>
 * <p>
 * Use to encrypt and decrypt for high scores file
 * AES Algorithm (Advanced Encryption Standard)
 */
public class EncrypterDecrypter {
	Cipher eCipher, dCipher;
	public static final String keyValue = "I'm awe-handsome"; // must be 16 bytes
	public static SecretKeySpec keySpec = new SecretKeySpec(keyValue.getBytes(), "AES");

	public EncrypterDecrypter() {
		try {
			// Can use AlgorithmParameterSpec instead of IvParameterSpec
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(keyValue.getBytes());
			eCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			eCipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
			dCipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchAlgorithmException e) {
			System.out.println(e.toString() + ": 1");
		}
	}

	/**
	 * encrypt() inputs a string and returns an encrypted version of that String
	 * @throws IOException 
	 * @throws IllegalBlockSizeException 
	 */
	public String encrypt(String valueToEnc){
		String encryptedValue = null;
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = valueToEnc.getBytes(StandardCharsets.UTF_8);
			// Encrypt
			byte[] encValue = eCipher.doFinal(utf8);
			// Encode bytes to base64 to get a string
			encryptedValue = Base64.getEncoder().encodeToString(encValue);
		} catch (BadPaddingException | IllegalBlockSizeException e) {
			System.out.println(e.toString() + ": 2");
		}
		return encryptedValue;
	}

	/**
	 * decrypt() inputs a string and returns an encrypted version of that String
	 */
	public String decrypt(String valueToDec) {
		String decryptedValue = null;
		try {
			// Decode base64 to get bytes
			byte[] decValue = Base64.getDecoder().decode(valueToDec);
			// Decrypt
			byte[] utf8 = dCipher.doFinal(decValue);
			// Decode using utf-8
			decryptedValue = new String(utf8, StandardCharsets.UTF_8);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println(e.toString() + ": 3");
		}
		return decryptedValue;
	}
}
