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
		
		int count = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (count == dimension*dimension) {
					goal[i][j] = 0;
				}
				goal[i][j] = count++;
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
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != goal[i][j]) {
					int n2 = dimension*dimension;
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
						// corner cases:
						// top right: [0][dimension-1]
						// top left: [0][0]
						// bottom left: [dimension-1][0]
						// bottom right: [dimension-1][dimension-1]
						
						// edge cases:
						// top: [0][0 < x < dimension -1]
						// left: [0 < x < dimension-1][0]
						// bottom: [dimension-1][0 < x < dimension-1]
						// right: [0 < x < dimension-1][dimension-1]
					}
				}
			}
		}
		
		public Iterator<Board> iterator() {
			return new NeighborsIterator();
		}
		
	}
	
	private class NeighborsIterator implements Iterator<Board>{
		public boolean hasNext() {
			return false;
		}

		public Board next() {
			return null;
		}
	}
	
	// string representation of this board (in the output format specified below)
	public String toString() {
		
	}
	
	// unit tests (not graded)
	public static void main(String[] args) {
		
	} 
}
