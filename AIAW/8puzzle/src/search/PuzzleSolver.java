package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class PuzzleSolver {
    PuzzleBoard       initial_;
    List<PuzzleBoard> solution_ = new ArrayList<>();

    public PuzzleSolver(PuzzleBoard initial) {
        if (null == initial) { throw new IllegalArgumentException(
                "Null puzzleboard presented"); }

        initial_ = initial.copy();

        if (initial_.isSolvable()) {
            solve();
        }
    }

    public int moves() {
        return solution_.size();
    }

    public Iterable<PuzzleBoard> solution() {
        return solution_;
    }

    public String toString() {
        if (solution_.isEmpty()) { return "No solution"; }
        return "Minimum number of moves: "
                + moves()
                + "\n"
                + Arrays.deepToString(solution_
                        .toArray(new PuzzleBoard[solution_.size()]));
    }

    private void solve() {
        if (initial_.isGoal()) {
            solution_.add(initial_.copy());
            return;
        }

        PriorityQueue<Node> minPQ = new PriorityQueue<>();
        HashSet<PuzzleBoard> closeSet = new HashSet<>();

        for (minPQ.add(new Node(initial_, 0, 0)); !minPQ.isEmpty();) {
            Node b = minPQ.remove();

            if (b.b_.isGoal()) {
                getPath(b);
                return;
            }

            closeSet.add(b.b_);

            for (PuzzleBoard newB : b.b_.neighbors()) {
                if (closeSet.contains(newB) || newB.equals(b.b_)) {
                    continue;
                }
                closeSet.add(newB);
                minPQ.add(new Node(newB, b.moves_ + 1 + newB.hamming(), b));
            }
        }
    }

    private void getPath(Node dest) {
        for (; null != dest; dest = dest.prev_) {
            solution_.add(dest.b_);
        }
        Collections.reverse(solution_);
    }

    private static class Node implements Comparable<Node> {
        int         cost_;
        int         moves_;
        Node        prev_;
        PuzzleBoard b_;

        public Node(PuzzleBoard b, int cost, int moves) {
            b_ = b;
            prev_ = null;
            cost_ = cost;
            moves_ = moves;
        }

        public Node(PuzzleBoard b, int cost, Node prev) {
            this(b, cost, prev.moves_ + 1);
            b_ = b;
            prev_ = prev;
        }

        @Override
        public int compareTo(Node o) {
            return cost_ - o.cost_;
        }
    }
}
