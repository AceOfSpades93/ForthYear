package AIAW.eightpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PuzzleBoard {

	private int N, M;
	private int[][] tiles_;
	private int hBlank, vBlank;
	
	private static int[] dx = {-1, 0, 0, 1};
	private static int[] dy = {0, -1, 1, 0};
	
	public PuzzleBoard(int[][] tiles) {
		if (null == tiles || tiles.length <= 0) {
		  throw new IllegalArgumentException();
		}
		
		N = tiles.length;
		M = tiles[0].length;
		tiles_ = new int[N][M];
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				if (0 == tiles[i][j]) {
					hBlank = i;
					vBlank = j;
				}
				tiles_[i][j] = tiles[i][j];
			}
		}
	}
	
	public PuzzleBoard copy() {
		return new PuzzleBoard(tiles_);
	}
	
	public boolean equals(Object y) {
		if (null == y || !(y instanceof PuzzleBoard)) {
			return false;
		}
		
		return Arrays.deepEquals(tiles_, ((PuzzleBoard)y).tiles_);
		
	}
	
	public Iterable<PuzzleBoard> neighboars() {
		List<PuzzleBoard> v = new ArrayList<>();
		for (int i = 0; i < dx.length; ++i) {
			int newHBlank = hBlank + dx[i];
			int newVBlank = vBlank + dy[i];
			
			if (newHBlank < 0 || newVBlank < 0 || newHBlank >= N || newVBlank >= M) {
				continue;
			}
			
			PuzzleBoard b = copy();;
			
			int aux = b.tiles_[hBlank][vBlank];
			b.tiles_[hBlank][vBlank] = b.tiles_[newHBlank][newVBlank];
			b.tiles_[newHBlank][newVBlank] = aux;
		
			v.add(b);
		}
		
		return v;
	}
}