import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ex0dusCLI4J.CLI;
import ex0dusCLI4J.CLIResult;

public class Run {

	public static void main(String[] args) {
		
		CLI.Switch in = new CLI.Switch("in");
		in.setDescription("Input file");
		in.setIsRequired(true);
		in.setHasValue(true);
		
		CLI.Switch out = new CLI.Switch("out");
		out.setDescription("Output file");
		out.setIsRequired(true);
		out.setHasValue(true);
		
		CLI.Switch keySwitch = new CLI.Switch("key");
		keySwitch.setDescription("Operation key");
		keySwitch.setIsRequired(true);
		keySwitch.setHasValue(true);
		
		CLI.Switch mode = new CLI.Switch("mode");
		mode.setDescription("Encrypt / decrypt");
		mode.setHasValue(true);
		mode.setIsRequired(true);
		
		CLI cli = new CLI(new CLI.Switch[] { in, out, mode, keySwitch });
		CLIResult parse = cli.parse(args);
		
		if (parse.isEmpty()) {
			
			cli.Splash("vigenere", "isaac / ex0dus");
			return;
			
		}
		
		String key;
		if (parse.parsedContains(keySwitch)) {
			
			key = parse.getParsed(keySwitch).getValue();
			
		} else {
			
			System.out.println("FAILURE = -key switch missing");
			return;
			
		}
		
		boolean encrypt;
		if (parse.parsedContains(mode)) {
			
			encrypt = parse.getParsed(mode).getValue().toLowerCase().equals("encrypt");
			
		} else {
			
			System.out.println("FAILURE = -mode switch missing");
			return;
			
		}
		
		File input;
		if (parse.parsedContains(in)) {
			
			input = new File(parse.getParsed(in).getValue());
			if (!input.exists()) {
				
				System.out.println("FAILURE = Input file missing");
				return;
				
			}
			
		} else {
			
			System.out.println("FAILURE = -in switch missing");
			return;
			
		}
		
		File output;
		if (parse.parsedContains(out)) {
			
			output = new File(parse.getParsed(out).getValue());
			if (output.exists()) {
				
				System.out.println("FAILURE = Output file already exists");
				return;
				
			}
			
		} else {
			
			System.out.println("FAILURE = -out switch missing");
			return;
			
		}
		
		try {
			
			if (encrypt) {
				
				OutputStream os = new VigenereOutputStream(new FileOutputStream(output), key.getBytes());
				InputStream is = new FileInputStream(input);
				
				int data;
				while ((data = is.read()) != -1) {
					
					os.write(data);
					
				}
				
				os.close();
				is.close();
				
			} else {
				
				OutputStream os = new FileOutputStream(output);
				InputStream is = new VigenereInputStream(new FileInputStream(input), key.getBytes());
				
				int data;
				int tmp;
				while ((tmp = is.read()) != -1) {
					
					data = tmp;
					os.write(data);
					
				}
				
				os.close();
				is.close();
				
			}
			
		} catch (IOException e) {
			
			System.out.println("FAILURE = " + e.getClass().getSimpleName());
			System.out.println(e.getMessage());
			return;
			
		}
		
	}

}
