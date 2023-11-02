package ru.tusur.edu.type.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskThemeType {
    // Категория: Сортировка (CATEGORY_SORTING)
    THEME_SORTING_ARRAYS("CATEGORY_SORTING", "Сортировка массивов целых чисел"),
    THEME_SORTING_STRINGS("CATEGORY_SORTING", "Сортировка строк в алфавитном порядке"),
    THEME_SORTING_CUSTOM("CATEGORY_SORTING", "Сортировка объектов по пользовательскому критерию"),

    // Категория: Рекурсия (CATEGORY_RECURSION)
    THEME_RECURSION_FACTORIAL("CATEGORY_RECURSION", "Рекурсивный поиск факториала числа"),
    THEME_RECURSION_FIBONACCI("CATEGORY_RECURSION", "Рекурсивный алгоритм вычисления чисел Фибоначчи"),
    THEME_RECURSION_TREE_TRAVERSAL("CATEGORY_RECURSION", "Рекурсивный обход дерева"),

    // Категория: Оптимизация (CATEGORY_OPTIMIZATION)
    THEME_OPTIMIZATION_CACHE("CATEGORY_OPTIMIZATION", "Оптимизация вычислений с использованием кэша"),
    THEME_OPTIMIZATION_DATABASE("CATEGORY_OPTIMIZATION", "Оптимизация работы с базой данных"),
    THEME_OPTIMIZATION_ALGORITHMS("CATEGORY_OPTIMIZATION", "Оптимизация алгоритмов сортировки"),

    // Категория: Массивы (CATEGORY_ARRAYS)
    THEME_ARRAYS_MIN_MAX("CATEGORY_ARRAYS", "Поиск максимального и минимального элементов в массиве"),
    THEME_ARRAYS_2D("CATEGORY_ARRAYS", "Работа с двумерными массивами"),
    THEME_ARRAYS_DYNAMIC("CATEGORY_ARRAYS", "Динамические массивы и списки"),

    // Категория: Кодирование (CATEGORY_ENCODING)
    THEME_ENCODING_BASE64("CATEGORY_ENCODING", "Кодирование и декодирование текста в Base64"),
    THEME_ENCODING_AES("CATEGORY_ENCODING", "Шифрование и дешифрование данных с использованием алгоритма AES"),
    THEME_ENCODING_URL("CATEGORY_ENCODING", "Кодирование и декодирование URL"),

    // Категория: Неизвестная (CATEGORY_UNKNOWN)
    THEME_UNKNOWN("CATEGORY_UNKNOWN", "Неизвестная тематика задачи");

    private final String category;
    private final String displayName;
}
