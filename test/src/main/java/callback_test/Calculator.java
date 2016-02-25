package callback_test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	
	public String concatenate(String filepath) throws IOException {
		lineCallback<String> sumCallback = new lineCallback<String>() {
			
			@Override
			public String doSomethingWithLine(String line, String value) {
				// TODO Auto-generated method stub
				return value+line;
				// Integer.valueOf 는 line 에서 읽어온 값을 Integer 로 변환해서 더함
			}
		};
		return lineReadTemplate(filepath, sumCallback, "");
	}
	
	public Integer calcsum(String filepath) throws IOException {
		lineCallback<Integer> sumCallback = new lineCallback<Integer>() {
			
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				// TODO Auto-generated method stub
				return value+Integer.valueOf(line);
				// Integer.valueOf 는 line 에서 읽어온 값을 Integer 로 변환해서 더함
			}
		};
		return lineReadTemplate(filepath, sumCallback, 0);
	}

	public Integer calcMultiply(String filepath) throws IOException {
	lineCallback<Integer> multiCallback = new lineCallback<Integer>() {
			
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				// TODO Auto-generated method stub
				return value*Integer.valueOf(line);
				// Integer.valueOf 는 line 에서 읽어온 값을 Integer 로 변환해서 더함
			}
		};
		return lineReadTemplate(filepath, multiCallback, 1);
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

	public <T> T lineReadTemplate(String filepath, lineCallback<T> callback, T initvalue) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			T res = initvalue;
			String line = null;
			while ((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
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
