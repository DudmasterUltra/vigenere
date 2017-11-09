import java.io.IOException;
import java.io.OutputStream;

public class VigenereOutputStream extends OutputStream {
	
	private OutputStream output;
	private byte[] key;
	private int i;
	
	public VigenereOutputStream(OutputStream output, byte[] key) {
		
		this.key = key;
		this.output = output;
		i = 0;
		
	}
	
	public VigenereOutputStream(OutputStream output, byte[] key, int offset) {
		
		this(output, key);
		i = offset % key.length;
		
	}

	@Override
	public void write(int data) throws IOException {
		
		int write = data + key[i];
		i++;
		if (i == key.length) {
			
			i -= key.length;
			
		}
		output.write(write);
		
	}
	
	@Override
	public void close() throws IOException {
		
		output.close();
		
	}

}
