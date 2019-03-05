import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Class to model a percolation system.
 * 
 * @author gabrielrodriguezj
 * @since 11/2018
 * @version 1.0
 *
 */
public class Percolation {

	/**
	 * Grid of size n*n; contains sites that can be blocked(false) or open(true).
	 */
	private boolean[][] grid;

	/**
	 * Implementation of the Weight Quick Union Algorithm for dynamic connectivity.
	 */
	private final WeightedQuickUnionUF wqu;

	/**
	 * Variable that save the dimension of the grid (a side)
	 */
	private final int size;

	/**
	 * Variable that save the number of opened sites.
	 */
	private int numberOfOpenSites;

	/**
	 * Variable that save position where start the percolation system.
	 */
	private final int start;

	/**
	 * Variable that save position where end the percolation system
	 */
	private final int end;

	/**
	 * Constructor by default, create n-by-n grid, with all sites blocked.
	 * 
	 * @param n Number of elements per columns and rows.
	 * @throws IllegalArgumentException if {@code n < 0}
	 */
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("The size of columns and rows must be greater than 0");
		}

		grid = new boolean[n][n];
		wqu = new WeightedQuickUnionUF(n * n + 2);
		size = n;
		numberOfOpenSites = 0;
		start = 0; // The first position at the grid
		end = n * n + 1; // The last position at the grid
		
		//Make the initial conections
		for (int i = 1; i <= size; i++) {
			// Conect the elements in the first row in the grid with the "start" element in
			// the grid
			wqu.union(start, i);
			// Conect the elements in the last row in the grid with the "end" element in the
			// grid
			wqu.union(end, size * (size - 1) + i);
		}
	}

	/**
	 * Open site (row, col) if it is not open already; the row and column parameters
	 * must be greater than 0.
	 * 
	 * @param row Number of col where is the site to open.
	 * @param col Number of column where is the site to open.
	 * @throws IllegalArgumentException if both {@code 0 <= row} or {@code 0 <= col}
	 */
	public void open(int row, int col) {
		validateIndex(row, col);

		if (!isOpen(row, col)) {
			grid[row - 1][col - 1] = true;
			numberOfOpenSites++;

			// connect with the 4 neighbor in a recursive way
			int p = xyTo1D(row, col);

			// Check the up site neighbor
			if (row > 1 && isOpen(row - 1, col)) {
				int q = xyTo1D(row - 1, col);
				wqu.union(p, q);
			}

			// Check the left site neighbor
			if (col > 1 && isOpen(row, col - 1)) {
				int q = xyTo1D(row, col - 1);
				wqu.union(p, q);
			}

			// Check the right site neighbor
			if (col < size && isOpen(row, col + 1)) {
				int q = xyTo1D(row, col + 1);
				wqu.union(p, q);
			}

			// Check the down site neighbor
			if (row < size && isOpen(row + 1, col)) {
				int q = xyTo1D(row + 1, col);
				wqu.union(p, q);
			}
		}
	}

	/**
	 * Check if the site (row, col) is open; the row and column parameters must be
	 * greater than 0.
	 * 
	 * @param row Number of row where is the site to open.
	 * @param col Number of column where is the site to open.
	 * @return <tt>True</tt> if the site is open, <tt>false</tt> if its close.
	 * @throws IllegalArgumentException if both {@code 0 <= row} or {@code 0 <= col}
	 */
	public boolean isOpen(int row, int col) {
		validateIndex(row, col);
		return grid[row - 1][col - 1];
	}

	/**
	 * Check if the site (row, col) is full; the row and column parameters must be
	 * greater than 0.
	 * 
	 * @param row Number of row where is the site to open.
	 * @param col Number of column where is the site to open.
	 * @return <tt>True</tt> if the site is full, <tt>false</tt> in other case.
	 * @throws IllegalArgumentException if both {@code 0 <= row} or {@code 0 <= col}
	 */
	public boolean isFull(int row, int col) {
		validateIndex(row, col);

		if (isOpen(row, col)) {
			int p = xyTo1D(row, col);
			return wqu.connected(start, p);
		}
		return false;
	}

	/**
	 * Calculate the number of open sites.
	 * 
	 * @return Number of open sites.
	 */
	public int numberOfOpenSites() {
		return numberOfOpenSites;
	}

	/**
	 * Check if the system percolate.
	 * 
	 * @return <tt>True</tt> if the system percolates, <tt>false</tt> in other case.
	 */
	public boolean percolates() {
		if (numberOfOpenSites == 0) {
			return false;
		}
		return wqu.connected(start, end);
	}

	/**
	 * Convert an matrix element (row, column) in an array element.
	 * 
	 * @param row Number of row.
	 * @param col Number of column.
	 * @return Array element.
	 */
	private int xyTo1D(int row, int col) {
		int site = (row - 1) * (size) + col;
		return site;
	}

	/**
	 * Method that evaluate that row or column be correct.
	 * 
	 * @param row Number of row.
	 * @param col Number of column.
	 */
	private void validateIndex(int row, int col) {
		if (row < 1 || col < 1) {
			throw new IllegalArgumentException("The row or column index must be greater than 0");
		}
		if (row > size || col > size) {
			throw new IllegalArgumentException("The row or column index must be smallest than size of grid");
		}
	}
}
