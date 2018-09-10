import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private class Move implements Comparable<Move> {
		private Board board;
		private Move previous;
		private int numOfMoves = 0;
		
		private Move(Board initial) {
			this.board = initial;
		}
		
		private Move(Board board, Move previous) {
			this.board = board;
			this.previous = previous;
			numOfMoves += previous.numOfMoves+1;
		}

		public int compareTo(Move that) {
			return (board.manhattan()+numOfMoves) - (that.board.manhattan() + that.numOfMoves);
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
			if (lastMove == null || !neighbor.equals(bestMove.previous.board)){
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
		return lastMove.numOfMoves;
	}
	
	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!isSolvable()) return null;
		
		Stack<Board> moves = new Stack<Board>();
		while (lastMove != null) {
			moves.push(lastMove.board);
			lastMove = lastMove.previous;
		}
		return moves;
	}
	
	// solve a slider puzzle
	public static void main (String[] args) {
		// create initial board from file
		int[][] blocks = {
				{8, 3, 5},
				{6, 4, 2},
				{1, 0, 7},
		};
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
