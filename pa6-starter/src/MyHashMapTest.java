import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

public class MyHashMapTest {
	
	private DefaultMap<String, String> testMap; // use this for basic tests
	private DefaultMap<String, String> mapWithCap; // use for testing proper rehashing
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	
	@Before
	public void setUp() {
		testMap = new MyHashMap<>();
		mapWithCap = new MyHashMap<>(4, MyHashMap.DEFAULT_LOAD_FACTOR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPut_nullKey() {
		testMap.put(null, TEST_VAL);
	}

	@Test
	public void testKeys_nonEmptyMap() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
	}
	
	@Test
	public void testKeys_nonEmptyMapWithLargeNumPut() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>();
		for(int i = 0; i < 20; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		Collections.sort(expectedKeys);
		assertEquals(expectedKeys, resultKeys);
	}
	
	@Test
	public void testKeys_nonEmptyMapWithLargeNumSet() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>();
		for(int i = 0; i < 20; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.set(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		Collections.sort(expectedKeys);
		assertEquals(resultKeys, resultKeys);
	}
	
	@Test
	public void testKeys_nonEmptyMapWithSet() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.set(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
	}
	
	@Test
	public void testReplace() {
		
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);	
		}
		// replace the first value
		String newValue = "CSE 12";
		testMap.replace( TEST_KEY + 0, newValue );
		assertEquals(newValue, testMap.get( "Test Key0" ) );
	}
	
	@Test
	public void testContainsKey () {
		
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
		}
		// Check if contains the key.
		boolean result =testMap.containsKey( "Test Key0" );
		assertEquals(true, result );
	}
	
	@Test
	public void testGet () {
		
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
		}
		// get the value at the "Test Key0"
		String value = testMap.get("Test Key0");
		assertEquals("Test Value0", value );
	}
	
	@Test
	public void testRemove() {
		
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
		}
		// test the boolean value
		boolean result = testMap.remove(TEST_KEY + 0);
		assertEquals(true, result );
		
		// test if the entry is removed
		String value = testMap.get("Test Key1");
		assertEquals("Test Value1", value );
		List<String> expectedKeys = new ArrayList<>();
		for(int i = 1; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.set(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		assertEquals(expectedKeys, resultKeys);
		
	}
	
	@Test
	public void testIsEmptyWithElements() {
		
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
		}
		// if the list is not empty
		boolean value = testMap.isEmpty();
		assertEquals( false, value );
	}
	
	@Test
	public void testIsEmptyWithoutElements() {
		
		//if the list is empty
		boolean value = testMap.isEmpty();
		assertEquals( true, value );
	}
	
	
}