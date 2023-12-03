package de.thorge.days;

import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolverDay1 extends Solver {
	private static final List<String> NUMBERS = List.of("one",
			"two",
			"three",
			"four",
			"five",
			"six",
			"seven",
			"eight",
			"nine");

	@Override
	public RiddleSolver<Integer> getFirstRiddleSolver() {
		return new RiddleSolver<>() {
			@Override
			public Integer getSolution() {
				return 142;
			}

			@Override
			public Integer solveRiddle() {
				Pattern pattern = Pattern.compile("(?<digit>\\d)");

				return splitLinesAndConvert(pattern::matcher).map(matcher -> solveDay1(matcher)).intSum();
			}
		};
	}

	@Override
	public RiddleSolver<Integer> getSecondRiddleSolver() {
		return new RiddleSolver<>() {
			@Override
			public Integer getSolution() {
				return 281;
			}

			@Override
			public String overrideTest() {
				return """
						two1nine
						eightwothree
						abcone2threexyz
						xtwone3four
						4nineeightseven2
						zoneight234
						7pqrstsixteen
						""";
			}

			@Override
			public Integer solveRiddle() {
				Pattern pattern = Pattern.compile("(?=((?<digit>\\d)|(?<word>one|two|three|four|five|six|seven|eight|nine)))");

				return splitLinesAndConvert(pattern::matcher).map(matcher -> solveDay1(matcher))
						.intSum();
			}
		};
	}

	private int solveDay1(Matcher matcher) {
		int sol = 0;

		while (matcher.find()) {
			sol = sol / 10 * 10;
			sol += matcher.group("digit") == null ? NUMBERS.indexOf(matcher.group("word")) + 1 : Integer.parseInt(matcher.group("digit"));

			if (sol < 10)
				sol = sol * 11;
		}

		return sol;
	}
}
