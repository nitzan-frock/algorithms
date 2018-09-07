import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;

public class Board {
	private int dimension;
	private int[][] blocks;
	private int[][] goal;
	private int empty_row;
	private int empty_col;
	private int hamming;
	private int manhattan;
	
	// construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {			
		dimension = blocks.length;
		this.blocks = blocks;
		goal = new int[dimension][dimension];
		
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
				goal[i][j] = position++;
			}
		}
		calcHamming();
		calcManhattan();
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
				if (blocks[i][j] != goal[i][j]) {
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
		int n2 = dimension*dimension;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != goal[i][j]) {
					int goal_i = n2 - (n2 - blocks[i][j]) - 1;
					int current_i = xyTo1D(i, j);
					int rowsOOP = Math.abs(goal_i - current_i) % dimension; // rows out of place
					int colsOOP = Math.abs(current_i - goal_i - (rowsOOP - dimension)); // cols out of place
					manhattan += rowsOOP + colsOOP;
				}
			}
		}
	}
	
	private int xyTo1D(int y, int x) {
		int index = x + dimension * (y - 1);
		return index;
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
		
		int swap = temp[0][0];
		temp[0][0] = temp[0][1];
		temp[0][1] = swap;
		
		return new Board(temp);
	}
	
	// does this board equal y?
	public boolean equals(Object y) {
		Board check = (Board) y;
		if (y == this) return true;
		if (y == null || !(y instanceof Board) || check.dimension != dimension) return false;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (check.blocks[i][j] != blocks[i][j]) return false;
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
		
		public Neighbors() {
			neighbors = new ArrayList<Board>();
			
			for (int i = 0; i < 4; i++) {
				int[][] copy = copyBlocks();
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
		
		private int[][] copyBlocks(){
			int[][] copy = new int[dimension][dimension];
			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					copy[i][j] = blocks[i][j];
				}
			}
			return copy;
		}
		
		public Iterator<Board> iterator() {
			return new NeighborsIterator();
		}
		
		private class NeighborsIterator implements Iterator<Board>{
			private ArrayList<Board> copy;
			private int first = 0;
			
			public NeighborsIterator() {
				copy = new ArrayList<Board>(neighbors);
			}
			
			public boolean hasNext() {
				return !copy.isEmpty();
			}

			public Board next() {
				return copy.remove(first);
			}
		}
	}
	
	// string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder strBoard = new StringBuilder();
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
		int[][] blocks = {
				{3, 4, 0},
				{1, 2, 8},
				{5, 6, 7},
		};
		Board b = new Board(blocks);
		System.out.println(b.toString());
		System.out.println(b.manhattan());
		
		for(Board n : b.neighbors()) {
			System.out.println(n.toString());
		}
	}
}
