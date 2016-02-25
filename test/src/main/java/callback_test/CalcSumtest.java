package callback_test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

public class CalcSumtest {
	Calculator calc;
	String numFilepath;
	
	@Before
	public void setUp(){
		this.calc = new Calculator();
		this.numFilepath = getClass().getResource("number.txt").getPath();
	}
	
	@Test
	public void sumOfNumbers() throws IOException {
		assertThat(calc.calcsum(this.numFilepath), is(10));
	}
	
	@Test
	public void multiplyOfNumbers() throws IOException {
		assertThat(calc.calcMultiply(this.numFilepath), is(24));
	}
	
	@Test
	public void concatenateStrings() throws IOException {
		assertThat(calc.concatenate(this.numFilepath), is("1234"));
	}

}
