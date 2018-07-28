import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private int numberOfSites;
	private int numOfTrials;
	private double trials[];
	private double mean;
	private double stddev;
	
	
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("grid size / number of trials must be greater than 0.");
		}
		numberOfSites = n*n;
		numOfTrials = trials;
		this.trials = new double[trials];
		
		for (int i = 0; i < numOfTrials; i++) {
			Percolation perc = new Percolation(n);
			while (!perc.percolates()) {
				int row = StdRandom.uniform(1, n+1);
				int col = StdRandom.uniform(1, n+1);
				perc.open(row,col);
			}
			double openSites = perc.numberOfOpenSites();
			double x = openSites / numberOfSites;
			this.trials[i] = x;
		}
	}
	
	public double mean() {
		mean = StdStats.mean(trials);
		return mean;
	}
	
	public double stddev() {
		stddev = StdStats.stddev(trials);
		return stddev;
	}
	
	public double confidenceLo() {
		double lo = mean - (1.96 * stddev) / (Math.sqrt(numOfTrials));
		return lo;
	}
	
	public double confidenceHi () {
		double hi = mean + (1.96 * stddev) / (Math.sqrt(numOfTrials));
		return hi;
	}
	
	public static void main(String[] args) {
		
	}
}
