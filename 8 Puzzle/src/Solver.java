import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/* 
 * Puzzle solver using the A* search algorithms. 
 * 
 * 
 * @author gabrielrodriguezj
 *
 */
public class Solver {
	
	/**
	 * SearchNode which contains the solution
	 */
	private SearchNode solution = null;

	/**
	 * Find a solution to the initial board (using the A* algorithm)
	 * 
	 * @param initial Initial Board
	 */
	public Solver(Board initial) {
		
		if (initial == null)
			throw new IllegalArgumentException("The initial board must not be null");
		
		boolean solved = false;
		SearchNode sn = new SearchNode(initial, 0, null);		
		MinPQ<SearchNode> pq = new MinPQ<>(sn.nodeComparator());
		
		pq.insert(sn);		
		while (true) {
			SearchNode snMin = pq.delMin();
			
			for (Board b : snMin.board.neighbors()) {
				
				if (b.isGoal()) {
					solved = true;
					solution = new SearchNode(b, snMin.moves + 1, snMin);
					break;
				}	
				
				// If is not equal to predecessor board
				if (!b.equals(snMin.board)) {
					SearchNode snTemp = new SearchNode(b, snMin.moves + 1, snMin);
					pq.insert(snTemp);
				}
			}
			
			if (solved)
				break;
		}
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
		if (!isSolvable()) {
			return -1;
		}
		
		if (solution != null) {
			// The last search node is not introduced to the MinPQ in order to reduce one extra iteration
			return solution.moves + 1;
		}
		
		return 0;
	}

	/**
	 * Sequence of boards in a shortest solution
	 * 
	 * @return null if unsolvable, iterable if is solvable
	 */
	public Iterable<Board> solution() {
		if (solution != null) {
			Stack<Board> stack = new Stack<>();
			SearchNode snSol = solution;
			while (snSol != null) {
				stack.push(snSol.board);
				snSol = snSol.previous;
			}
			return stack;
		}

		return new Stack<Board>();
	}

	/**
	 * Solve a slider puzzle
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

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
	
	/**
	 * Class for representing every element in the priority queue.
	 * 
	 * @author gabrielrodriguezj
	 *
	 */
	private class SearchNode {
		/**
		 * Current board
		 */
		private final Board board;
		
		/**
		 * Movements made to rich the current board
		 */
		private final int moves;
		
		/**
		 * Private Search Node containing the previous board and movements
		 */
		private final SearchNode previous;
		
		/**
		 * Default constructor
		 * 
		 * @param board Current board
		 * @param moves Amount of movements to rich the current board
		 * @param previous Previous state
		 */
		private SearchNode(Board board, int moves, SearchNode previous) {
			this.board = board;
			this.moves = moves;
			this.previous = previous;
		}
		
		/**
		 * Comparator instance
		 * 
		 * @return Search node comparator
		 */
		public Comparator<SearchNode> nodeComparator() {
			return new SearchNodeComparator();
		}
	}
	
	/**
	 * Implements the comparator for Search Node
	 * 
	 * @author gabrielrodriguezj
	 *
	 */
	private class SearchNodeComparator implements Comparator<SearchNode> {
		@Override
		public int compare(SearchNode searchNode1, SearchNode searchNode2) {
			
			int priorityFuncionSN1 = searchNode1.moves + searchNode1.board.manhattan();
			// int priorityFuncionSN1 = searchNode1.moves + searchNode1.board.hamming();
			int priorityFuncionSN2 = searchNode2.moves + searchNode2.board.manhattan();
			// int priorityFuncionSN2 = searchNode2.moves + searchNode2.board.hamming();
			
			if (priorityFuncionSN1 < priorityFuncionSN2)
				return -1;
			
			if (priorityFuncionSN1 > priorityFuncionSN2)
				return 1;
			
			return 0;
		}
	}
	
}
