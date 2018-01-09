package test;

import test.BasicMath;

import org.junit.After;
import org.junit.Before;

import junit.framework.TestCase;

// TestCase is used for overriding setUp, tearDown, and it extends to Assert (for assert methods)
public class MathTest extends TestCase
{
	private int value1;
	private int value2;

	public MathTest(String testName)
	{
		super(testName);
	}

	// Initialize values
	@Before
	protected void setUp() throws Exception
	{
		super.setUp();
		value1 = 3;
		value2 = 5;
	}

	// Clean up values after each test
	@After
	protected void tearDown() throws Exception
	{
		super.tearDown();
		value1 = 0;
		value2 = 0;
	}

	// Test if the total is equal to the sum of value1 (3) and value2 (5)
	public void testAdd()
	{
		int total = 8;
		int sum = BasicMath.add(value1, value2);
		assertEquals(sum, total);
		assertTrue(sum == total);
	}

	// Test if the total is not equal to the sum of value1 (3) and value2 (5)
	public void testFailedAdd()
	{
		int total = 9;
		int sum = BasicMath.add(value1, value2);
		assertNotSame(sum, total);
	}

	// Test if the total is equal to the subtraction of value1 (3) and value2 (5)
	public void testSub()
	{
		int total = -2;
		int sub = BasicMath.sub(value1, value2);
		assertEquals(sub, total);
	}

	// Test if the product is equal to product of value1 (3) and value2 (5)
	public void testMulti()
	{
		int product = 15;
		int mult = BasicMath.multi(value1, value2);
		assertEquals(product, mult);
	}

	// Testing for HelloWorld
	public void testHelloWorld()
	{
		String[] array = {"Hell World", "Hello World", "Sea World", "Universal Studios"};
		String randomWord = array[(int) (Math.random() * array.length)];

		assertEquals("Hello World", randomWord);
	}
}