package org.example.Sortings;

import java.util.*;
import java.util.concurrent.*;

public class MergeSort implements SortingStrategy {
    public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new MergeSortTask<>(list, comparator));
    }

    public static class MergeSortTask<T> extends RecursiveTask<List<T>> {
        private final List<T> list;
        private final Comparator<? super T> comparator;

        MergeSortTask(List<T> list, Comparator<? super T> comparator) {
            this.list = list;
            this.comparator = comparator;
        }

        public List<T> compute() {
            if (list.size() <= 1)
                return list;

            int mid = list.size() / 2;

            MergeSortTask<T> leftTask = new MergeSortTask<>(list.subList(0, mid), comparator);
            MergeSortTask<T> rightTask = new MergeSortTask<>(list.subList(mid, list.size()), comparator);

            leftTask.fork();
            List<T> rightResult = rightTask.fork().join();
            List<T> leftResult = leftTask.join();

            return merge(leftResult, rightResult, comparator);
        }
    }

    public static <T> List<T> merge(List<T> left, List<T> right, Comparator<? super T> comparator) {
        List<T> result = new ArrayList<>(left.size() + right.size());

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
