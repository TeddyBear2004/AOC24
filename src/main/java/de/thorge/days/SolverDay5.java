package de.thorge.days;

import de.thorge.AdvancedList;
import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SolverDay5 extends Solver {

    @Override
    public RiddleSolver<Integer> getFirstRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 143;
            }

            @Override
            public Integer solveRiddle() {
                AdvancedList<String> strings = splitLines();

                List<List<Integer>> rules = new ArrayList<>();
                List<List<Integer>> orders = new ArrayList<>();

                boolean rulesDone = false;
                for (String string : strings) {
                    if (string.isEmpty()) {
                        rulesDone = true;
                        continue;
                    }
                    if (rulesDone) {
                        String[] split = string.trim().split(",");
                        List<Integer> order = new ArrayList<>();
                        for (String s : split) {
                            order.add(Integer.parseInt(s));
                        }
                        orders.add(order);
                    } else {
                        String[] split = string.trim().split("\\|");
                        rules.add(List.of(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                    }
                }

                int sum = 0;
                outer:
                for (List<Integer> order : orders) {

                    for (int i = 0; i < order.size(); i++) {
                        int finalI = i;
                        List<Integer> illegalInts = rules.stream().filter(list -> Objects.equals(list.get(1), order.get(finalI))).map(list -> list.get(0)).toList();
                        for (int j = i + 1; j < order.size(); j++) {
                            if (illegalInts.contains(order.get(j))) {
                                continue outer;
                            }
                        }
                    }

                    sum += order.get(order.size() / 2);
                }

                return sum;
            }
        };
    }

    @Override
    public RiddleSolver<Integer> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 123;
            }

            @Override
            public Integer solveRiddle() {
                AdvancedList<String> strings = splitLines();

                List<List<Integer>> rules = new ArrayList<>();
                List<List<Integer>> orders = new ArrayList<>();

                boolean rulesDone = false;
                for (String string : strings) {
                    if (string.isEmpty()) {
                        rulesDone = true;
                        continue;
                    }
                    if (rulesDone) {
                        String[] split = string.trim().split(",");
                        List<Integer> order = new ArrayList<>();
                        for (String s : split) {
                            order.add(Integer.parseInt(s));
                        }
                        orders.add(order);
                    } else {
                        String[] split = string.trim().split("\\|");
                        rules.add(List.of(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                    }
                }

                int sum = 0;
                for (List<Integer> order : orders) {

                    List<Integer> copy = new ArrayList<>(order);
                    copy.sort((o1, o2) -> {
                        List<Integer> legalSeconds = rules.stream().filter(list -> Objects.equals(list.getFirst(), o1)).map(List::getLast).toList();
                        if (legalSeconds.contains(o2)) {
                            return -1;
                        }
                        return 1;
                    });

                    if (copy.equals(order)) {
                        continue;
                    }

                    sum += copy.get(order.size() / 2);
                }

                return sum;
            }
        };
    }
}


