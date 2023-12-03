package de.thorge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class AdvancedList<C> extends ArrayList<C> {

    public AdvancedList() {
        super();
    }

    public AdvancedList(List<? extends C> list) {
        super(list);
    }

    public <E> E reduce(BiFunction<C, E, E> biFunction, E startValue) {

        for (C c : this) {
            startValue = biFunction.apply(c, startValue);
        }

        return startValue;
    }

    public Optional<C> getOptional(int index) {
        if (index < 0 || index >= size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(super.get(index));
    }

    public <E> AdvancedList<E> map(Function<? super C, ? extends E> mapper) {
        AdvancedList<E> newList = new AdvancedList<>();
        for (C c : this) {
            E apply = mapper.apply(c);
            if (apply != null)
                newList.add(apply);
        }
        return newList;
    }

    public AdvancedList<C> filter(Predicate<C> predicate) {
        removeIf(c -> !predicate.test(c));
        return this;
    }

    @SuppressWarnings("unchecked")
    public AdvancedList<C> sortSmallToBig() {
        if (!isEmpty() && get(0) instanceof Comparable) {
            sort((Comparator<? super C>) Comparator.naturalOrder());
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public AdvancedList<C> sortBigToSmall() {
        if (!isEmpty() && get(0) instanceof Comparable) {
            sort((Comparator<? super C>) Comparator.naturalOrder().reversed());
        }
        return this;
    }

    public AdvancedList<C> first(int n) {
        removeRange(n, size());
        return this;
    }

    public AdvancedList<C> last(int n) {
        removeRange(0, size() - n);
        return this;
    }

    public int intSum() {
        int sum = 0;

        for (C c : this) {
            if (c instanceof Number number)
                sum += number.intValue();
            else throw new IllegalArgumentException("Type is not a number.");
        }

        return sum;
    }

    public double doubleSum() {
        double sum = 0;

        for (C c : this) {
            if (c instanceof Number number)
                sum += number.doubleValue();
            else throw new IllegalArgumentException("Type is not a number.");
        }

        return sum;
    }

    public long longSum() {
        long sum = 0;

        for (C c : this) {
            if (c instanceof Number number)
                sum += number.longValue();
            else throw new IllegalArgumentException("Type is not a number.");
        }

        return sum;
    }

}
