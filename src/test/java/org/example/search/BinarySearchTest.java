package org.example.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {

    private static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + ":" + age;
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name, age);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person person = (Person) o;
            return age == person.age && java.util.Objects.equals(name, person.name);
        }
    }

    private List<Person> people;
    private Comparator<Person> byAgeComparator;
    private BinarySearch<Person> binarySearch;

    @BeforeEach
    void setUp() {
        people = new ArrayList<>(Arrays.asList(
                new Person("Alice", 20),
                new Person("Bob", 25),
                new Person("Charlie", 25),
                new Person("David", 30),
                new Person("Eve", 35)
        ));
        byAgeComparator = Comparator.comparingInt(p -> p.age);
        binarySearch = new BinarySearch<>();
    }

    // Базовый поиск по одному полю
    @Test
    void testSearchByOneField_Found() {
        Person target = new Person("AnyName", 30);
        Person result = binarySearch.searchByOneField(people, target, byAgeComparator);
        assertNotNull(result);
        assertEquals("David", result.name);
    }

    @Test
    void testSearchByOneField_NotFound() {
        Person target = new Person("Ghost", 40);
        Person result = binarySearch.searchByOneField(people, target, byAgeComparator);
        assertNull(result);
    }

    // Поиск граничных индексов
    @Test
    void testFindFirstAndLastOccurrence() {
        Person target = new Person("?", 25);
        int first = binarySearch.findFirstOccurance(people, target, byAgeComparator);
        int last = binarySearch.findLastOccurance(people, target, byAgeComparator);
        assertEquals(1, first);
        assertEquals(2, last);
    }

    // Расширенный поиск
    @Test
    void testSearch_FoundExactMatch() {
        Person target = new Person("Charlie", 25);
        Person result = binarySearch.search(people, target, byAgeComparator);
        assertNotNull(result);
        assertEquals("Charlie", result.name);
    }

    @Test
    void testSearch_NotFound() {
        Person target = new Person("Zoe", 99);
        Person result = binarySearch.search(people, target, byAgeComparator);
        assertNull(result);
    }

    // Поиск с использованием hashCode
    @Test
    void testSearch_WithHashCodeEnabled() {
        binarySearch.setUseHashCode(true);
        Person target = new Person("Alice", 20);
        Person result = binarySearch.search(people, target, byAgeComparator);
        assertNotNull(result);
        assertEquals("Alice", result.name);
    }

    // Fluent interface
    @Test
    void testSetUseHashCodeFluent() {
        BinarySearch<Person> sameInstance = binarySearch.setUseHashCode(true);
        assertTrue(binarySearch.isUseHashCode());
        assertSame(binarySearch, sameInstance);
    }

    // Граничные случаи
    // Поиск в пустом списке
    @Test
    void testSearchByOneField_EmptyList() {
        List<Person> emptyList = new ArrayList<>();
        Person target = new Person("Test", 25);
        Person result = binarySearch.searchByOneField(emptyList, target, byAgeComparator);
        assertNull(result);
    }

    // Поиск в списке с одним элементом
    @Test
    void testSearchByOneField_SingleElement() {
        List<Person> singleList = List.of(new Person("Solo", 50));
        Person target = new Person("Any", 50);
        Person result = binarySearch.searchByOneField(singleList, target, byAgeComparator);
        assertNotNull(result);
        assertEquals("Solo", result.name);
    }

    // Поиск в списке с двумя элементами
    @Test
    void testSearchByOneField_TwoElements() {
        List<Person> twoList = Arrays.asList(
                new Person("First", 10),
                new Person("Second", 20)
        );
        twoList.sort(byAgeComparator);

        Person result1 = binarySearch.searchByOneField(twoList, new Person("Any", 10), byAgeComparator);
        Person result2 = binarySearch.searchByOneField(twoList, new Person("Any", 20), byAgeComparator);

        assertEquals("First", result1.name);
        assertEquals("Second", result2.name);
    }

    // Поиск первого вхождения несуществующего элемента
    @Test
    void testFindFirstOccurrence_NotFound() {
        Person target = new Person("Ghost", 99);
        int first = binarySearch.findFirstOccurance(people, target, byAgeComparator);
        assertEquals(-1, first);
    }

    // Поиск последнего вхождения несуществующего элемента
    @Test
    void testFindLastOccurrence_NotFound() {
        Person target = new Person("Ghost", 99);
        int last = binarySearch.findLastOccurance(people, target, byAgeComparator);
        assertEquals(-1, last);
    }

    // Поиск с hashCode для нового объекта с такими же полями
    @Test
    void testSearch_WithHashCodeButDifferentObject() {
        binarySearch.setUseHashCode(true);
        // Новый объект с такими же полями, но другой экземпляр
        Person target = new Person("Alice", 20);
        Person result = binarySearch.search(people, target, byAgeComparator);
        // Должен найти, потому что у нас переопределены equals и hashCode
        assertNotNull(result);
        assertEquals("Alice", result.name);
    }

    // Поиск первого элемента в списке
    @Test
    void testSearch_FirstElement() {
        Person target = new Person("Alice", 20);
        Person result = binarySearch.search(people, target, byAgeComparator);
        assertNotNull(result);
        assertEquals("Alice", result.name);
    }

    // Поиск последнего элемента в списке
    @Test
    void testSearch_LastElement() {
        Person target = new Person("Eve", 35);
        Person result = binarySearch.search(people, target, byAgeComparator);
        assertNotNull(result);
        assertEquals("Eve", result.name);
    }

    // Поиск среднего элемента в списке
    @Test
    void testSearch_MiddleElement() {
        Person target = new Person("Charlie", 25);
        Person result = binarySearch.search(people, target, byAgeComparator);
        assertNotNull(result);
        assertEquals("Charlie", result.name);
    }

    // Тесты производительности
    // Бинарный поиск в большом списке (100,000 элементов)
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void testPerformance_SearchByOneField_LargeList() {
        // Создаем большой список для тестирования производительности
        List<Person> largeList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            largeList.add(new Person("Person" + i, i));
        }
        largeList.sort(byAgeComparator);

        // Ищем элемент в конце списка (худший случай для бинарного поиска)
        Person target = new Person("Test", 99999);
        Person result = binarySearch.searchByOneField(largeList, target, byAgeComparator);

        assertNotNull(result);
        assertEquals(99999, result.age);
    }

    // Расширенный поиск в большом списке с одинаковыми именами
    @Test
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    void testPerformance_Search_LargeList() {
        List<Person> largeList = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            // Создаем объекты с одинаковыми именами, но разными возрастами
            largeList.add(new Person("SamePerson", i));
        }
        largeList.sort(byAgeComparator);

        binarySearch.setUseHashCode(false);
        // Ищем объект, который точно существует
        Person target = new Person("SamePerson", 25000);
        Person result = binarySearch.search(largeList, target, byAgeComparator);

        assertNotNull(result);
        assertEquals(25000, result.age);
        assertEquals("SamePerson", result.name);
    }

    // Поиск первого и последнего вхождения в списке с дубликатами
    @Test
    @Timeout(value = 150, unit = TimeUnit.MILLISECONDS)
    void testPerformance_FindOccurrences_LargeListWithDuplicates() {
        // Создаем список с множеством дубликатов
        List<Person> largeList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            largeList.add(new Person("Duplicate", i / 100)); // Каждое значение повторяется 100 раз
        }
        largeList.sort(byAgeComparator);

        Person target = new Person("Test", 500);
        int first = binarySearch.findFirstOccurance(largeList, target, byAgeComparator);
        int last = binarySearch.findLastOccurance(largeList, target, byAgeComparator);

        assertTrue(first >= 0);
        assertTrue(last >= 0);
        assertTrue(first <= last);
        // Проверяем, что между first и last действительно нужный возраст
        assertEquals(500, largeList.get(first).age);
        assertEquals(500, largeList.get(last).age);
    }

    // Множественные поисковые операции
    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    void testPerformance_MultipleSearches() {
        List<Person> mediumList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            mediumList.add(new Person("User" + i, i));
        }
        mediumList.sort(byAgeComparator);

        // Выполняем несколько поисковых операций
        for (int i = 0; i < 100; i++) {
            Person target = new Person("Search", i * 100);
            Person result = binarySearch.searchByOneField(mediumList, target, byAgeComparator);
            assertNotNull(result);
            assertEquals(i * 100, result.age);
        }
    }

    // Сравнение бинарного поиска с линейным
    @Test
    void testPerformance_CompareWithLinearSearch() {
        // Тест для демонстрации преимущества бинарного поиска
        List<Person> largeList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            largeList.add(new Person("Person" + i, i));
        }
        largeList.sort(byAgeComparator);

        Person target = new Person("Test", 99999);

        long binaryStartTime = System.nanoTime();
        Person binaryResult = binarySearch.searchByOneField(largeList, target, byAgeComparator);
        long binaryEndTime = System.nanoTime();
        long binaryTime = binaryEndTime - binaryStartTime;

        long linearStartTime = System.nanoTime();
        Person linearResult = null;
        for (Person person : largeList) {
            if (byAgeComparator.compare(target, person) == 0) {
                linearResult = person;
                break;
            }
        }
        long linearEndTime = System.nanoTime();
        long linearTime = linearEndTime - linearStartTime;

        assertNotNull(binaryResult);
        assertNotNull(linearResult);
        assertEquals(binaryResult, linearResult);

        System.out.printf("Время бинарного поиска: %d нс%n", binaryTime);
        System.out.printf("Время линейного поиска: %d нс%n", linearTime);
        System.out.printf("Бинарный поиск в %.1f раз быстрее%n", (double) linearTime / binaryTime);

        // Проверяем, что бинарный поиск действительно быстрее
        assertTrue(binaryTime < linearTime,
                "Бинарный поиск должен быть быстрее линейного");
    }
}