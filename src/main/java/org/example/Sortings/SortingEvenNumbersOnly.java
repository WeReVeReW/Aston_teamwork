package org.example.Sortings;

import org.example.objects.Product;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class SortingEvenNumbersOnly implements SortingStrategy {
    private final SortingStrategy baseStrategy;

    public SortingEvenNumbersOnly(SortingStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    @Override
    public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        List<T> original = new ArrayList<>(list);

        // Извлекаем чётные элементы по числовому полю
        List<T> evens = list.stream()
                .filter(e -> isEvenNumericField(e))
                .collect(Collectors.toList());

        // Сортируем только чётные элементы
        List<T> sortedEvens = baseStrategy.sort(evens, comparator);

        List<T> result = new ArrayList<>(original.size());
        Iterator<T> evensIterator = sortedEvens.iterator();

        for (T item : original) {
            if (isEvenNumericField(item)) {
                result.add(evensIterator.next());
            } else {
                result.add(item);
            }
        }

        return result;
    }

    private boolean isEvenNumericField(Object obj) {
        if (obj instanceof Product p) {
            return ((int) p.getPrice()) % 2 == 0;
        }
        return false;
    }
}
