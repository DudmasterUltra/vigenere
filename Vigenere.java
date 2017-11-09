public class Vigenere {

	public static void encrypt(byte[] file, byte[] key) {
		
		int i = 0;
		for (int index = 0; index < file.length; index++) {

			file[index] += key[i];
			i++;
			if (i == key.length) {
				
				i -= key.length;
				
			}
			
		}
		
	}

	public static void decrypt(byte[] file, byte[] key) {

		int i = 0;
		for (int index = 0; index < file.length; index++) {

			file[index] -= key[i];
			i++;

			if (i == key.length) {
				
				i -= key.length;
				
			}
			
		}
		
	}
	
}
