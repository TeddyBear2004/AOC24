package de.thorge.days;

import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

public class SolverDay2 extends Solver {

    private static int validateSequence(String[] strings) {
        Boolean increasing = null;
        Integer last = null;

        for (String string : strings) {
            int current = Integer.parseInt(string);

            if (last == null) {
                last = current;
                continue;
            }

            if (current == last || Math.abs(last - current) > 3) {
                return 0;
            }

            if (increasing == null) {
                increasing = current > last;
            } else if ((increasing && current < last) || (!increasing && current > last)) {
                return 0;
            }

            last = current;
        }

        return 1;
    }

    @Override
    public RiddleSolver<Integer> getFirstRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 2;
            }

            @Override
            public Integer solveRiddle() {
                return splitLinesAndWords()
                        .map(SolverDay2::validateSequence)
                        .intSum();
            }
        };
    }

    @Override
    public RiddleSolver<Integer> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 4;
            }

            @Override
            public Integer solveRiddle() {
                return splitLinesAndWords()
                        .map(strings -> {
                            for (int i = 0; i < strings.length; i++) {
                                String[] trimmed = new String[strings.length - 1];
                                System.arraycopy(strings, 0, trimmed, 0, i);
                                System.arraycopy(strings, i + 1, trimmed, i, trimmed.length - i);
                                int result = validateSequence(trimmed);
                                if (result == 1) {
                                    System.out.println("Removed: " + strings[i]);
                                    return 1;
                                }
                            }
                            System.out.println("No valid removal found");
                            return validateSequence(strings);
                        }).intSum();
            }
        };
    }
}
