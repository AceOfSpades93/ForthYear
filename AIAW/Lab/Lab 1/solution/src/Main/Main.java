package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import eightPuzzle.Board;
import eightPuzzle.PuzzleSolver;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner input = null;
		PrintStream output = null;
		try {
			input = new Scanner(new File("input.in"));
			int[][] board = new int[3][3];
			
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					board[i][j] = input.nextInt();
				}
			}
			
			int[][] goal = new int[][] {{1, 2, 3},
									    {8, 0, 4},
									    {7, 6, 5}
									   };
			
			List<Board> sol = new PuzzleSolver(new Board(board), new Board(goal)).getSolution();
			
			output = new PrintStream(new File("output.out"));
			output.println("isSolvable: " + (sol.size() > 0) );
			if (sol.size() > 0) {
				output.println(sol.size());
				for (Board b : sol) {
					output.println(b.toString());
				}
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		finally {
			if (null != input) {
				input.close();
			}
			if (null != output) {
				output.close();
			}
		}
		
	}
}
