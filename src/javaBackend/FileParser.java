package javaBackend;

import java.io.File;

import javax.swing.JTextField;

public class FileParser {
	private File[] textFieldParser(JTextField tf) {
		String[] filesText = tf.getText().split(";");
		File[] files = new File[filesText.length];
		for(int i = 0; i < filesText.length; i++) {
			files[i] = new File(filesText[i]);
		}
		return files;
	}
}
