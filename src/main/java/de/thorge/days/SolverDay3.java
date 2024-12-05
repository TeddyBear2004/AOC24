package de.thorge.days;

import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolverDay3 extends Solver {

    @Override
    public RiddleSolver<Long> getFirstRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Long getSolution() {
                return 0L;
            }

            @Override
            public Long solveRiddle() {
                return 0L;
            }
        };
    }

    @Override
    public RiddleSolver<Long> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Long getSolution() {
                return 106921067L;
            }

            @Override
            public Long solveRiddle() {
                Pattern compile = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
                return splitLines().map(s -> {
                    String[] segments = s.split("(?=do\\(\\))|(?=don't\\(\\))");
                    long solution = 0;
                    for (String segment : segments) {
                        if (segment.startsWith("don't()")) {
                            continue;
                        }

                        Matcher matcher = compile.matcher(segment);
                        while (matcher.find()) {
                            int prod = getProd(matcher);
                            solution += prod;
                        }

                    }
                    return solution;

                }).longSum();
            }
        };
    }

    private static int getProd(Matcher matcher) {
        int prod = 1;
        for (int i1 = 1; i1 <= matcher.groupCount(); i1++) {
            String group = matcher.group(i1);
            if (group != null) {
                prod *= Integer.parseInt(group);
            }
        }

        return prod;
    }
}


