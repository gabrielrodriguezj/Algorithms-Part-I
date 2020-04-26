import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Unmutable class for representing the behaviour of a square board of the puzzle game; It
 * can contains whatever amount (n^2) of tiles;
 * 
 * 
 * @author gabrielrodriguezj
 *
 */
public class Board {

	/**
	 * Size of a side of the square board
	 */
	private final int dimension;

	/**
	 * Tiles of the puzzle
	 */
	private final int[][] blocks;
	
	/**
	 * Save the hamming distance
	 */
	private int hamming;
	
	/**
	 * Save the manhattan distance
	 */
	private int manhattan;

	/**
	 * Twin board, is created in the constructor and saved,this with the objective
	 * to make unmutable the Board class and every time return the same twin board.
	 */
	private Board twin = null;

	/**
	 * Construct a board from an n-by-n array of blocks, where blocks[i][j] = block
	 * in row i, column
	 * 
	 * @param blocks Matrix with the value of every tile
	 */
	public Board(int[][] blocks) {
		this.dimension = blocks.length;
		this.blocks = new int[this.dimension][this.dimension];

		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
		
		// calculate Hamming and Manhattan distance
		hamming = calculateHammingDistance();
		manhattan = calculateManhattanDistance();
	}

	public int dimension() {
		return this.dimension;
	}

	/**
	 * Method that returns the hamming distance of the board
	 * 
	 * @return number of blocks out of place
	 */
	public int hamming() {
		return hamming;
	}
	
	/**
	 * Method for calculate the number of blocks out of place (in the wrong position)
	 * 
	 * @return number of blocks out of place
	 */
	private int calculateHammingDistance() {
		int outOfPlace = 0;
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (this.blocks[i][j] != toGoal(i, j) && this.blocks[i][j] != 0) {
					outOfPlace++;
				}
			}
		}

		return outOfPlace;
	}

	/**
	 * Method that returns the manhattan distance of the board
	 * 
	 * @return number of blocks out of place
	 */
	public int manhattan() {
		return manhattan;
	}
	
	/**
	 * Method for calculate the sum of Manhattan distances between blocks and goal
	 * 
	 * @return Sum of Manhattan distances between blocks and goal
	 */
	private int calculateManhattanDistance() {
		int distance = 0;

		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (this.blocks[i][j] != toGoal(i, j) && this.blocks[i][j] != 0) {
					distance += calculateManhattanDistance(i, j);
				}
			}
		}

		return distance;
	}

	/**
	 * Calculate Manhattan distance
	 * 
	 * @param i vertical position (row)
	 * @param j horizontal position (column)
	 * @return Manhattan distance
	 */
	private int calculateManhattanDistance(int i, int j) {
		int blockGoal = this.blocks[i][j];
		int row = blockGoal / this.dimension;
		int col = blockGoal % this.dimension;
		if (col == 0) {
			row--;
			col = this.dimension - 1;
		} else {
			col--;
		}
		
		int manhattanDistance = 0;
		manhattanDistance = Math.abs(i - row) + Math.abs(j - col);

		return manhattanDistance;
	}
	
	/**
	 * Determine if the current board is the goal
	 * 
	 * @return True if this board is the goal
	 */
	public boolean isGoal() {
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (this.blocks[i][j] != toGoal(i, j)) {
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * Method that converts a tile position to it's expected 
	 * tile value.
	 * 
	 * @param i row of the tile
	 * @param j column of the tile
	 * @return goal value
	 */
	private int toGoal(int i, int j) {
		if (i == this.dimension -1 && j == this.dimension - 1)
			return 0;
		
		return i * this.dimension + j + 1;
	}

	/**
	 * Access to the board twin
	 * 
	 * @return A twin board
	 */
	public Board twin() {
		if (twin == null)
			twin = createTwinBoard();
		
		return twin;
	}
	
	/**
	 * Create a board that is obtained by exchanging any pair (randomly) of blocks
	 * 
	 * @return A twin board
	 */
	private Board createTwinBoard() {
		int[] tile1;
		int[] tile2;
		
		int i = 0;
		int aux = 0;
		
		do {
			tile1 = nTo2D(i);
			aux = this.blocks[tile1[0]][tile1[1]];
			i++;
		} while (aux == 0);
		
		do {
			tile2 = nTo2D(i);
			aux = this.blocks[tile2[0]][tile2[1]];
			i++;
		} while (aux == 0);

		
		int[][] newBlock = swap(tile1[0], tile1[1], tile2[0], tile2[1]);
		Board board = new Board(newBlock);

		return board;
	}

	/**
	 * Given a 1D position, calculate it's 2D position in the board
	 * 
	 * @param n 1D position
	 * @return Array with index 0 = row and index 1 = column 
	 */
	private int[] nTo2D(int n) {
		int[] pos = new int[2];

		if (n == 0) {
			pos[0] = 0;
			pos[1] = 0;
			return pos;
		}

		int row = n / this.dimension;
		int col = n - (row * this.dimension);

		pos[0] = row;
		pos[1] = col;

		return pos;
	}
	
	/**
	 * Swap the value of two tiles
	 * 
	 * @param i1 Row of tile 1
	 * @param j1 Column of tile 1
	 * @param i2 Row of tile 2
	 * @param j2 Column of tile 2
	 * @return New block with the tiles swaped
	 */
	private int[][] swap(int i1, int j1, int i2, int j2) {
		// Create a copy of the board, the class is inmutable
		int[][] newBlock = cloneArray(this.blocks);
		
		int tile1 = newBlock[i1][j1];
		int tile2 = newBlock[i2][j2];
		newBlock[i1][j1] = tile2;
		newBlock[i2][j2] = tile1;
		
		return newBlock;
	}
	
	/**
	 * Clone an array
	 * 
	 * @param src Array to be cloned
	 * @return Arry cloned
	 */
	private int[][] cloneArray(int[][] src) {
	    int length = src.length;
	    int[][] target = new int[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}

	/**
	 * Compare two boards
	 * 
	 * @return True if two boards are equals.
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		Board that = (Board) o;

		if (this.dimension != that.dimension)
			return false;

		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				if (this.blocks[i][j] != that.blocks[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Calculate the boards that can be generated moving a tile to the blank space
	 * 
	 * @return Iterable Boards
	 */
	public Iterable<Board> neighbors() {

		int i = 0;
		int j = 0;
		boolean found0 = false;

		// Find empty block
		for (i = 0; i < this.dimension; i++) {
			for (j = 0; j < this.dimension; j++) {
				if (this.blocks[i][j] == 0) {
					found0 = true;
					break;
				}
			}

			if (found0)
				break;
		}

		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;

		// check the available movements
		if (i > 0 && i <= this.dimension - 1)
			up = true;
		if (i >= 0 && i < this.dimension - 1)
			down = true;
		if (j > 0 && j <= this.dimension - 1)
			left = true;
		if (j >= 0 && j < this.dimension - 1)
			right = true;

		Stack<Board> stack = new Stack<>();

		if (up) {
			int[][] newBlock = move(1, i, j);
			Board board = new Board(newBlock);
			stack.push(board);
		}

		if (down) {
			int[][] newBlock = move(2, i, j);
			Board board = new Board(newBlock);
			stack.push(board);
		}

		if (left) {
			int[][] newBlock = move(3, i, j);
			Board board = new Board(newBlock);
			stack.push(board);
		}

		if (right) {
			int[][] newBlock = move(4, i, j);
			Board board = new Board(newBlock);
			stack.push(board);
		}

		return stack;
	}
	
	/**
	 * Given a direction and a position's tile, swap the blocks
	 * 
	 * @param direction Direction 1 - 4
	 * @param i Row of the blank space
	 * @param j Column of the blank space
	 * @return New block with the the tiles moved
	 */
	private int[][] move(int direction, int i, int j) {
		/*
		 * up = 1; down = 2; left = 3; right = 4;
		 */

		if (direction == 1)
			return swap(i, j, --i, j);
		if (direction == 2)
			return swap(i, j, ++i, j);
		if (direction == 3)
			return swap(i, j, i, --j);
		if (direction == 4)
			return swap(i, j, i, ++j);
		
		return new int[this.dimension][this.dimension];
	}

	/**
	 * String representation of this board
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.dimension + "\n");
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				s.append(String.format("%2d ", this.blocks[i][j]));
			}
			s.append("\n");
		}

		return s.toString();
	}
	
	/**
	 * Board tester
	 * 
	 * @param args Text file path containing the board information
	 */
	public static void main(String[] args) {
		// create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] tiles = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            tiles[i][j] = in.readInt();
	    Board initial = new Board(tiles);
	    StdOut.println(initial);
	}
}