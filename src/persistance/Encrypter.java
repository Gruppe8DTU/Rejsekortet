package persistance;

import javax.crypto.Cipher;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.AlgorithmParameters;
import javax.crypto.spec.IvParameterSpec;

import java.util.Random;


public class Encrypter {
	
	private static byte[] salt;
    private int iterationCount = 1024;
    private int keyStrength = 256;
    private SecretKey key;
    private static int SALT_LENGTH = 32;
    
    public Encrypter(String passPhrase, byte[] salt) throws Exception {
    	this.salt = salt;
    	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    	KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
    	SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
    }
    public byte[] getSalt(){
    	return salt;
    }
    public String getKey(){
    	return key.toString();
    }
    public static byte[] generateSalt(){
    	Random rnd = new Random();
    	byte[] tmpSalt = new byte[SALT_LENGTH];
    	rnd.nextBytes(tmpSalt);
    	return tmpSalt;
    }    	
}
    

