package org.example.Sortings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    private final MergeSort mergeSort = new MergeSort();

    // Базовая проверка сортировки чисел
    @Test
    void testSort_IntegerList() {
        List<Integer> list = Arrays.asList(5, 2, 8, 1, 9);
        List<Integer> sorted = mergeSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 5, 8, 9), sorted);
    }

    // Сортировка строк в лексикографическом порядке
    @Test
    void testSort_StringList() {
        List<String> list = Arrays.asList("banana", "apple", "cherry");
        List<String> sorted = mergeSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList("apple", "banana", "cherry"), sorted);
    }

    // Обработка пустого списка
    @Test
    void testSort_EmptyList() {
        List<Integer> list = Arrays.asList();
        List<Integer> sorted = mergeSort.sort(list, Comparator.naturalOrder());
        assertTrue(sorted.isEmpty());
    }

    // Список с одним элементом
    @Test
    void testSort_SingleElement() {
        List<Integer> list = Arrays.asList(42);
        List<Integer> sorted = mergeSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList(42), sorted);
    }

    // Сортировка по убыванию
    @Test
    void testSort_ReverseOrder() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> sorted = mergeSort.sort(list, Comparator.reverseOrder());
        assertEquals(Arrays.asList(5, 4, 3, 2, 1), sorted);
    }

    // Прямой вызов алгоритма слияния
    @Test
    void testMerge_DirectCall() {
        List<Integer> left = Arrays.asList(1, 3, 5);
        List<Integer> right = Arrays.asList(2, 4, 6);
        List<Integer> merged = MergeSort.merge(left, right, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), merged);
    }

    // Обработка дублирующихся значений
    @Test
    void testSort_DuplicateValues() {
        List<Integer> list = Arrays.asList(3, 1, 3, 2, 1);
        List<Integer> sorted = mergeSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 1, 2, 3, 3), sorted);
    }

    // Слияние пустых списков
    @Test
    void testMerge_EmptyLists() {
        List<Integer> left = Arrays.asList();
        List<Integer> right = Arrays.asList(1, 2, 3);
        List<Integer> merged = MergeSort.merge(left, right, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 3), merged);

        List<Integer> left2 = Arrays.asList(1, 2, 3);
        List<Integer> right2 = Arrays.asList();
        List<Integer> merged2 = MergeSort.merge(left2, right2, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 3), merged2);
    }

    // Тесты производительности

    // Сортировка большого списка
    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void testPerformance_LargeList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 10000; i > 0; i--) {
            list.add(i); // Обратный порядок
        }

        List<Integer> sorted = mergeSort.sort(list, Comparator.naturalOrder());

        assertEquals(10000, sorted.size());
        // Проверяем корректность сортировки
        for (int i = 0; i < 9999; i++) {
            assertTrue(sorted.get(i) <= sorted.get(i + 1));
        }
    }

    // Сортировка почти отсортированного списка
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testPerformance_AlmostSorted() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            list.add(i);
        }
        // Небольшие нарушения порядка
        list.set(1000, 1500);
        list.set(2000, 2500);
        list.set(3000, 3500);

        List<Integer> sorted = mergeSort.sort(list, Comparator.naturalOrder());

        assertEquals(5000, sorted.size());
        for (int i = 0; i < 4999; i++) {
            assertTrue(sorted.get(i) <= sorted.get(i + 1));
        }
    }
    /*
    // Сравнение параллельного и однопоточного подхода
    @Test
    void testPerformance_ParallelVsSequential() {
        // Создаем большой список для лучшего распараллеливания
        List<Integer> list = new ArrayList<>();
        int size = 1000000; // 1 миллион элементов
        for (int i = 0; i < size; i++) {
            list.add((int) (Math.random() * size)); // Случайные данные
        }

        // Замер времени параллельной версии
        long parallelStart = System.nanoTime();
        List<Integer> parallelResult = mergeSort.sort(new ArrayList<>(list), Comparator.naturalOrder());
        long parallelEnd = System.nanoTime();
        long parallelTime = parallelEnd - parallelStart;

        // Замер времени однопоточной версии
        long sequentialStart = System.nanoTime();
        List<Integer> sequentialResult = sequentialMergeSort(new ArrayList<>(list), Comparator.naturalOrder());
        long sequentialEnd = System.nanoTime();
        long sequentialTime = sequentialEnd - sequentialStart;

        // Вывод результатов
        System.out.printf("Параллельная: %,d нс%n", parallelTime);
        System.out.printf("Однопоточная: %,d нс%n", sequentialTime);

        // Проверяем корректность результатов
        assertEquals(sequentialResult, parallelResult, "Результаты должны быть одинаковыми");
        assertEquals(size, parallelResult.size());

        // Проверяем, что параллельная версия быстрее
        assertTrue(parallelTime < sequentialTime,
                String.format("Параллельная должна быть быстрее. Параллельная: %d ns, Однопоточная: %d ns",
                        parallelTime, sequentialTime));
    }

    // Оптимизированная однопоточная версия для сравнения
    private <T> List<T> sequentialMergeSort(List<T> list, Comparator<? super T> comparator) {
        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }

        int mid = list.size() / 2;
        List<T> left = sequentialMergeSort(list.subList(0, mid), comparator);
        List<T> right = sequentialMergeSort(list.subList(mid, list.size()), comparator);

        return MergeSort.merge(left, right, comparator);
    }

    private long measureTime(Runnable task) {
        long start = System.nanoTime();
        task.run();
        return System.nanoTime() - start;
    }
    */
}
