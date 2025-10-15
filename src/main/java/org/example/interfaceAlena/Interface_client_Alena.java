package org.example.interfaceAlena;

import java.util.*;
import java.io.*;
import java.nio.file.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.example.Main;
import org.example.objects.*;

public class Interface_client_Alena {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Product> productList = new ArrayList<>();
    private static Gson gson = new Gson();
    private static String productOfCollection;

    public static String getProductOfCollection() {
        return productOfCollection;
    }

    public static List<Product> startClientInteraction() {
        System.out.println("\nДобро пожаловать в систему управления коллекцией товаров!");

        // Шаг 1: Запрос размера коллекции
        int collectionSize = getCollectionSize();

        // Шаг 2: Выбор способа заполнения
        int choice = getFillingMethod();

        // Шаг 3: Заполнение коллекции
        switch (choice) {
            case 1:
                fillFromFile(collectionSize);
                break;
            case 2:
                fillRandomly(collectionSize);
                break;
            case 3:
                fillManually(collectionSize);
                break;
        }

        // Вывод итоговой коллекции
        System.out.println("\nИтоговая коллекция");
        System.out.println("Размер коллекции: " + productList.size());
        for (Product product : productList) {
            System.out.println(product);
        }

        return productList;
    }

    private static int getCollectionSize() {
        while (true) {
            try {
                System.out.print("Введите размер итоговой коллекции: ");
                int size = Integer.parseInt(scanner.nextLine());
                if (size > 0) {
                    return size;
                } else {
                    System.out.println("Размер должен быть положительным числом!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число!");
            }
        }
    }

    private static int getFillingMethod() {
        while (true) {
            System.out.println("\nВыберите способ заполнения коллекции:");
            System.out.println("1. Из JSON-файла");
            System.out.println("2. Рандомно");
            System.out.println("3. Вручную в консоли");
            System.out.print("Ваш выбор: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 3) {
                    return choice;
                } else {
                    System.out.println("Пожалуйста, выберите число от 1 до 3!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число!");
            }
        }
    }

    private static void fillFromFile(int collectionSize) {
        System.out.println("\nЗаполнение из файла");

        while (true) {
            System.out.print("Введите путь до JSON-файла: ");
            String filePath = scanner.nextLine();

            try {
                // Проверка существования файла
                if (!Files.exists(Paths.get(filePath))) {
                    System.out.println("Файл не существует!");
                    continue;
                }

                // Проверка расширения файла
                if (!filePath.toLowerCase().endsWith(".json")) {
                    System.out.println("Файл должен быть в формате JSON!");
                    continue;
                }

                // Чтение и валидация файла
                String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
                if (validateJsonFile(jsonContent, collectionSize)) {
                    System.out.println("Файл успешно загружен и проверен!");
                    break;
                }

            } catch (IOException e) {
                System.out.println("Ошибка чтения файла: " + e.getMessage());
            } catch (JsonSyntaxException e) {
                System.out.println("Неверный формат JSON в файле!");
            }
        }
    }

    private static boolean validateJsonFile(String jsonContent, int expectedSize) {
        try {
            JsonArray jsonArray = gson.fromJson(jsonContent, JsonArray.class);

            if (jsonArray == null) {
                System.out.println("Файл не содержит валидный JSON массив!");
                return false;
            }

            if (jsonArray.size() != expectedSize) {
                System.out.println("Количество товаров в файле (" + jsonArray.size() +
                        ") не соответствует ожидаемому (" + expectedSize + ")");
                return false;
            }

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();

                // Проверка обязательных полей
                if (!obj.has("type") || !obj.has("id") || !obj.has("price") || !obj.has("name")) {
                    System.out.println("Не все обязательные поля присутствуют!");
                    return false;
                }

                String type = obj.get("type").getAsString();
                if (!Arrays.asList("Book", "Phone", "Disc", "Doshirak").contains(type)) {
                    System.out.println("Неизвестный тип товара: " + type);
                    return false;
                }
            }

            // Если все проверки пройдены, добавляем товары в коллекцию
            productList.clear();
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                addProductFromJson(obj);
            }

            return true;

        } catch (Exception e) {
            System.out.println("Ошибка валидации JSON: " + e.getMessage());
            return false;
        }
    }

    private static void addProductFromJson(JsonObject obj) {
        String type = obj.get("type").getAsString();
        int id = obj.get("id").getAsInt();
        double price = obj.get("price").getAsDouble();
        String name = obj.get("name").getAsString();

        switch (type) {
            case "Book":
                String genre = obj.get("genre").getAsString();
                productList.add(new Book(id, price, name, genre));
                break;
            case "Phone":
                String color = obj.get("color").getAsString();
                productList.add(new Phone(id, price, name, color));
                break;
            case "Disc":
                int year = obj.get("year").getAsInt();
                productList.add(new Disc(id, price, name, year));
                break;
            case "Doshirak":
                String taste = obj.get("taste").getAsString();
                productList.add(new Doshirak(id, price, name, taste));
                break;
        }
    }

    private static void fillRandomly(int collectionSize) {
        System.out.println("\nЗаполнение рандомными товарами");

        // Создаем заранее подготовленную коллекцию товаров
        List<Product> allProducts = new ArrayList<>();

        // Дошираки
        allProducts.add(new Doshirak(1, 53.10, "Доширак куриный", "Курочка"));
        allProducts.add(new Doshirak(2, 55.15, "Доширак грибной", "Грибной"));
        allProducts.add(new Doshirak(3, 56.50, "Доширак сюрприз", "Ушная сера"));
        allProducts.add(new Doshirak(4, 56.50, "Доширак Чачжан мён", "Острая свинка"));
        allProducts.add(new Doshirak(5, 149.99, "Доширак для богатых", "Нефть"));
        allProducts.add(new Doshirak(6, 49.99, "Доширак Классик", "Обалденный"));
        allProducts.add(new Doshirak(7, 43.99, "Доширак Пибиммен", "Кисло-сладкий"));

        // Телефоны
        allProducts.add(new Phone(8, 15000.05, "iPhone 9", "Черный"));
        allProducts.add(new Phone(9, 22000.10, "iPhone 10", "Белый"));
        allProducts.add(new Phone(10, 31000.00, "iPhone 11", "Золотой"));
        allProducts.add(new Phone(11, 40569.00, "iPhone 12", "Красный"));
        allProducts.add(new Phone(12, 45000.50, "iPhone 13", "Зеленый"));
        allProducts.add(new Phone(13, 47000.50, "iPhone 14", "Синий"));
        allProducts.add(new Phone(14, 49000.40, "iPhone 15", "Серый"));
        allProducts.add(new Phone(15, 54000.50, "iPhone 16", "Оранжевый"));
        allProducts.add(new Phone(16, 150000.99, "iPhone 17", "Охра"));

        // Книги
        allProducts.add(new Book(17, 534.50, "Задача трёх тел", "Научная фантастика"));
        allProducts.add(new Book(18, 1245.50, "Шум и ярость", "Южноготический роман"));
        allProducts.add(new Book(19, 2358.00, "Илиада", "Эпос"));
        allProducts.add(new Book(20, 367.50, "Вакханки", "Древнегреческая трагедия"));
        allProducts.add(new Book(21, 175.90, "Радость кипячения воды", "Кулинария"));
        allProducts.add(new Book(22, 1900.69, "Как защитить свой курятник от гоблинов", "Фантастика"));
        allProducts.add(new Book(23, 328.89, "Готовим с какашкой", "Кулинария"));
        allProducts.add(new Book(24, 1045.54, "Управляем стоматологической клиникой по-чингисхановски", "Менеджмент"));
        allProducts.add(new Book(25, 521.69, "Материалы Второй международной конференции по голым мышам", "Медицинское исследование"));

        // Диски
        allProducts.add(new Disc(26, 333.33, "Зеленый слоник", 1999));
        allProducts.add(new Disc(27, 456.50, "Нападение помидоров-убийц", 1978));
        allProducts.add(new Disc(28, 1342.33, "Твин Пикс", 2017));
        allProducts.add(new Disc(29, 828.38, "Идиоты", 1998));
        allProducts.add(new Disc(30, 353.00, "Голова-ластик", 1977));
        allProducts.add(new Disc(31, 100.33, "Клоуны-убийцы из космоса", 1988));
        allProducts.add(new Disc(32, 193.33, "Бобры-зомби", 2014));
        allProducts.add(new Disc(33, 127.50, "Пффффт", 1954));
        allProducts.add(new Disc(34, 321.00, "Идиократия", 2006));
        allProducts.add(new Disc(35, 667.50, "Суспирия", 2018));

        // Очищаем текущую коллекцию
        productList.clear();

        // Если запрошенный размер больше чем у нас товаров - используем все товары
        if (collectionSize >= allProducts.size()) {
            productList.addAll(allProducts);
            System.out.println("\nВся коллекция товаров");
        } else {
            // Выбираем случайные товары из всей коллекции
            Random random = new Random();
            Set<Integer> selectedIndices = new HashSet<>();
            while (selectedIndices.size() < collectionSize) {
                selectedIndices.add(random.nextInt(allProducts.size()));
            }

            for (int index : selectedIndices) {
                productList.add(allProducts.get(index));
            }
            System.out.println("\nСлучайная выборка из " + collectionSize + " товаров");
        }

        // Выводим все товары в коллекции
        for (Product product : productList) {
            System.out.println(product);
        }

        System.out.println("\nКоллекция заполнена! Всего товаров: " + productList.size());
    }

    private static void fillManually(int collectionSize) {
        System.out.println("\nЗаполнение вручную");

        productList.clear();

        // Выбор типа товара
        int productType = chooseProductType();

        switch (productType) {
            case 1:
                productOfCollection = "Book";
                break;
            case 2:
                productOfCollection = "Phone";
                break;
            case 3:
                productOfCollection = "Disc";
                break;
            case 4:
                productOfCollection = "Doshirak";
                break;
        }

        for (int i = 0; i < collectionSize; i++) {
            System.out.println("\nДобавление товара " + (i + 1) + " из " + collectionSize + " ---");

            // Ввод общих полей
            int id = i + 1;
            double price = getValidatedDouble("Введите цену товара: ");
            String name = getValidatedString("Введите название товара: ");

            // Ввод специфических полей и создание товара
            switch (productType) {
                case 1:
                    String genre = getValidatedString("Введите жанр книги: ");
                    productList.add(new Book(id, price, name, genre));
                    break;
                case 2:
                    String color = getValidatedString("Введите цвет телефона: ");
                    productList.add(new Phone(id, price, name, color));
                    break;
                case 3:
                    int year = getValidatedYear("Введите год выпуска диска: ");
                    productList.add(new Disc(id, price, name, year));
                    break;
                case 4:
                    String taste = getValidatedString("Введите вкус дошика: ");
                    productList.add(new Doshirak(id, price, name, taste));
                    break;
            }

            System.out.println("Товар успешно добавлен!");
        }
    }

    private static int chooseProductType() {

        System.out.println("Выберите тип товара:");
        System.out.println("1. Книга");
        System.out.println("2. Телефон");
        System.out.println("3. DVD-Диск");
        System.out.println("4. Доширак");
        System.out.print("Ваш выбор: ");


        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Пожалуйста, выберите число от 1 до 4!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число!");
            }
        }
    }

    private static String getValidatedString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Поле не может быть пустым!");
        }
    }

    private static double getValidatedDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value > 0) {
                    return Math.round(value * 100.0) / 100.0;
                } else {
                    System.out.println("Цена должна быть положительной!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число!");
            }
        }
    }

    private static int getValidatedYear(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int year = Integer.parseInt(scanner.nextLine());
                if (year >= 1900 && year <= 2024) {
                    return year;
                } else {
                    System.out.println("Год должен быть между 1900 и 2024!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число!");
            }
        }
    }
}