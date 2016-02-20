package teststudy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitTest {
	static JUnitTest testObject;

	@Test
	public void test1() {
		assertThat(this, not(sameInstance(testObject)));
		testObject = this;

	}

	@Test
	public void test2() {
		assertThat(this, not(sameInstance(testObject)));
		testObject = this;

	}

	@Test
	public void test3() {
		assertThat(this, not(sameInstance(testObject)));
		testObject = this;

	}
}
