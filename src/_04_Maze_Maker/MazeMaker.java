package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		int x = randGen.nextInt(w);
		int y = randGen.nextInt(h);

		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(maze.cells[x][y]);
		maze.cells[0][randGen.nextInt(maze.cells[0].length)].setWestWall(false);
		maze.cells[maze.cells.length-1][randGen.nextInt(maze.cells[0].length)].setEastWall(false);
		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. Get an ArrayList of unvisited neighbors using the current cell and the
		// method below
		ArrayList<Cell> neighbors = getUnvisitedNeighbors(currentCell);
		// C. if has unvisited neighbors,
		if (neighbors.size() > 0) {
			// C1. select one at random.
			int n = randGen.nextInt(neighbors.size());
			// C2. push it to the stack
			uncheckedCells.push(neighbors.get(n));
			// C3. remove the wall between the two cells
			removeWalls(currentCell, neighbors.get(n));
			// C4. make the new cell the current cell and mark it as visited
			currentCell = neighbors.get(n);
			currentCell.setBeenVisited(true);
			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}

		// D. if all neighbors are visited
		else {
			// D1. if the stack is not empty
			if (!uncheckedCells.isEmpty()) {
				// D1a. pop a cell from the stack
				Cell cell = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell = cell;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getY() == c2.getY()) {
			if (c1.getX() > c2.getX()) {
				c1.setWestWall(false);
				c2.setEastWall(false);
			} else if (c2.getX() > c1.getX()) {
				c1.setEastWall(false);
				c2.setWestWall(false);
			}
		} else if (c1.getX() == c2.getX()) {
			if (c1.getY() > c2.getY()) {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			} else if (c2.getY() > c1.getY()) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			}
		}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvis = new ArrayList<Cell>();
		int x = c.getX();
		int y = c.getY();
		if (x > 0 && !maze.getCell(x - 1, y).hasBeenVisited()) {
			unvis.add(maze.getCell(x - 1, y));
		}
		if (y > 0 && !maze.getCell(x, y - 1).hasBeenVisited()) {
			unvis.add(maze.getCell(x, y - 1));
		}
		if (x < width - 1 && !maze.getCell(x + 1, y).hasBeenVisited()) {
			unvis.add(maze.getCell(x + 1, y));
		}
		if (y < width - 1 && !maze.getCell(x, y + 1).hasBeenVisited()) {
			unvis.add(maze.getCell(x, y + 1));
		}
		return unvis;
	}
}
