import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Class to perform a series of computational experiment (Monte Carlo
 * Simulation).
 * 
 * @author gabrielrodriguezj
 * @since 11/2018
 * @version 1.0
 *
 */
public class PercolationStats {
	
	/**
	 * Constant for the confidence interval at 95%
	 */
	private static final double CONFIDENCE_95 = 1.96;

	/**
	 * Array for save the number of cells that allow a percolation in the grid.
	 */
	private final double[] percolations;
	
	/**
	 * Sample mean of percolation threshold.
	 */
	private final double mean;
	
	/**
	 * Sample standard deviation of percolation threshold.
	 */
	private final double stddev;

	/**
	 * perform trials independent experiments on an n-by-n grid.
	 * 
	 * @param n      Size of the grid for the percolation experiment.
	 * @param trials Number of repetitions of the experiment.
	 */
	public PercolationStats(int n, int trials) {
		if (n <= 0) {
			throw new IllegalArgumentException("The size of columns and rows must be greater than 0");
		}

		if (trials <= 0) {
			throw new IllegalArgumentException("The number of experiments must be greater than 0");
		}

		percolations = new double[trials];

		for (int i = 0; i < trials; i++) {

			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;

				p.open(row, col);
			}
			percolations[i] = (double) p.numberOfOpenSites() / (n * n);
		}
		mean = StdStats.mean(percolations);
		stddev = Math.sqrt(StdStats.stddev(percolations));
	}

	/**
	 * Method that return the sample mean of percolation threshold.
	 * 
	 * @return Mean of percolation threshold.
	 */
	public double mean() {
		return mean;
	}

	/**
	 * Method that return the sample standard deviation of percolation threshold.
	 * 
	 * @return Standard deviation of the samples.
	 */
	public double stddev() {
		return stddev;
	}

	/**
	 * Method that return the low endpoint of 95% confidence interval.
	 * 
	 * @return Low endpoint of 95% confidence interval.
	 */
	public double confidenceLo() {
		return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(percolations.length));
	}

	/**
	 * Method that return the high endpoint of 95% confidence interval.
	 * 
	 * @return High endpoint of 95% confidence interval.
	 */
	public double confidenceHi() {
		return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(percolations.length));
	}

	/**
	 * Main method for the test client.
	 * 
	 * @param args Arguments for run the experiments: number of experiments and size
	 *             of the grid.
	 */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		PercolationStats ps = new PercolationStats(n, t);
		String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
		StdOut.println("mean                    = " + ps.mean());
		StdOut.println("stddev                  = " + ps.stddev());
		StdOut.println("95% confidence interval = " + confidence);
	}
}
