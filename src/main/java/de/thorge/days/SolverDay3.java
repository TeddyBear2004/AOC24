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
                return 161L;
            }

            @Override
            public Long solveRiddle() {
                return splitLineAndRegexGroups("mul\\((\\d{1,3})(,\\d{1,3})?(,\\d{1,3})?\\)")
                        .map(strings -> {
                            int sum = 0;
                            for (String[] string : strings) {
                                int solution = 1;
                                for (int i = 0; i < string.length; i++) {
                                    if (string[i] == null)
                                        continue;
                                    if (string[i].startsWith(",")) {
                                        string[i] = string[i].substring(1);
                                    }
                                    solution *= Integer.parseInt(string[i]);
                                }
                                sum += solution;
                            }
                            return sum;
                        }).longSum();
            }
        };
    }

    @Override
    public RiddleSolver<Long> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Long getSolution() {
                return 48L;
            }


            @Override
            public String overrideTest() {
                return "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
            }

            @Override
            public Long solveRiddle() {
                Pattern compile = Pattern.compile("mul\\((\\d{1,3})(,\\d{1,3})?(,\\d{1,3})?\\)");
                return splitLines().map(s -> {
                    Matcher matcher = compile.matcher(s);

                    long sum = 0;

                    while (matcher.find()) {
                        long solution = 1;
                        boolean any = false;
                        for (int i = 1; i < matcher.groupCount(); i++) {
                            int start = matcher.start(i);
                            String substring = s.substring(0, start);
                            int dontI = substring.lastIndexOf("don't()");
                            int doI = substring.lastIndexOf("do()");
                            if (dontI > doI) {
                                continue;
                            }

                            if (matcher.group(i).startsWith(",")) {
                                solution *= Integer.parseInt(matcher.group(i).substring(1));
                            } else
                                solution *= Integer.parseInt(matcher.group(i));
                            any = true;
                        }
                        if (any)
                            sum += solution;
                    }

                    return sum;
                }).longSum();
            }
        };
    }

}


