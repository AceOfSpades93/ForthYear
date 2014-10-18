package search;

import java.util.ArrayList;
import java.util.List;

public class PuzzleBoard {
	private int N;
	private int hBlank, vBlank;
	private int[][] tiles_;
	
	public PuzzleBoard(int[][] tiles) {
		if (null == tiles || 0 == tiles.length || tiles.length != tiles[0].length) {
			throw new IllegalArgumentException();
		}
		
		N = tiles.length;
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				tiles_[i][j] = tiles[i][j];
				if (0 == tiles[i][j]) {
					hBlank = i;
					vBlank = j;
				}
			}
		}
		
		tiles_[hBlank][vBlank] = N * N;
	}
	
	public PuzzleBoard clone() {
		return new PuzzleBoard(tiles_);
	}
	
	public int manhattan() {
		int dist = 0;
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				dist += Math.abs(i - tiles_[i][j] / N) + Math.abs(j - (tiles_[i][j] - 1) % N);
			}
		}
		
		return dist;
	}
	
	public int hammilton() {
		int dist = 0;
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (i * N + j  + 1 != tiles_[i][j] ) {
					++dist;
				}
			}
		}
		return dist;
	}
	
	public boolean isSolvable() {
		List<Integer> v = new ArrayList<>();
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (tiles_[i][j] < N* N) {
					v.add(tiles_[i][j]);
				}
			}
		}
		
		int numberOfInversions = countInversions(v.toArray(new Integer[v.size()]), 0, v.size() - 1);
		
		return true;
	}
	
	private int countInversions(Integer[] v, int start, int end) {
		int count = 0;
		
		for (int i = start; i <= end; ++i) {
			for (int j = i - 1; j >= 0; --j) {
				if (v[j + 1] >= v[j]) {
					break;
				}
				int aux = v[j + 1];
				v[j + 1] = v[j];
				v[j] = aux;
			}
		}
		
		return count;
	}

	public Iterable<PuzzleBoard> neighbors() {
		int[] dx = {-1, 0, 0, 1};
		int[] dy = {0, -1, 1, 0};
		
		List<PuzzleBoard> v = new ArrayList<>();
		
		for (int i = 0; i < dx.length; ++i) {
			int newHBlank = hBlank + dx[i];
			int newVBlank = vBlank + dy[i];
			
			if (newHBlank < 0 || newVBlank < 0 || newHBlank >= N || newVBlank >= N) {
				continue;
			}
			
			PuzzleBoard b = new PuzzleBoard(tiles_);
			
			int aux = b.tiles_[newHBlank][newVBlank];
			b.tiles_[newHBlank][newVBlank] = b.tiles_[hBlank][vBlank];
			b.tiles_[hBlank][vBlank] = aux;
			
			v.add(b);
		}
		
		return v;
	}
}
