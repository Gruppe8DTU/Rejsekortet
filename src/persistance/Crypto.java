/*
 * Klasse der haandterer kryptering og dekryptering af passwords foer de sendes til databasen
 */

package persistance;

import javax.crypto.Cipher;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.AlgorithmParameters;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class Crypto {

	    Cipher dcipher;

	    private byte[] salt = new String("12345678").getBytes();
	    private int iterationCount = 1024;
	    private int keyStrength = 128;
	    private SecretKey key;
	    private byte[] iv;
	    
	    PBEKeySpec spec;

	    public Crypto(String passPhrase) throws Exception {
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	        spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
	        System.out.println("spec = " + spec);
	        SecretKey tmp = factory.generateSecret(spec);
	        System.out.println(tmp);
	        System.out.println("secret key tmp : " + tmp);
	        key = new SecretKeySpec(tmp.getEncoded(), "AES");
	        System.out.println("key : " + key);
	        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        System.out.println(dcipher.getIV());
	    }
	    public String getSpec(){
	    	return spec.getPassword().toString();
	    }
	    public String getSecretKey(){
	    	return key.toString();
	    }
	    public String encoded(){
	    	return key.getEncoded().toString();
	    }

	    public String encrypt(String data) throws Exception {
	        dcipher.init(Cipher.ENCRYPT_MODE, key);
	        AlgorithmParameters params = dcipher.getParameters();
	        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
	        byte[] utf8EncryptedData = dcipher.doFinal(data.getBytes());
	        byte[] base64EncryptedData = Base64.encodeBase64(utf8EncryptedData);
	        System.out.println("Encrypted Data " + base64EncryptedData);
	        System.out.println(base64EncryptedData.length);
	        String returnValue = new String(base64EncryptedData);
	        return returnValue;
	    }

	    public String decrypt(String base64EncryptedData) throws Exception {
	        dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
	        byte[] decryptedData = Base64.decodeBase64(base64EncryptedData);
	        byte[] utf8DecryptedData = dcipher.doFinal(decryptedData);
	        return new String(utf8DecryptedData, "UTF8");
	    }
}
	

