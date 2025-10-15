package Sortings;

import java.util.*;
import java.util.concurrent.*;

public class QuickSort implements SortingStrategy {
    public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new QuickSortTask<>(list, comparator));
    }

    public static class QuickSortTask<T> extends RecursiveTask<List<T>> {
        private final List<T> list;
        private final Comparator<? super T> comparator;

        QuickSortTask(List<T> list, Comparator<? super T> comparator) {
            this.list = list;
            this.comparator = comparator;
        }

        public List<T> compute() {
            if (list.size() <= 1)
                return list;

            T pivot = list.get(0);
            List<T> left = new ArrayList<>();
            List<T> right = new ArrayList<>();

            for (int i = 1; i < list.size(); i++) {
                T element = list.get(i);
                if (comparator.compare(element, pivot) <= 0)
                    left.add(element);
                else
                    right.add(element);
            }

            QuickSortTask<T> leftTask = new QuickSortTask<>(left, comparator);
            QuickSortTask<T> rightTask = new QuickSortTask<>(right, comparator);

            leftTask.fork();
            List<T> rightResult = rightTask.compute();
            List<T> leftResult = leftTask.join();

            List<T> result = new ArrayList<>(leftResult.size() + 1 + rightResult.size());
            result.addAll(leftResult);
            result.add(pivot);
            result.addAll(rightResult);
            return result;
        }
    }
}
