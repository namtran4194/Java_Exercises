package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AddSubTest {
	@Test
	public void testAddPass() {
		// assertEquals(String message, long expected, long actual)
		assertEquals("error in add()", 3, Calculator.add(1, 2));
		assertEquals("error in add()", -3, Calculator.add(-1, -2));
		assertEquals("error in add()", 9, Calculator.add(9, 0));
	}

	@Test
	public void testAddFail() {
		// assertNotEquals(String message, long expected, long actual)
		assertNotEquals("error in add()", 0, Calculator.add(1, 2));
	}

	@Test
	public void testSubPass() {
		assertEquals("error in sub()", 1, Calculator.sub(2, 1));
		assertEquals("error in sub()", -1, Calculator.sub(-2, -1));
		assertEquals("error in sub()", 0, Calculator.sub(2, 2));
	}

	@Test
	public void testSubFail() {
		assertNotEquals("error in sub()", 0, Calculator.sub(2, 1));
	}

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(AddSubTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
