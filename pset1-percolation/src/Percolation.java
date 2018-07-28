import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int gridSize;
	private int numberOfOpenSites;
	private int grid[];
	private WeightedQuickUnionUF grid_uf;
	
	// create n-by-n grid, with all sites blocked.
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("grid size must be greater than 0.");
		}
		gridSize = n;
		grid = new int[gridSize*gridSize+2];
		grid_uf = new WeightedQuickUnionUF(gridSize*gridSize+2);
		
		for (int i = 0; i < n*n; i++) {
			grid[i] = 0;
		}
	}
	
	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		if (isValidxy(row, col) && !isOpen(row, col)) {
			grid[xyTo1D(row, col)] = 1;
			numberOfOpenSites++;
			connectToAdjacentSites(row, col);
			int top = 1;
			if (row == top) {
				grid_uf.union(0, xyTo1D(row, col));
			} else if (row == gridSize) {
				grid_uf.union(gridSize*gridSize+1, xyTo1D(row, col));
			}
		}
	}
	
	public int numberOfOpenSites() {
		return numberOfOpenSites;
	}
	
	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (isValidxy(row, col)) {
			if (grid[xyTo1D(row, col)] == 1) {
				return true;
			}
		}
		return false;		
	}
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) {		
		if (isValidxy(row, col)) {
			if (grid_uf.connected(0, xyTo1D(row,col))){
				return true;
			}
		}
		return false;
	}
	
	public boolean percolates() {
		return grid_uf.connected(0, gridSize*gridSize+1);
	}
	
	private void connectToAdjacentSites(int row, int col) {
		int p = xyTo1D(row, col);
		
		// check for above tile
		if (row-1 >= 1 && isOpen(row-1, col)) {
			int q = xyTo1D(row-1, col);
			grid_uf.union(p, q);
		} 
		
		// check for left tile
		if (col-1 >= 1 && isOpen(row, col-1)) {
			int q = xyTo1D(row, col-1);
			grid_uf.union(p, q);
		}
		
		// check for down tile
		if (row+1 <= gridSize && isOpen(row+1, col)) {
			int q = xyTo1D(row+1, col);
			grid_uf.union(p, q);
		}
		
		// check for right tile
		if (col+1 <= gridSize && isOpen(row, col+1)) {
			int q = xyTo1D(row, col+1);
			grid_uf.union(p, q);
		}
	}
	
	private boolean isValidxy(int y, int x) {
		if (x < 1 || x > gridSize) {
				throw new IllegalArgumentException("col index i out of bounds");
		} else if (y < 1 || y > gridSize){
				throw new IllegalArgumentException("row index i out of bounds");
			}
		return true;
	}
	
	private int xyTo1D(int y, int x) {
		int index = x + gridSize * (y - 1);
		return index;
	}
	
	public static void main(String[] args) {

	}
}
