package callback_test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	public Integer calcsum(String filepath) throws IOException {
		BufferReaderCallback sumCallback = new BufferReaderCallback() {
			@Override
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				// TODO Auto-generated method stub
				Integer sum = 0;
				String line = null;
				while ((line = br.readLine()) != null) {
					sum += Integer.valueOf(line);
				}
				return sum;
			}
		};
		return fileReadTemplate(filepath,sumCallback);
	}
	
	public Integer calcMultiply(String filepath) throws IOException{
		BufferReaderCallback sumCallback = new BufferReaderCallback() {
			@Override
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				// TODO Auto-generated method stub
				Integer sum = 1;
				String line = null;
				while ((line = br.readLine()) != null) {
					sum *= Integer.valueOf(line);
				}
				return sum;
			}
		};
		return fileReadTemplate(filepath,sumCallback);
	}

	public Integer fileReadTemplate(String filepath, BufferReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			Integer result = callback.doSomethingWithReader(br);
			return result;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
