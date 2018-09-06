import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;

public class Board {
	private int dimension;
	private int[][] blocks;
	private int[][] goal;
	private int hamming;
	private int manhattan;
	
	// construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {			
		dimension = blocks.length;
		this.blocks = blocks;
		
		int position = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (position == dimension*dimension) {
					goal[i][j] = 0;
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
		return false;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors(){
		return new Neighbors(blocks);
	}
	
	private class Neighbors implements Iterable<Board>{
		private ArrayList<Board> neighbors;
		
		private Neighbors(int[][] blocks) {
			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					if (blocks[i][j] == 0) {
						initializeNeighbors(blocks, i, j);
					}
				}
			}
		}
		
		private void initializeNeighbors(int[][] blocks, int row, int col) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (!(i == 0 && j == 0)) {
						int[][] copy = copyBlocks(blocks);
						
					}
				}
			}
			if (isTopEdge(row)){
				if(isLeftEdge(col)) {
					// top left: [0][0]
					
				}else if(isRightEdge(col)) {
					// top right: [0][dimension-1]
				}
				
			}else if (isBottomEdge(row)) {
				if(isLeftEdge(col)) {
					// bottom left: [dimension-1][0]
				}else if(isRightEdge(col)) {
					// bottom right: [dimension-1][dimension-1]
				}
			} else if (isRightEdge(col)) {
				
			} else if (isLeftEdge(col)) {
				
			}
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
		
		private boolean isTopEdge(int row) {
			return row == 0;
		}
		
		private boolean isBottomEdge(int row) {
			return row == dimension-1;
		}
		
		private boolean isLeftEdge(int col) {
			return col == 0;
		}
		
		private boolean isRightEdge(int col) {
			return col == dimension-1;
		}
		
		public Iterator<Board> iterator() {
			return new NeighborsIterator();
		}
		
		private class NeighborsIterator implements Iterator<Board>{
			public boolean hasNext() {
				return false;
			}

			public Board next() {
				return null;
			}
		}
	}
	
	// string representation of this board (in the output format specified below)
	public String toString() {
		
	}
	
	// unit tests (not graded)
	public static void main(String[] args) {
		
	} 
}
