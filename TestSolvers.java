package cse12pa3student;

/*
 * Write your JUnit tests here
 * Use the formatMaze() method to get nicer JUnit output
 */

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSolvers 
{


	/* Helper method to compare two mazes */
	public void checkMaze(SearchWorklist wl, Maze startMaze, String[] expected) {
		Square s = MazeSolver.solve(startMaze, wl);
		if(expected == null) { assertNull(s); }
		else {
			String actualStr = formatMaze(startMaze.showSolution());
			String expectedStr = formatMaze(expected);
			assertEquals(expectedStr, actualStr);
		}
	}
	

	/* Helper method to format String[] output as String */
	public String formatMaze(String[] arr) {
		String result = "";
		for (String s: arr)
			result += "\n"+s;
		return (result+"\n");
	}

	
	
	/* Worklist Tests */
	
	@Test
	public void testStackWorklist() 
	{
		Square s1 = new Square(0,0, false);
		Square s2 = new Square(1,0, false);
		Square s3 = new Square(1,1, false);
		SearchWorklist swl = new StackWorklist();
		
		swl.add(s1);
		swl.add(s2);
		assertEquals(false, swl.isEmpty());
		
		swl.remove(); 
		swl.remove();
		assertEquals(true, swl.isEmpty());
		
		//Ensure stack behavior
		swl.add(s1);
		swl.add(s2);
		swl.add(s3);
		assertEquals(s3, swl.remove());
	}
	

	@Test
	public void testQueueWorklist() {
		Square s1 = new Square(0,0, false);
		Square s2 = new Square(1,0, false);
		Square s3 = new Square(1,1, false);
		SearchWorklist qwl = new QueueWorklist();
		
		qwl.add(s1);
		qwl.add(s2);
		assertEquals(false, qwl.isEmpty());
		
		qwl.remove(); 
		qwl.remove();
		assertEquals(true, qwl.isEmpty());
		
		//Ensure queue behavior
		qwl.add(s1);
		qwl.add(s2);
		qwl.add(s3);
		assertEquals(s1, qwl.remove());
	}	
	
	
	@Test
	public void testClassExample() 
	{
		Maze m = new Maze(new String[]{
				"#_#_",
				"____",
				"_##S",
				"F___"
			});
		String[] stackExpected = {
				
				"#_#_",
				"****",
				"*##S",
				"F___"
			};
		checkMaze(new StackWorklist(), m, stackExpected);
	}
	
	@Test
	public void testClassExampleQueue() 
	{
		Maze m = new Maze(new String[]{
				"#_#_",
				"____",
				"_##S",
				"F___"
			});
		String[] queueExpected = {
				"#_#_",
				"____",
				"_##S",
				"F***"
			};
		checkMaze(new QueueWorklist(), m, queueExpected);
	}
	
	@Test
	public void testFailingOrder() 
	{
		String[] expected = {
				"#_#_",
				"****",
				"*##S",
				"F___"
			};
		String[] actualAndWrong= {
				"#_#_",
				"****",
				"*##S",
				"F___"
			};
		assertEquals(formatMaze(expected), formatMaze(actualAndWrong));
	}

	@Test
	public void testClassNoWallStack() 
	{
	Maze m = new Maze(new String[]{
			"___S",
			"____",
			"____",
			"F___"
		});
	String[] stackExpected = {
			"***S",
			"*___",
			"*___",
			"F___"
		};
	checkMaze(new StackWorklist(), m, stackExpected);
	}
	
	@Test
	public void testClassNoWallQueue()
	{
	Maze m = new Maze(new String[]{
			"___S",
			"____",
			"____",
			"F___"
		});
	String[] queueExpected = {
			"___S",
			"___*",
			"___*",
			"F***"
		};
	checkMaze(new QueueWorklist(), m, queueExpected);
	}
	
	@Test
	public void testClassALotOfWallQueue()
	{
	Maze m = new Maze(new String[]{
			"S__#",
			"_#_#",
			"___#",
			"F##_"
		});
	String[] queueExpected = {
			"S__#",
			"*#_#",
			"*__#",
			"F##_"
		};
	checkMaze(new QueueWorklist(), m, queueExpected);
	}
	
	@Test
	public void testClassALotOfWallQStack()
	{
	Maze m = new Maze(new String[]{
			"S__#",
			"##_#",
			"___#",
			"F##_"
		});
	String[] stackExpected = {
			"S**#",
			"##*#",
			"***#",
			"F##_"
		};
	checkMaze(new StackWorklist(), m, stackExpected);
	}
	
	@Test
	public void testClassFinishStartNextToOthersStack() 
	{
	Maze m = new Maze(new String[]{
			"___#",
			"##_#",
			"S__#",
			"F##_"
		});
	String[] stackExpected = {
			"___#",
			"##_#",
			"S__#",
			"F##_"
		};
	checkMaze(new StackWorklist(), m, stackExpected);
	}
	
	@Test
	public void testClassFinishStartNextToOthersQueue() {
	Maze m = new Maze(new String[]{
			"___#",
			"##_#",
			"S__#",
			"F##_"
		});
	String[] queueExpected = {
			"___#",
			"##_#",
			"S__#",
			"F##_"
		};
	checkMaze(new QueueWorklist(), m, queueExpected);
	}

	@Test
	public void testClassCornersQueue() 
	{
	Maze m = new Maze(new String[]{
			"S___",
			"_#__",
			"___#",
			"#__F"
		});
	String[] queueExpected = {
			"S**_",
			"_#*_",
			"__*#",
			"#_*F"
		};
	checkMaze(new QueueWorklist(), m, queueExpected);
	}
	
	@Test
	public void testClassCornersStack()
	{
	Maze m = new Maze(new String[]{
			"S___",
			"_#__",
			"___#",
			"#__F"
		});
	String[] stackExpected = {
			"S___",
			"*#__",
			"**_#",
			"#**F"
		};
	checkMaze(new StackWorklist(), m, stackExpected);
	}
	
}

