package Demo;

import search.PuzzleBoard;
import search.PuzzleSolver;

public class Demo {
    public static void main(String[] args) {

        int[][] init = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };

        System.out.println(new PuzzleSolver(new PuzzleBoard(init)));
    }

}
