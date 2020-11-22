package cse12pa3student;

/* Class to implement a Maze solver */

public abstract class MazeSolver 
{
	
	public static Square solve( Maze maze, SearchWorklist wl )
	{
		// Initialize wl to be a new empty worklist	
		if ( wl instanceof StackWorklist ) 
		{
			wl = new StackWorklist();
		
		} else 
		{
			wl = new QueueWorklist();
		}
		
		// Add the start square to wl
		wl.add( maze.start );
		
		// Mark the start as visited
		maze.start.visit();
		
		// While wl is not empty
		while ( wl.isEmpty() == false ) 
		{
			
			// Let current = remove the first element from wl
			Square current = wl.remove();
			if( current == maze.finish ) // If current is the finish square
			{
				return current;	// Return the Square
			} 
			else 
			{
				int rowc = current.getRow();
				int colc = current.getCol();
			
				//For east neighbor
				if( checkBound( maze.contents, rowc, colc+1 ) )
				{
					Square currentEast = maze.contents[rowc][colc+1];
					if( availableNeighbor( maze.contents, currentEast ) )
					{
						// Mark the neighbor as visited
						currentEast.visit(); 
						// Set the previous of the neighbor to current
						currentEast.setPrevious( current ); 
						// Add the neighbor to the worklist
						wl.add( currentEast ); 
					}
				}
			
				//For south neighbor
				if( checkBound( maze.contents, rowc+1, colc ) ) 
				{
					Square currentSouth = maze.contents[rowc+1][colc];
					if( availableNeighbor( maze.contents, currentSouth ) ) 
					{
						currentSouth.visit();
						currentSouth.setPrevious( current );
						wl.add( currentSouth );
					}
				}
			
				//For west neighbor
				if( checkBound( maze.contents, rowc, colc-1 ) )
				{
					Square currentWest = maze.contents[rowc][colc-1];
					if( availableNeighbor( maze.contents, currentWest ) ) 
					{
						currentWest.visit();
						currentWest.setPrevious( current );
						wl.add( currentWest );
					}
				}
			
				//For north neighbor
				if( checkBound( maze.contents, rowc-1, colc ) )
				{
					Square currentNorth = maze.contents[rowc-1][colc];
					if( availableNeighbor(maze.contents, currentNorth))
					{
						currentNorth.visit();
						currentNorth.setPrevious(current);
						wl.add(currentNorth);
					}	
				}
			}
		}
		
		// If no path found, return null
		return null;
	}
	
	// Check if the neighbor is visited or is a wall
	static boolean availableNeighbor( Square[][] contents, Square s ) 
	{
		boolean neighborVisited = true;
		boolean neighborWall = true;
		if( s.isVisited() ) 
		{
			neighborVisited = false;	
		}
		
		if( s.getIsWall() ) 
		{
			neighborWall = false;
		}
		
		return neighborVisited && neighborWall;
	}
	
	// Check if the neighbor is in the maze
	static boolean checkBound( Square[][] contents, int rowOffset, int colOffset ) 
	{
		
		boolean rowInBound= false;
		boolean colInBound= false;
		
		if( ( rowOffset >= 0 ) && ( rowOffset <= contents.length-1 ) )
		{
			rowInBound = true;
		}
		
		if( ( colOffset >= 0 ) && ( colOffset <= contents[0].length-1 ) )
		{
			colInBound = true;
		}
		
		return rowInBound && colInBound;
	}
}
