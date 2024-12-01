package de.thorge.days;

import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SolverDay1 extends Solver {

    @Override
    public RiddleSolver<Integer> getFirstRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 11;
            }

            @Override
            public Integer solveRiddle() {
                Pattern pattern = Pattern.compile("");

                List<int[]> list = splitLineAndRegexGroups("(\\d+)\\s+(\\d+)")
                        .map(strings -> strings.get(0))
                        .map(strings -> {
                            int left = Integer.parseInt(strings[0]);
                            int right = Integer.parseInt(strings[1]);

                            return new int[]{left, right};
                        }).stream().toList();

                List<Integer> left = list.stream().map(ints -> ints[0]).toList();
                List<Integer> right = list.stream().map(ints -> ints[1]).toList();

                return solveDay1(left, right);
            }
        };
    }

    @Override
    public RiddleSolver<Integer> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 31;
            }

            @Override
            public Integer solveRiddle() {
                List<int[]> list = splitLineAndRegexGroups("(\\d+)\\s+(\\d+)")
                        .map(strings -> strings.get(0))
                        .map(strings -> {
                            int left = Integer.parseInt(strings[0]);
                            int right = Integer.parseInt(strings[1]);

                            return new int[]{left, right};
                        }).stream().toList();

                List<Integer> left = list.stream().map(ints -> ints[0]).toList();
                List<Integer> right = list.stream().map(ints -> ints[1]).toList();


                int sum = 0;
                for (Integer i1 : left) {
                    long count = right.stream().filter(i1::equals).count();

                    sum += (int) (i1 * count);
                }
                return sum;
            }
        };
    }

    private int solveDay1(List<Integer> left, List<Integer> right) {
        left = new ArrayList<>(left);
        right = new ArrayList<>(right);

        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);

        int sum = 0;
        for (int i = 0; i < left.size(); i++) {
            Integer i1 = left.get(i);
            sum += Math.abs(
                    i1 - right.get(i)
            );
        }
        return sum;
    }
}
