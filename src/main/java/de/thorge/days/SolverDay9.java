package de.thorge.days;

import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolverDay9 extends Solver {

    @Override
    public RiddleSolver<Integer> getFirstRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 114;
            }

            @Override
            public Integer solveRiddle() {
                return splitLinesAndWords()
                        .map(strings -> Arrays.stream(strings)
                                .filter(s -> {
                                    try{
                                        Integer.parseInt(s);
                                        return true;
                                    }catch(NumberFormatException e){
                                        return false;
                                    }
                                })
                                .map(Integer::parseInt).toList())
                        .map(integers -> {
                            List<List<Integer>> integers1 = new ArrayList<>();
                            integers1.add(new ArrayList<>(integers));
                            return integers1;
                        })
                        .map(integers -> getNextValue(integers))
                        .intSum();
            }
        };
    }

    public int getNextValue(List<List<Integer>> integers) {
        int lastLayer = integers.size() - 1;
        List<Integer> lastLayerInts = integers.get(lastLayer);

        if (lastLayerInts.stream().allMatch(integer -> integer == 0)) {
            lastLayerInts.add(0);

            for (int i = lastLayer; i >= 1; i--) {
                int lastElement = integers.get(i).size() - 1;
                int a = integers.get(i).get(lastElement) + integers.get(i - 1).get(lastElement);
                integers.get(i - 1).add(a);
            }

            return integers.get(0).get(integers.get(0).size() - 1);
        }
        List<Integer> integers1 = new ArrayList<>();
        for (int i = 1; i < lastLayerInts.size(); i++) {
            integers1.add(lastLayerInts.get(i) - lastLayerInts.get(i - 1));
        }

        integers.add(integers1);
        return getNextValue(integers);
    }

    @Override
    public RiddleSolver<Integer> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 2;
            }

            @Override
            public Integer solveRiddle() {
                return splitLinesAndWords()
                        .map(strings -> Arrays.stream(strings)
                                .filter(s -> {
                                    try{
                                        Integer.parseInt(s);
                                        return true;
                                    }catch(NumberFormatException e){
                                        return false;
                                    }
                                })
                                .map(Integer::parseInt).toList())
                        .map(integers -> {
                            List<List<Integer>> integers1 = new ArrayList<>();
                            integers1.add(new ArrayList<>(integers));
                            return integers1;
                        })
                        .map(integers -> getPreviousValue(integers))
                        .intSum();
            }
        };
    }

    public int getPreviousValue(List<List<Integer>> integers) {
        int lastLayer = integers.size() - 1;
        List<Integer> lastLayerInts = integers.get(lastLayer);

        if (lastLayerInts.stream().allMatch(integer -> integer == 0)) {
            lastLayerInts.add(0, 0);

            for (int i = lastLayer; i >= 1; i--) {
                int firstElement = 0;
                int a = integers.get(i - 1).get(firstElement) - integers.get(i).get(firstElement);
                integers.get(i - 1).add(0, a);
            }

            for (List<Integer> integer : integers) {
                System.out.println(integer);
            }

            return integers.get(0).get(0);
        }
        List<Integer> integers1 = new ArrayList<>();
        for (int i = 1; i < lastLayerInts.size(); i++) {
            integers1.add(lastLayerInts.get(i) - lastLayerInts.get(i - 1));
        }

        integers.add(integers1);
        return getPreviousValue(integers);
    }

}


