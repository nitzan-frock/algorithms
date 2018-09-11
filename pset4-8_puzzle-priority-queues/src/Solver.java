import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private class Move implements Comparable<Move> {
		private Board board;
		private Move previous;
		private int numOfMoves;
		
		private Move(Board initial) {
			this.board = initial;
			this.previous = null;
			numOfMoves = 0;
		}
		
		private Move(Board board, Move previous) {
			this.board = board;
			this.previous = previous;
			numOfMoves = previous.numOfMoves+1;
		}

		public int compareTo(Move that) {			
			return (board.manhattan() + numOfMoves) - (that.board.manhattan() + that.numOfMoves);
		}
	}
	
	// linked list of moves
	private Move lastMove;
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null) throw new IllegalArgumentException("No null arguments.");
		MinPQ<Move> moves = new MinPQ<Move>();
		moves.insert(new Move(initial));
		MinPQ<Move> movesTwin = new MinPQ<Move>();
		movesTwin.insert(new Move(initial.twin()));
		
		while (true) {
			lastMove = findSolution(moves);
			if (lastMove != null || findSolution(movesTwin) != null) return;
		}
	}
	
	private Move findSolution(MinPQ<Move> moves) {
		if (moves.isEmpty()) return null;
		Move bestMove = moves.delMin();
		if (bestMove.board.isGoal()) return bestMove;
		for (Board neighbor: bestMove.board.neighbors()) {
			if (bestMove.previous == null || (lastMove == null && !bestMove.previous.board.equals(neighbor))){
				moves.insert(new Move(neighbor, bestMove));
			}
		}
		
		return null;
	}
	
	// is the initial board solvable?
	public boolean isSolvable() {
		return lastMove != null;
	}
	
	// min number of moves to solve inital board; -1 if unsolvable
	public int moves() {
		if (!isSolvable()) {
			return -1;
		}
		return lastMove.numOfMoves;
	}
	
	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!isSolvable()) return null;
		
		Stack<Board> solution = new Stack<Board>();
		while (lastMove != null) {
			solution.push(lastMove.board);
			lastMove = lastMove.previous;
		}
		return solution;
	}
	
	// solve a slider puzzle
	public static void main (String[] args) {
		// create initial board from file
//		int[][] blocks = {
//				{ 1, 2, 3, 4, 5, 6, 7, 8, 9,10},
//				{11,12,13,14,15,16,17,18,19,20},
//				{21,22,23,24,25,26,27,28,29,30},
//				{31,32,33,34,35,36,37,38,39,40},
//				{41,42,43,44,45,46,47,48,49,50},
//				{51,52,53,54,55,56,57,58,59,60},
//				{61,62,63,64,65,66,67,68,69,70},
//				{71,72,73,74,75,76,77,78,79,80},
//				{81,82,83,84,85,86,87,88,89,90},
//				{91,92,93,94,95,96,97,98,99, 0},
//		};
		
		int[][] blocks = {
				{1,2,3},
				{4,5,6},
				{7,8,0}
		};
		
//		int[][] blocks = {
//				{0,1,3},
//				{4,2,5},
//				{7,8,6}
//		};
		
	    Board initial = new Board(blocks);

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
}
