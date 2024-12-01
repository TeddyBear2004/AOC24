package de.thorge.solver;

import de.thorge.AdvancedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RiddleSolver<A> {

    private Solver solver;
    private Solver.FileType type;

    public void setSolver(Solver solver) {
        this.solver = solver;
    }

    public void setType(Solver.FileType type) {
        this.type = type;
    }

    public abstract A getSolution();

    public abstract A solveRiddle();

    public String overrideTest() {
        return null;
    }

    public AdvancedList<AdvancedList<String[]>> splitLineAndRegexGroups(String regex) {
        Pattern compile = Pattern.compile(regex);
        return splitLinesAndConvert(s -> {
            Matcher matcher = compile.matcher(s);
            AdvancedList<String[]> matches = new AdvancedList<>();
            while (matcher.find()) {
                String[] strings = new String[matcher.groupCount()];
                for (int i = 1; i < strings.length + 1; i++) {
                    strings[i - 1] = matcher.group(i);
                }
                matches.add(strings);
            }
            return matches;
        });
    }

    public AdvancedList<List<Integer>> splitLineAndSingleInteger() {
        return splitLinesAndConvert(s -> {
            List<Integer> integers = new ArrayList<>();
            for (String s1 : s.split("")) {
                integers.add(Integer.parseInt(s1));
            }
            return integers;
        });
    }

    public AdvancedList<String[]> splitLinesAndWords() {
        return splitLinesAndConvert(s -> s.split(" "));
    }

    public AdvancedList<Integer> splitLinesAndInteger() {
        return splitLinesAndConvert(Integer::parseInt);
    }

    public <C> AdvancedList<C> splitLinesAndConvert(Function<String, C> function) {
        AdvancedList<C> cList = new AdvancedList<>();
        splitLines().forEach(s -> cList.add(function.apply(s)));
        return cList;
    }

    public List<String> splitLines() {
        return split("\n");
    }

    public List<String> split(String regex) {
        String s = overrideTest();

        return Arrays.asList((s == null || type != Solver.FileType.TEST ? solver.get(type) : s).split(regex));
    }


}
