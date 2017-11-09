import java.io.IOException;
import java.io.InputStream;

public class VigenereInputStream extends InputStream {
	
	private InputStream input;
	private byte[] key;
	private int i;
	
	public VigenereInputStream(InputStream input, byte[] key) {
		
		this.key = key;
		this.input = input;
		i = 0;
		
	}
	
	public VigenereInputStream(InputStream input, byte[] key, int offset) {
		
		this(input, key);
		i = offset % key.length;
		
	}

	@Override
	public int read() throws IOException {
		
		int read = input.read();
		if (read == -1) {
			
			return -1;
			
		}
		int out = read - key[i];
		i++;
		if (i == key.length) {
			
			i -= key.length;
			
		}
		return out;
		
	}

}
