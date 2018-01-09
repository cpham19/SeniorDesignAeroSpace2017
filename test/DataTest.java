package test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import junit.framework.TestCase;
import test.Data;
import app.DataPacket;

// TestCase is used for overriding setUp, tearDown, and it extends to Assert (for assert methods)
public class DataTest extends TestCase
{
	private ArrayList<DataPacket> list1;
	private ArrayList<DataPacket> list2;

	public DataTest(String testName)
	{
		super(testName);
	}

	// Initialize values
	@Before
	protected void setUp() throws Exception
	{
		super.setUp();
		list1 = Data.createList();
		list2 = Data.createList();

		// Copy list1 to list2
		for (int i = 0; i < list1.size(); i++) {
			list2.add(i, list2.get(i));
		}
	}

	// Clean up values after each test
	@After
	protected void tearDown() throws Exception
	{
		super.tearDown();
		list1 = new ArrayList<>();
		list2 = new ArrayList<>();
	}

	// Test if the first list is equal to second list
	public void testEquals()
	{
		System.out.println("First Test");
		System.out.println("Acceleration in first object in first list: " + list1.get(0).getxAccel());
		System.out.println("Acceleration in first object in second list: " + list2.get(0).getxAccel() + "\n");

		assertEquals(list1.get(0).getxAccel(), list2.get(0).getxAccel());
	}

	// Test if the first list is equal to second list with modified first list
	public void testNotEquals()
	{
		// Create a DataPacket from the first row, assign the acceleration to a random number, and replace the first object with the new object in the list
		DataPacket dp = list1.get(0);
		dp.setxAccel(5000000.5000);
		list1.add(0, dp);

		System.out.println("Second Test (Modified List1)");
		System.out.println("Acceleration in first object in first list: " + list1.get(0).getxAccel());
		System.out.println("Acceleration in first object in second list: " + list2.get(0).getxAccel() + "\n");


		assertEquals(list1.get(0).getxAccel(), list2.get(0).getxAccel());
	}

	public void testTrue()
	{
		DataPacket dp = list1.get(0);
//		dp.setL0(true);
		list1.add(0, dp);

		System.out.println("Third Test");
		System.out.println("Boolean in first object in first list: " + list1.get(0).isL0());
		System.out.println("Boolean in first object in second list: " + list2.get(0).isL0() + "\n");

//		assertTrue("Is L0 true in the first list? ",list1.get(0).isL0());
//		assertTrue("Is L0 true in the second list? ", list2.get(0).isL0());
	}
}