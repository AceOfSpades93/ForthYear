package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class PuzzleSolver {
	private PuzzleBoard initial;
	private List<PuzzleBoard> sol = new ArrayList<>();
	
	public PuzzleSolver(PuzzleBoard board) {
		initial = board.clone();
		
		if (initial.isSolvable()) {
			solve();
		}
	}
	
	public boolean isSolvable() {
		return initial.isSolvable();
	}
	
	public Iterable<PuzzleBoard> solution() {
		return sol;
	}
	
	private void solve() {
		PriorityQueue<Node> pQ = new PriorityQueue<>();
		HashSet<PuzzleBoard> wasExtended = new HashSet<>();
		
		pQ.add(new Node(initial, 0));
		
		while (!pQ.isEmpty()) {
			Node b = pQ.peek();
			
			if (b.v.equals(destination)) {
				break;
			}
			
			pQ.remove();
			
			if (wasExtended.contains(b.v)) {
				continue;
			}
			
			wasExtended.add(b.v);
			
			for(PuzzleBoard newB: b.v.neighbors()) {
				pQ.add(new Node(newB, b.cost + 1 + newB.hammilton(), b));
			}
		}
		
		if (!pQ.isEmpty()) {
			getPath(pQ.remove());
		}
	}
	
	private void getPath(Node end) {
		for (; null != end; end = end.prev) {
			sol.add(0, end.v);
		}
	}

	private static class Node {
		int cost;
		Node prev = null;
		PuzzleBoard v = null; 
		
		public Node(PuzzleBoard v, int cost) {
			this.v = v;
			this.cost = cost;
		}
		
		public Node(PuzzleBoard v, int cost, Node prev) {
			this(v, cost);
			this.prev = prev;
		}
		
	};
}