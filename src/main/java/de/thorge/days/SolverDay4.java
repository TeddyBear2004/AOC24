package de.thorge.days;

import de.thorge.AdvancedList;
import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SolverDay4 extends Solver {
	private static final char[] WORD = new char[]{'X', 'M', 'A', 'S'};
	@Override
	public RiddleSolver<Integer> getFirstRiddleSolver() {
		return new RiddleSolver<>() {
			@Override
			public Integer getSolution() {
				return 18;
			}

			@Override
			public Integer solveRiddle() {
				AdvancedList<char[]> strings = splitLinesAndConvert(String::toCharArray);
				int sum = 0;

				for (char[] strings1 : strings) {
					sum += searchWord(strings1);
				}

				for (int i = 0; i < strings.get(0).length; i++) {
					char[] chars = new char[strings.size()];
					for (int j = 0; j < strings.size(); j++) {
						chars[j] = strings.get(j)[i];
					}

					sum += searchWord(chars);
				}

				for (int i = 0; i < strings.size(); i++) {
					int j = 0;
					List<Character> chars = new ArrayList<>();

					for (int di = 0; di < strings.size() - i; di++) {
						chars.add(strings.get(i + di)[j + di]);
					}

					sum += searchWord(toArr(chars));
				}

				for (int j = 1; j < strings.get(0).length; j++) {
					int i = 0;
					List<Character> chars = new ArrayList<>();

					for (int di = 0; di < strings.get(0).length - j; di++) {
						chars.add(strings.get(i + di)[j + di]);
					}

					sum += searchWord(toArr(chars));
				}


				for (int i = strings.size() - 1; i >= 0; i--) {
					List<Character> chars = new ArrayList<>();

					for (int di = 0; di <= i; di++) {
						chars.add(strings.get(i - di)[di]);
					}

					sum += searchWord(toArr(chars));
				}

				for (int j = 1; j < strings.get(0).length; j++) {
					List<Character> chars = new ArrayList<>();

					for (int di = 0; di < strings.get(0).length - j; di++) {
						chars.add(strings.get(strings.size() - 1 - di)[j + di]);
					}

					sum += searchWord(toArr(chars));
				}

				return sum;
			}
		};
	}

	private static char[] toArr(List<Character> chars) {
		char[] arr = new char[chars.size()];
		for (int i = 0; i < chars.size(); i++) {
			arr[i] = chars.get(i);
		}
		return arr;
	}

	private static int[][] toArrInts(List<int[]> chars) {
		int[][] arr = new int[chars.size()][];
		for (int i = 0; i < chars.size(); i++) {
			arr[i] = chars.get(i);
		}
		return arr;
	}

	private static int searchWord(char[] chars) {
		if (chars.length < WORD.length) {
			return 0;
		}

		int sum = 0;
		for (int i = 0; i < chars.length; i++) {
			if (WORD[0] == chars[i]) {
				int cur = 1;
				for (int j = 1; j < WORD.length; j++) {
					if (i + j < chars.length && WORD[j] == chars[i + j]) {
						cur++;
					} else {
						break;
					}
				}
				if (cur == WORD.length) {
					sum++;
				}
				cur = 1;
				for (int j = 1; j < WORD.length; j++) {
					if (i - j >= 0 && WORD[j] == chars[i - j]) {
						cur++;
					} else {
						break;
					}
				}

				if (cur == WORD.length) {
					sum++;
				}
			}
		}

		return sum;
	}



	@Override
	public RiddleSolver<Integer> getSecondRiddleSolver() {
		return new RiddleSolver<>() {
			@Override
			public Integer getSolution() {
				return 9;
			}

			@Override
			public Integer solveRiddle() {
				AdvancedList<char[]> strings = splitLinesAndConvert(String::toCharArray);
				int sum = 0;

				Set<int[]> leftOccurrences = new HashSet<>();
				for (int i = 0; i < strings.size(); i++) {
					int j = 0;
					List<Character> chars = new ArrayList<>();
					List<int[]> locations = new ArrayList<>();

					for (int di = 0; di < strings.size() - i; di++) {
						chars.add(strings.get(i + di)[j + di]);
						locations.add(new int[]{i + di, j + di});
					}

					leftOccurrences.addAll(searchWordMas(toArr(chars), toArrInts(locations)));
				}

				for (int j = 1; j < strings.get(0).length; j++) {
					int i = 0;
					List<Character> chars = new ArrayList<>();
					List<int[]> locations = new ArrayList<>();

					for (int di = 0; di < strings.get(0).length - j; di++) {
						chars.add(strings.get(i + di)[j + di]);
						locations.add(new int[]{i + di, j + di});
					}

					leftOccurrences.addAll(searchWordMas(toArr(chars), toArrInts(locations)));
				}

				Set<int[]> rightOccurrences = new HashSet<>();

				for (int i = strings.size() - 1; i >= 0; i--) {
					List<Character> chars = new ArrayList<>();
					List<int[]> locations = new ArrayList<>();

					for (int di = 0; di <= i; di++) {
						chars.add(strings.get(i - di)[di]);
						locations.add(new int[]{i - di, di});
					}

					rightOccurrences.addAll(searchWordMas(toArr(chars), toArrInts(locations)));
				}

				for (int j = 1; j < strings.get(0).length; j++) {
					List<Character> chars = new ArrayList<>();
					List<int[]> locations = new ArrayList<>();

					for (int di = 0; di < strings.get(0).length - j; di++) {
						chars.add(strings.get(strings.size() - 1 - di)[j + di]);
						locations.add(new int[]{strings.size() - 1 - di, j + di});
					}

					rightOccurrences.addAll(searchWordMas(toArr(chars), toArrInts(locations)));
				}

				leftOccurrences.removeIf(ints -> rightOccurrences.stream().noneMatch(ints1 -> ints1[0] == ints[0] && ints1[1] == ints[1]));

				return leftOccurrences.size();
			}
		};
	}

	private static final char[] WORD_MAS = new char[]{'M', 'A', 'S'};

	private static Set<int[]> searchWordMas(char[] chars, int[][] locations) {
		if (chars.length != locations.length) {
			throw new IllegalArgumentException("chars and locations must have the same length");
		}
		if (chars.length < WORD_MAS.length) {
			return Set.of();
		}

		Set<int[]> sum = new HashSet<>();
		for (int i = 0; i < chars.length; i++) {
			if (WORD_MAS[0] == chars[i]) {
				int cur = 1;
				for (int j = 1; j < WORD_MAS.length; j++) {
					if (i + j < chars.length && WORD_MAS[j] == chars[i + j]) {
						cur++;
					} else {
						break;
					}
				}
				if (cur == WORD_MAS.length) {
					sum.add(locations[i + 1]);
				}
				cur = 1;
				for (int j = 1; j < WORD_MAS.length; j++) {
					if (i - j >= 0 && WORD_MAS[j] == chars[i - j]) {
						cur++;
					} else {
						break;
					}
				}

				if (cur == WORD_MAS.length) {
					sum.add(locations[i - 1]);
				}
			}
		}

		return sum;
	}

}


