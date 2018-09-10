import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
	private Point[] goalCoordinates;
	private int[][] goal;
	private int[][] blocks;
	private int dimension;
	private int empty_row;
	private int empty_col;
	private int hamming;
	private int manhattan;
	
	// construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {			
		dimension = blocks.length;
		this.blocks = copyBlocks(blocks);
		goal = new int[dimension][dimension];
		goalCoordinates = new Point[dimension*dimension];
		
		int position = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] == 0) {
					empty_row = i;
					empty_col = j;
				}
				if (position == dimension*dimension) {
					goal[i][j] = 0;
					break;
				}
				goalCoordinates[position] = new Point(i, j);
				goal[i][j] = position++;
			}
		}
		calcHamming();
		calcManhattan();
	}
	
	private int[][] copyBlocks(int[][] blocks){
		int[][] copy = new int[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				copy[i][j] = blocks[i][j];
			}
		}
		return copy;
	}
	
	private class Point {
		private int row;
		private int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getCol() {
			return col;
		}
	}
	
	// board dimension n
	public int dimension() {
		return dimension;
	}
	
	// number of blocks out of place
	public int hamming() {
		return hamming;
	}
	
	private void calcHamming() {
		hamming = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != goal[i][j] && blocks[i][j] != 0) {
					hamming++;
				}
			}
		}
	}
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		return manhattan;
	}
	
	private void calcManhattan() {
		manhattan = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != goal[i][j] && blocks[i][j] != 0) {
					Point goal = goalCoordinates[blocks[i][j]];
					int targetRow = goal.getRow();
					int targetCol = goal.getCol();
					int rowsOOP = Math.abs(targetRow - i);
					int colsOOP = Math.abs(targetCol - j);
					manhattan += rowsOOP + colsOOP;
				}
			}
		}
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != goal[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		int[][] temp = new int[dimension][dimension];
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				temp[i][j] = blocks[i][j];
			}
		}
		
		int swap;
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (temp[i][j] != 0) {
					swap = temp[i][j];
				}
			}
		}
		int swap = temp[0][0];
		temp[0][0] = temp[0][1];
		temp[0][1] = swap;
		
		return new Board(temp);
	}
	
	// does this board equal y?
	public boolean equals(Object y) {
		if (y == this) return true;
		if (!(y instanceof Board) || ((Board) y).dimension != dimension) return false;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (((Board) y).blocks[i][j] != blocks[i][j]) return false;
			}
		}
		return true;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors(){
		return new Neighbors();
	}
	
	private class Neighbors implements Iterable<Board>{
		private ArrayList<Board> neighbors;
		private int[] rowMove = {1, 0, -1, 0};
		private int[] colMove = {0, 1, 0, -1}; 
		
		private Neighbors() {
			neighbors = new ArrayList<Board>();
			
			for (int i = 0; i < 4; i++) {
				int[][] copy = copyBlocks(blocks);
				int newRow = empty_row + rowMove[i];
				int newCol = empty_col + colMove[i];
				
				if (isInBounds(newRow, newCol)) {
					copy[newRow][newCol] = blocks[empty_row][empty_col];
					copy[empty_row][empty_col] = blocks[newRow][newCol];
					neighbors.add(new Board(copy));
				}
			}
		}
		
		private boolean isInBounds(int row, int col) {
			return (row >= 0 && 
					row < dimension && 
					col >= 0 &&
					col < dimension);
		}
		
		public Iterator<Board> iterator() {
			return new NeighborsIterator();
		}
		
		private class NeighborsIterator implements Iterator<Board>{
			private ArrayList<Board> copy;
			private int first = 0;
			
			private NeighborsIterator() {
				copy = new ArrayList<Board>(neighbors);
			}
			
			public boolean hasNext() {
				return !copy.isEmpty();
			}

			public Board next() {
				if (!hasNext()) throw new NoSuchElementException("No elements.");
				return copy.remove(first);
			}
		}
	}
	
	// string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder strBoard = new StringBuilder();
		strBoard.append(dimension + "\n");
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (dimension > 3 && blocks[i][j] < 10) {
					strBoard.append(" ");
				}
				strBoard.append(blocks[i][j]);
				strBoard.append(" ");
			}
			strBoard.append("\n");
		}
		return strBoard.toString();
	}
	
	// unit tests (not graded)
	public static void main(String[] args) {
	}
}
