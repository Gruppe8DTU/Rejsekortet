package persistance;

import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.Random;


public class Encrypter {
	
	private static String salt;
    private int iterationCount = 1024;
    private int keyStrength = 128;
    private SecretKey key;
    private static int SALT_LENGTH = 32;
    
    public Encrypter(String passPhrase, String salt) throws Exception {
    	this.salt = salt;
    	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    	KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt.getBytes(), iterationCount, keyStrength);
    	SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
    }
    public String getSalt(){
    	System.out.println("salt : " + salt);
    	return salt;
    }
    public String getKey(){
    	System.out.println("key : " + key);
    	return key.toString();
    }
    public static String generateSalt(){
    	Random rnd = new Random(System.currentTimeMillis());
    	byte[] tmpSalt = new byte[SALT_LENGTH];
    	rnd.nextBytes(tmpSalt);
    	System.out.println("byte arr again : " + tmpSalt);
    	String str = tmpSalt.toString();
    	return str;
    }    	
}
    

