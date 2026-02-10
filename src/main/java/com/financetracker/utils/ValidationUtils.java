package com.financetracker.utils;

import java.util.regex.Pattern;

/**
 * Утилиты для валидации данных
 */
public class ValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*\\d).+$"
    );
    
    /**
     * Проверка корректности email
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Проверка надежности пароля (минимум буквы и цифры)
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
    
    /**
     * Проверка что строка не пустая
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Проверка минимальной длины строки
     */
    public static boolean hasMinLength(String value, int minLength) {
        return value != null && value.length() >= minLength;
    }
    
    /**
     * Проверка максимальной длины строки
     */
    public static boolean hasMaxLength(String value, int maxLength) {
        return value != null && value.length() <= maxLength;
    }
    
    /**
     * Проверка что число положительное
     */
    public static boolean isPositiveNumber(String value) {
        try {
            double number = Double.parseDouble(value);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
