package ru.tusur.edu.util;

public class EnumUtil {

    private EnumUtil() {
    }

    public static <T extends Enum<T>> T mapToEnumOrDefault(String value, T defaultValue) {
        try {
            return Enum.valueOf(defaultValue.getDeclaringClass(), value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultValue;
        }
    }
}
