import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/* 
 * Puzzle solver using the A* search algorithms. 
 * https://en.wikipedia.org/wiki/15_puzzle
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
	 * Is solvable the board provided
	 */
	private boolean solvable = false;

	/**
	 * Find a solution to the initial board (using the A* algorithm).
	 * Not all initial boards can lead to the goal board by a sequence of moves. 
	 * To detect such situations, use the fact that boards are divided into two equivalence
	 * classes with respect to reachability:
	 *    Those that can lead to the goal board
	 *    Those that can lead to the goal board if we modify the initial board 
	 *    by swapping any pair of tiles
	 * So, if the initial board is solvable, none of its twins will have a solution 
	 * Also, if the initial board is unsolvable, all of its twins are solvable.
	 * 
	 * @param initial Initial Board
	 */
	public Solver(Board initial) {
		
		if (initial == null)
			throw new IllegalArgumentException("The initial board must not be null");
		
		boolean solvedOriginal = false;
		boolean solvedTwin = false;
		
		SearchNode snOriginal = new SearchNode(initial, 0, null);
		SearchNode snTwin = new SearchNode(initial.twin(), 0, null);
		MinPQ<SearchNode> pqOriginal = new MinPQ<>(snOriginal.nodeComparator());
		MinPQ<SearchNode> pqTwin = new MinPQ<>(snTwin.nodeComparator());
		
		//Is the initial board the solution?
		if (initial.isGoal()) {
			solution = snOriginal;
			solvable = true;
			return;
		}
		
		if (snTwin.board.isGoal()) {
			solvable = false;
			return;
		}
		
		// If the initianl or the twin board are not the goal go ahead with the A* algorithm
		
		pqOriginal.insert(snOriginal);
		pqTwin.insert(snTwin);
		
		while (true) {
			snOriginal = pqOriginal.delMin();
			snTwin = pqTwin.delMin();
			
			// Processing the original board solution
			for (Board b : snOriginal.board.neighbors()) {
				
				if (b.isGoal()) {
					solvedOriginal = true;
					solution = new SearchNode(b, snOriginal.moves + 1, snOriginal);
					solvable = true;
					break;
				}	
				
				// If is not equal to predecessor board
				if (!b.equals(snOriginal.board)) {
					SearchNode snTemp = new SearchNode(b, snOriginal.moves + 1, snOriginal);
					pqOriginal.insert(snTemp);
				}
			}
			
			// Process the twin board if not found the solution in the original one
			if (!solvedOriginal) {
				// Processing the twin board solution
				for (Board b : snTwin.board.neighbors()) {
					
					if (b.isGoal()) {
						solvedTwin = true;
						// solution = new SearchNode(b, snTwin.moves + 1, snTwin);
						break;
					}
					
					if (!b.equals(snTwin.board)) {
						SearchNode snTemp = new SearchNode(b, snTwin.moves + 1, snTwin);
						pqTwin.insert(snTemp);
					}
				}
			}
			
			
			if (solvedOriginal || solvedTwin) {
				break;
			}
				
		}
	}

	/**
	 * Check if the initial board is solvable
	 * 
	 * @return True if the initial board is solvable
	 */
	public boolean isSolvable() {
		return solvable;
	}

	/**
	 * Minimum number of moves to solve initial board
	 * 
	 * @return  -1 if unsolvable, 0< if is solvable 
	 */
	public int moves() {
		if (isSolvable()) {
			return solution.moves;
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
