import edu.princeton.cs.algs4.MinPQ;

/* 
 * Puzzle solver using the A* search algorithms. 
 * 
 * 
 * @author gabrielrodriguezj
 *
 */
public class Solver {

	/**
	 * Find a solution to the initial board (using the A* algorithm)
	 * 
	 * @param initial Initial Board
	 */
	public Solver(Board initial) {
		
	}

	/**
	 * Check if the initial board is solvable
	 * 
	 * @return True if the initial board is solvable
	 */
	public boolean isSolvable() {
		return true;
	}

	/**
	 * Minimum number of moves to solve initial board
	 * 
	 * @return  -1 if unsolvable, 0< if is solvable 
	 */
	public int moves() {
		if(!isSolvable()) {
			return -1;
		}
		
		return 0;
	}

	/**
	 * Sequence of boards in a shortest solution
	 * 
	 * @return null if unsolvable, iterable if is solvable
	 */
	public Iterable<Board> solution() {
		return null;
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {
	}
}
