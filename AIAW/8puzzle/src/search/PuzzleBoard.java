package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PuzzleBoard {
	private int N_;
	private int hBlank_ = -1;
	private int vBlank_ = -1;
	private int[][]  tiles_;
	
	public PuzzleBoard(int[][] tiles) {
		if (!isInvalidBoard(tiles)) {
			throw new IllegalArgumentException("Invalid board: " + toString());
		}
		
		N_ = tiles.length;
		tiles_ = deepCopy(tiles);
		
		for (int i = 0; i < N_; ++i) {
			for (int j = 0; j < N_; ++j) {
				if (0 == tiles[i][j] || N_ * N_ == tiles[i][j]) {
					hBlank_ = i;
					vBlank_ = j;
					break;
				}
			}
			if (-1 != hBlank_ && -1 != vBlank_) {
				break;
			}
		}
		
		tiles_[hBlank_][vBlank_] = N_ * N_;
	}
	
	public int getSize() {return N_;}
	
	public PuzzleBoard copy() {
		tiles_[hBlank_][vBlank_] = 0;
		PuzzleBoard b= new PuzzleBoard(tiles_);
		tiles_[hBlank_][vBlank_] = N_ * N_;
		return b;
	}
	
	public boolean isSolvable() {
		int inversions = 0;
		int[] tiles = falttenArray(tiles_);
		
		
		for (int i = 1; i < tiles.length - 1; ++i) {
			for (int j = i - 1; j >= 0; --j) {
				if (tiles[j] > tiles[j + 1]) {
					int aux = tiles[j];
					tiles[j] = tiles[j + 1];
					tiles[j + 1] = aux;
					++inversions;
				}
			}
		}
		
		if (0 != N_ % 2) {
			return 0 == inversions % 2;
		}
		
		return (0 == inversions % 2) == (0 != (N_ - hBlank_ - 1) % 2);
	}
	

	public boolean isGoal() {
		return 0 == hamming();
	}
	
	public Iterable<PuzzleBoard> neighbors() {
		int[] dx = {-1, 0, 0, 1};
		int[] dy = {0, -1, 1, 0};
		List<PuzzleBoard> v = new ArrayList<>();
		
		for (int i = 0; i < dx.length; ++i) {
			int newHBlank = hBlank_ + dx[i];
			int newVBlank = vBlank_ + dy[i];
			
			if (newHBlank < 0 || newVBlank < 0 || newHBlank >= N_ || newVBlank >= N_) {
				continue;
			}
			
			PuzzleBoard b = copy();
			int aux = b.tiles_[hBlank_][vBlank_];
			b.tiles_[hBlank_][vBlank_] = b.tiles_[newHBlank][newVBlank];
			b.tiles_[newHBlank][newVBlank] = aux;
			b.hBlank_ = newHBlank;
			b.vBlank_ = newVBlank;
			
			v.add(b);
		}
		
		return v;
	}
	
	public int hamming() {
		int dist = 0;
		
		for (int i = 0; i < N_; ++i) {
			for (int j = 0; j < N_; ++j) {
				if (i * N_ + j + 1 != tiles_[i][j]) {
					++dist;
				}
			}
		}
		
		return dist;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof PuzzleBoard)) {
			return false;
		}
		PuzzleBoard b = (PuzzleBoard)obj;
		return N_ == b.N_ && Arrays.deepEquals(tiles_, b.tiles_);
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(tiles_);
	}
	
	@Override
	public String toString() {
		tiles_[hBlank_][vBlank_] = 0;
		String ans =  Arrays.deepToString(tiles_) + "\n";
		tiles_[hBlank_][vBlank_] =  N_ * N_;
		return ans;
	}
	
	private int[] falttenArray(int[][] tiles_) {
		int[] v = new int[N_ * N_ - 1];
		
		for (int k = 0, i = 0; i < N_; ++i) {
			for (int j = 0; j < N_; ++j) {
				if (tiles_[i][j] < N_ * N_) {
					v[k++] = tiles_[i][j];
				}
			}
		}
		
		return v;
	}
	
	private int[][] deepCopy(int[][] tiles_) {
		int[][] tiles = new int[N_][N_];
		
		for (int i = 0; i < N_; ++i) {
			tiles[i] = Arrays.copyOf(tiles_[i], N_);
		}
		
		return tiles;
	}

	private boolean isInvalidBoard(int[][] tiles) {
		if (null == tiles || 0 == tiles.length) {
			return false;
		}
		
		int N = tiles.length;
		HashSet<Integer> was = new HashSet<>();
		for (int i = 0; i < N; ++i) {
			if (null == tiles[i] || N != tiles[i].length) {
				return false;
			}
			for (int j = 0; j < N; ++j) {
				if (tiles[i][j] < 0 || tiles[i][j] >= N * N || was.contains(tiles[i][j])) {
					return false;
				}
			}
		}
		
		return true;
	}
}
