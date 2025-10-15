package org.example;

import org.example.Sortings.InsertionSort;
import org.example.Sortings.MergeSort;
import org.example.Sortings.QuickSort;
import org.example.Sortings.SortingStrategy;
import org.example.builders.*;
import org.example.objects.Book;
import org.example.objects.Phone;
import org.example.objects.Product;
import org.example.search.BinarySearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static org.example.interfaceAlena.Interface_client_Alena.getProductOfCollection;
import static org.example.interfaceAlena.Interface_client_Alena.startClientInteraction;

public class Main {

    private static SortingStrategy strategy;
    private static int comparatorField;
    private static List<Product> products = new ArrayList<>();

    private static boolean isUserWantsToContinue = true;

    public static void main(String[] args) {
        while (isUserWantsToContinue) {
            products = startClientInteraction();

            comparatorField = choiceStrategy();

            products = strategy.sort(products, getComparator(comparatorField));

            System.out.println("\nОтсортированная коллекция: \n" + products.toString());

            Product found = getTargetProduct(products, getComparator(comparatorField));

            if (found != null) {
                System.out.println("\nНайден продукт: \n" + found.toString());
            } else {
                System.out.println("\nПродукт не найден.");
            }

            if(getExitChoice() == 0){
                isUserWantsToContinue = false;
            }
        }

    }

    private static int choiceStrategy(){
        System.out.println("\nВыберите поле для сортировки и последующего поиска:");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. ExtraField (В зависимости от ранее выбранного типа товара)");
        System.out.print("Введите цифру соответствующую выбранному полю для сортировки и поиска: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                strategy = new QuickSort();
                break;
            case 2:
                strategy = new MergeSort();
                break;
            case 3:
                strategy = new InsertionSort();
                break;
            default:
                System.out.println("Некорректные данные...");
                break;
        }

        return choice;
    }

    private static Comparator<Product> getComparator(int field) {
        switch (field) {
            case 1: // Name
                return Comparator.comparing(Product::getName);
            case 2: // Price
                return Comparator.comparingDouble(Product::getPrice);
            case 3: // Дополнительные поля в зависимости от типа товара
                return Comparator.comparing(Product::getExtraField);
            default:
                throw new IllegalArgumentException("Некорректное поле для сортировки");
        }
    }

    private static Product getTargetProduct(List<Product> products, Comparator comparator) {
        Scanner scanner = new Scanner(System.in);
        ProductBuilder target;

        switch (getProductOfCollection()){
            case "Book":
                target = new BookBuilder();
                break;
            case "Phone":
                target = new PhoneBuilder();
                break;
            case "Disc":
                target = new DiscBuilder();
                break;
            case "Doshirak":
                target = new DoshirakBuilder();
                break;
            default:
                target = null;
                break;
        }

        System.out.println("\nВыберите вариант для поиска продукта:");
        System.out.println("1. Поиск по выбранному ранее полю для сортировки");
        System.out.println("2. Поиск по всем полям продукта");
        System.out.print("Введите цифру (1 или 2): ");
        int choice = scanner.nextInt();

        scanner.nextLine(); // очищаем буфер

        if (choice == 1) {
            // Инициализируем только поле, выбранное ранее для сортировки
            switch (comparatorField) {
                case 1: // Name
                    System.out.print("Введите имя продукта: ");
                    target.setName(scanner.nextLine());
                    break;
                case 2: // Price
                    System.out.print("Введите цену продукта: ");
                    target.setPrice(scanner.nextDouble());
                    scanner.nextLine(); // очищаем буфер
                    break;
                case 3: // ExtraField
                    System.out.print("Введите значение дополнительного поля: ");
                    target.setExtraField(scanner.nextLine());
                    break;
                default:
                    System.out.println("Некорректный выбор поля для поиска.");
            }
        } else if (choice == 2) {
            // Инициализируем все поля продукта
            System.out.print("Введите имя продукта: ");
            target.setName(scanner.nextLine());

            System.out.print("Введите цену продукта: ");
            target.setPrice(scanner.nextDouble());
            scanner.nextLine(); // очищаем буфер

            System.out.print("Введите дополнительное поле (color, taste, year, genre и т.д.): ");
            target.setExtraField(scanner.nextLine());
        } else {
            System.out.println("Некорректный выбор. Будет возвращён пустой объект.");
        }

        BinarySearch binarySearch = new BinarySearch();

        if(choice == 1){
            return (Product) binarySearch.searchByOneField(products, target.build(), comparator);
        } else if (choice == 2) {
            return (Product) binarySearch.search(products, target.build(), comparator);
        }

        return null;
    }

    private static int getExitChoice(){
        while (true) {
            try {
                System.out.print("\nВведите 1 если хотите продолжить, и 0, если хотите выйти из программы: ");
                Scanner scanner = new Scanner(System.in);
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0 || choice == 1) {
                    return choice;
                } else {
                    System.out.println("Введите 1, если хотите продолжить и 0, если хотите выйти из программы!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите 1 или 0!");
            }
        }
    }

}