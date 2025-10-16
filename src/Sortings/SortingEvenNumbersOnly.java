package Sortings;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class SortingEvenNumbersOnly implements SortingStrategy {
    private final SortingStrategy baseStrategy;

    public SortingEvenNumbersOnly(SortingStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    public <T> List<T> sort(List<T> list, Comparator<? super T> comparator, ToIntFunction<? super T> fieldExtractor) {

        List<T> original = new ArrayList<>(list);

        List<T> evens = list.stream()
                .filter(e -> fieldExtractor.applyAsInt(e) % 2 == 0)
                .collect(Collectors.toList());

        List<T> sortedEvens = baseStrategy.sort(evens, comparator);

        List<T> result = new ArrayList<>(original.size());
        Iterator<T> evensIterator = sortedEvens.iterator();

        for (T item : original) {
            if (fieldExtractor.applyAsInt(item) % 2 == 0) {
                result.add(evensIterator.next());
            } else {
                result.add(item);
            }
        }

        return result;
    }

    @Override
    public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        throw new UnsupportedOperationException();
    }
}
