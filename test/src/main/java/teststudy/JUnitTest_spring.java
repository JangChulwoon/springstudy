package teststudy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/xml/set-xml/junit.xml")
public class JUnitTest_spring {
	@Autowired
	ApplicationContext context;

	static Set<JUnitTest_spring> testObject = new HashSet<JUnitTest_spring>();
	static ApplicationContext contextObject = null;

	@Test
	public void test1() {
		assertThat(testObject, not(hasItem(this)));
		testObject.add(this);
		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}

	@Test
	public void test2() {
		assertThat(testObject, not(hasItem(this)));
		testObject.add(this);
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}

	@Test
	public void test3() {
		assertThat(testObject, not(hasItems(this)));
		testObject.add(this);
		//assertThat(contextObject,either(is(nullValue())).or(is(this.context)));
	}
}
