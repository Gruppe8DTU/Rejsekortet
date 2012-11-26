package persistance;

public class main {
	
	static private byte[] salt = new String("12345678").getBytes();
	

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		Encrypter enc1 = new Encrypter("hej", salt);
		byte[] salt2 = enc1.getSalt();
		Encrypter enc2 = new Encrypter("hej", salt2);
		String key1 = enc1.getKey();
		String key2 = enc2.getKey();
		System.out.println("Salt1 : " + enc1.getSalt().toString());
		System.out.println("Salt2 : " + enc2.getSalt());
		System.out.println("key1 : " + key1);
		System.out.println("key2 : " + key2);
		System.out.println("pass1 : " + key1.substring(32));

	}

}
