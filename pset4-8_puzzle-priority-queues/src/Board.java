import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
	private int[][] goalCoordinates;
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
		goalCoordinates = new int[dimension*dimension+1][2];
		
		int position = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] == 0) {
					empty_row = i;
					empty_col = j;
				}
				goalCoordinates[position++] = new int[]{i, j};
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
		int position = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != position++ && blocks[i][j] != 0) {
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
		int position = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != position++ && blocks[i][j] != 0) {
					int[] goal = goalCoordinates[blocks[i][j]];
					int targetRow = goal[0];
					int targetCol = goal[1];
					int rowsOOP = Math.abs(targetRow - i);
					int colsOOP = Math.abs(targetCol - j);
					manhattan += rowsOOP + colsOOP;
				}
			}
		}
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		int position = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != position++ && blocks[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	private int toCol (int index) {
		return index % dimension;
	}
	
	private int toRow (int index) {
		return index / dimension;
	}
	
	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		int[][] temp = copyBlocks(blocks);
		
		int index = 0;
		int swap = 1;
		
		while (temp[toRow(index)][toCol(index)] == 0 || temp[toRow(swap)][toCol(swap)] == 0) {
			index++;
			swap++;
		}
		
		Board twin = new Board(swap(temp, toRow(index), toCol(index), toRow(swap), toCol(swap)));
		
		return twin;
	}
	
	private int[][] swap(int[][] blocks, int row, int col, int rowSwap, int colSwap) {
		int swap = blocks[row][col];
		blocks[row][col] = blocks[rowSwap][colSwap];
		blocks[rowSwap][colSwap] = swap;
		return blocks;
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
				strBoard.append(blocks[i][j] + " ");
			}
			strBoard.append("\n");
		}
		return strBoard.toString();
	}
	
	// unit tests (not graded)
	public static void main(String[] args) {
	}
}
