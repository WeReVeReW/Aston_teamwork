package Sortings;

import java.util.*;
import java.util.concurrent.*;

public class InsertionSort implements SortingStrategy {
    public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {

        // Делим массив на две части и сортируем их параллельно в двух потоках.

        int mid = list.size() / 2;
        List<T> left = new ArrayList<>(list.subList(0, mid));
        List<T> right = new ArrayList<>(list.subList(mid, list.size()));

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<List<T>> leftFuture = executor.submit(() -> {
            insertionSort(left, comparator);
            return left;
        });

        Future<List<T>> rightFuture = executor.submit(() -> {
            insertionSort(right, comparator);
            return right;
        });

        List<T> sortedLeft = null;
        List<T> sortedRight = null;
        try {
            sortedLeft = leftFuture.get();
            sortedRight = rightFuture.get();
        } catch (InterruptedException | ExecutionException e) {}

        executor.shutdown();

        return merge(sortedLeft, sortedRight, comparator);
    }

    public static <T> void insertionSort(List<T> list, Comparator<? super T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);

            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }
    }

    public static <T> List<T> merge(List<T> left, List<T> right, Comparator<? super T> comparator) {
        List<T> result = new ArrayList<>();

        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size())
            result.add(left.get(i++));
        while (j < right.size())
            result.add(right.get(j++));

        return result;
    }
}
