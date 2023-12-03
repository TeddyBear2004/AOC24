package de.thorge.days;

import de.thorge.AdvancedList;
import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SolverDay3 extends Solver {

    @Override
    public RiddleSolver<Long> getFirstRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Long getSolution() {
                return 4361L;
            }

            @Override
            public Long solveRiddle() {
                AdvancedList<AdvancedList<Character>> advancedLists = splitLinesAndConvert(s -> {
                    AdvancedList<Character> objects = new AdvancedList<>();
                    for (char c : s.toCharArray()) {
                        objects.add(c == '.' ? null : c);
                    }
                    objects.add(null);

                    return objects;
                });

                long sum = 0;
                for (int lineNr = 0; lineNr < advancedLists.size(); lineNr++) {
                    int currentInt = 0;
                    AtomicBoolean isConfirmed = new AtomicBoolean(false);

                    nextChar:
                    for (int charNr = 0; charNr < advancedLists.get(lineNr).size(); charNr++) {
                        Character c = advancedLists.get(lineNr).get(charNr);

                        if (c != null) {
                            if (Character.isDigit(c)) {
                                currentInt = currentInt * 10 + Character.getNumericValue(c);
                            } else {
                                if (currentInt != 0) {
                                    if (isConfirmed.get()) {
                                        sum += currentInt;
                                    }

                                }

                                isConfirmed.set(false);
                                currentInt = 0;
                                continue;
                            }
                        } else {
                            if (currentInt != 0) {
                                if (isConfirmed.get()) {
                                    sum += currentInt;
                                }

                            }

                            isConfirmed.set(false);
                            currentInt = 0;
                            continue;
                        }

                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (i == 0 && j == 0)
                                    continue;

                                if (!Character.isDigit(c)) {
                                    continue nextChar;
                                }

                                if (isConfirmed.get())
                                    continue;

                                int dx = lineNr + i;
                                int dy = charNr + j;

                                advancedLists.getOptional(dx)
                                        .flatMap(characters -> characters.getOptional(dy))
                                        .filter(character -> !Character.isSpaceChar(character))
                                        .filter(character -> character != '\r')
                                        .filter(character -> !Character.isDigit(character))
                                        .ifPresent(character -> isConfirmed.set(true));
                            }
                        }
                    }

                }
                return sum;
            }
        };
    }

    @Override
    public RiddleSolver<Long> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Long getSolution() {
                return 467835L;
            }

            @Override
            public Long solveRiddle() {
                AdvancedList<AdvancedList<Character>> advancedLists = splitLinesAndConvert(s -> {
                    AdvancedList<Character> objects = new AdvancedList<>();
                    for (char c : s.toCharArray()) {
                        objects.add(c == '.' ? null : c);
                    }
                    objects.add(null);

                    return objects;
                });

                Map<Integer, Set<AtomicInteger>> map = new HashMap<>();

                for (int lineNr = 0; lineNr < advancedLists.size(); lineNr++) {

                    AtomicInteger currentInt = new AtomicInteger();
                    nextChar:
                    for (int charNr = 0; charNr < advancedLists.get(lineNr).size(); charNr++) {
                        Character c = advancedLists.get(lineNr).get(charNr);

                        if (c != null) {
                            if (Character.isDigit(c)) {
                                currentInt.set(currentInt.get() * 10 + Character.getNumericValue(c));
                            } else {
                                currentInt = new AtomicInteger();
                                continue;
                            }
                        } else {
                            currentInt = new AtomicInteger();
                            continue;
                        }

                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                if (i == 0 && j == 0)
                                    continue;

                                if (!Character.isDigit(c)) {
                                    continue nextChar;
                                }

                                int dx = lineNr + i;
                                int dy = charNr + j;

                                AtomicInteger finalCurrentInt = currentInt;
                                advancedLists.getOptional(dx)
                                        .flatMap(characters -> characters.getOptional(dy))
                                        .filter(character -> character == '*')
                                        .ifPresent(character -> {
                                            int i1 = (dx * 1000) + dy;
                                            map.putIfAbsent(i1, new HashSet<>());
                                            map.get(i1).add(finalCurrentInt);
                                        });
                            }
                        }
                    }
                }

                long sum = 0;

                for (Set<AtomicInteger> value : map.values()) {
                    if (value.size() == 2) {
                        int m = 1;
                        for (AtomicInteger atomicInteger : value) {
                            m *= atomicInteger.get();
                        }
                        sum += m;
                    }
                }

                return sum;
            }
        };
    }

}


