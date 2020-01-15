package fr.ensimag.deca.syntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class oracle{
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec("/usr/bin/sh ./src/test/script/launchers/validor_synt");
		StringBuilder output = new StringBuilder();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}

		int exitVal = process.waitFor();
		if (exitVal == 0) {
			System.out.println(output);
			System.exit(0);
		} else {
			//abnormal...
		}
	}
}