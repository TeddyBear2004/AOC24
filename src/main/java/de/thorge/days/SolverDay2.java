package de.thorge.days;

import de.thorge.solver.RiddleSolver;
import de.thorge.solver.Solver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolverDay2 extends Solver {

    @Override
    public RiddleSolver<Integer> getFirstRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 8;
            }

            @Override
            public Integer solveRiddle() {
                int maxRed = 12;
                int maxGreen = 13;
                int maxBlue = 14;
                Pattern compile = Pattern.compile("(\\d+) (blue|red|green)");

                return splitLineAndRegexGroups("(Game (\\d+):)|((\\d+ (blue|red|green)(, )?)+);?")
                        .map(strings1 -> {
                            int id = Integer.parseInt(strings1.get(0)[2]);

                            for (int i = 1; i < strings1.size(); i++) {
                                String s = strings1.get(i)[0];
                                Matcher matcher = compile.matcher(s);

                                while (matcher.find()) {
                                    int blue = 0, red = 0, green = 0;

                                    switch(matcher.group(2)){
                                        case "blue" -> blue += Integer.parseInt(matcher.group(1));
                                        case "green" -> green += Integer.parseInt(matcher.group(1));
                                        case "red" -> red += Integer.parseInt(matcher.group(1));
                                    }

                                    if (blue > maxBlue || red > maxRed || green > maxGreen)
                                        return null;
                                }

                            }

                            return id;
                        })
                        .intSum();
            }
        };
    }

    @Override
    public RiddleSolver<Integer> getSecondRiddleSolver() {
        return new RiddleSolver<>() {
            @Override
            public Integer getSolution() {
                return 2286;
            }

            @Override
            public Integer solveRiddle() {
                Pattern compile = Pattern.compile("(\\d+) (blue|red|green)");

                return splitLineAndRegexGroups("(Game (\\d+):)|((\\d+ (blue|red|green)(, )?)+);?")
                        .map(strings1 -> {
                            int maxBlue = 0, maxGreen = 0, maxRed = 0;
                            for (int i = 1; i < strings1.size(); i++) {
                                String s = strings1.get(i)[0];
                                Matcher matcher = compile.matcher(s);


                                while (matcher.find()) {
                                    int blue = 0, green = 0, red = 0;

                                    switch(matcher.group(2)){
                                        case "blue" -> blue += Integer.parseInt(matcher.group(1));
                                        case "green" -> green += Integer.parseInt(matcher.group(1));
                                        case "red" -> red += Integer.parseInt(matcher.group(1));
                                    }

                                    if (maxBlue < blue)
                                        maxBlue = blue;
                                    if (maxGreen < green)
                                        maxGreen = green;
                                    if (maxRed < red)
                                        maxRed = red;
                                }


                            }

                            return maxBlue * maxGreen * maxRed;
                        })
                        .intSum();
            }
        };
    }
    
}


