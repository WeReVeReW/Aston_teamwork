package org.example.Sortings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    private final InsertionSort insertionSort = new InsertionSort();

    // Базовая проверка сортировки чисел
    @Test
    void testSort_IntegerList() {
        List<Integer> list = Arrays.asList(5, 2, 8, 1, 9);
        List<Integer> sorted = insertionSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 5, 8, 9), sorted);
    }

    // Сортировка строк в лексикографическом порядке
    @Test
    void testSort_StringList() {
        List<String> list = Arrays.asList("banana", "apple", "cherry");
        List<String> sorted = insertionSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList("apple", "banana", "cherry"), sorted);
    }

    // Обработка пустого списка
    @Test
    void testSort_EmptyList() {
        List<Integer> list = Arrays.asList();
        List<Integer> sorted = insertionSort.sort(list, Comparator.naturalOrder());
        assertTrue(sorted.isEmpty());
    }

    // Список с одним элементом
    @Test
    void testSort_SingleElement() {
        List<Integer> list = Arrays.asList(42);
        List<Integer> sorted = insertionSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList(42), sorted);
    }

    // Сортировка по убыванию
    @Test
    void testSort_ReverseOrder() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> sorted = insertionSort.sort(list, Comparator.reverseOrder());
        assertEquals(Arrays.asList(5, 4, 3, 2, 1), sorted);
    }

    // Прямой вызов алгоритма сортировки вставками
    @Test
    void testInsertionSort_DirectCall() {
        List<Integer> list = Arrays.asList(5, 2, 8, 1, 9);
        InsertionSort.insertionSort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 5, 8, 9), list);
    }

    // Проверка алгоритма слияния
    @Test
    void testMerge_DirectCall() {
        List<Integer> left = Arrays.asList(1, 3, 5);
        List<Integer> right = Arrays.asList(2, 4, 6);
        List<Integer> merged = InsertionSort.merge(left, right, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), merged);
    }

    // Обработка дублирующихся значений
    @Test
    void testSort_DuplicateValues() {
        List<Integer> list = Arrays.asList(3, 1, 3, 2, 1);
        List<Integer> sorted = insertionSort.sort(list, Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 1, 2, 3, 3), sorted);
    }

    // Тесты производительности

    // Сортировка среднего списка (1000 элементов)
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testPerformance_MediumList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1000; i > 0; i--) {
            list.add(i); // Обратный порядок для худшего случая
        }

        List<Integer> sorted = insertionSort.sort(list, Comparator.naturalOrder());

        assertEquals(1000, sorted.size());
        // Проверяем корректность сортировки
        for (int i = 0; i < 999; i++) {
            assertTrue(sorted.get(i) <= sorted.get(i + 1));
        }
    }

    // Сортировка почти отсортированного списка
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void testPerformance_AlmostSorted() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            list.add(i);
        }
        // Небольшие нарушения порядка
        list.set(100, 150);
        list.set(200, 250);

        List<Integer> sorted = insertionSort.sort(list, Comparator.naturalOrder());

        assertEquals(500, sorted.size());
        for (int i = 0; i < 499; i++) {
            assertTrue(sorted.get(i) <= sorted.get(i + 1));
        }
    }

    // Сравнение лучшего и худшего случаев
    @Test
    void testPerformance_BestVsWorstCase() {
        int size = 1000;

        // Лучший случай - уже отсортированный список
        List<Integer> bestCase = new ArrayList<>();
        for (int i = 0; i < size; i++) bestCase.add(i);

        // Худший случай - обратный порядок
        List<Integer> worstCase = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) worstCase.add(i);

        long bestTime = measureSortTime(bestCase);
        long worstTime = measureSortTime(worstCase);

        System.out.printf("Лучший случай: %d нс%n", bestTime);
        System.out.printf("Худший случай: %d нс%n", worstTime);

        assertTrue(worstTime > bestTime, "Худший случай должен быть медленнее чем лучший случай");
    }

    private long measureSortTime(List<Integer> list) {
        long start = System.nanoTime();
        insertionSort.sort(new ArrayList<>(list), Comparator.naturalOrder());
        return System.nanoTime() - start;
    }
}
