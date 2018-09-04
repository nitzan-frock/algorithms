import edu.princeton.cs.algs4.MinPQ;

public class Board {
	private int dimension;
	private int[][] blocks;
	private int[][] goal;
	
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
	}
	
	// board dimension n
	public int dimension() {
		return dimension;
	}
	
	// number of blocks out of place
	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != goal[i][j]) {
					hamming++;
				}
			}
		}
		return hamming;
	}
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int manhattan = 0;
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
		return manhattan;
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
		
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors(){
		
	}
	
	// string representation of this board (in the output format specified below)
	public String toString() {
		
	}
	
	// unit tests (not graded)
	public static void main(String[] args) {
		
	} 
}
