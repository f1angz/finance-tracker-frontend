package com.financetracker.services;

import com.financetracker.models.User;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * Сервис аутентификации
 * Сейчас использует mock данные, в будущем будет работать с REST API
 */
public class AuthService {
    
    private static AuthService instance;
    private final Preferences preferences;
    
    // Mock база данных пользователей
    private final Map<String, User> users = new HashMap<>();
    
    // Текущий авторизованный пользователь
    private User currentUser;
    
    private static final String PREF_REMEMBERED_EMAIL = "remembered_email";
    
    private AuthService() {
        this.preferences = Preferences.userNodeForPackage(AuthService.class);
        initializeMockUsers();
    }
    
    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }
    
    /**
     * Инициализация тестовых пользователей
     */
    private void initializeMockUsers() {
        // Добавляем тестового пользователя
        User testUser = new User(
            "1",
            "Иван Петров",
            "test@example.com",
            "password123"
        );
        users.put(testUser.getEmail(), testUser);
    }
    
    /**
     * Вход в систему
     * TODO: Заменить на API запрос к бекенду
     */
    public boolean login(String email, String password) {
        User user = users.get(email);
        
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        
        return false;
        
        /* Будущая реализация с API:
        try {
            LoginRequest request = new LoginRequest(email, password);
            Response<LoginResponse> response = apiService.login(request);
            
            if (response.isSuccessful() && response.body() != null) {
                currentUser = response.body().getUser();
                saveAuthToken(response.body().getToken());
                return true;
            }
            return false;
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при входе", e);
        }
        */
    }
    
    /**
     * Регистрация нового пользователя
     * TODO: Заменить на API запрос к бекенду
     */
    public boolean register(String name, String email, String password) {
        // Проверка что email не занят
        if (users.containsKey(email)) {
            return false;
        }
        
        // Создание нового пользователя
        User newUser = new User(
            String.valueOf(System.currentTimeMillis()),
            name,
            email,
            password
        );
        
        users.put(email, newUser);
        return true;
        
        /* Будущая реализация с API:
        try {
            RegisterRequest request = new RegisterRequest(name, email, password);
            Response<RegisterResponse> response = apiService.register(request);
            
            return response.isSuccessful();
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при регистрации", e);
        }
        */
    }
    
    /**
     * Выход из системы
     */
    public void logout() {
        currentUser = null;
        // TODO: Удалить токен аутентификации
    }
    
    /**
     * Получение текущего пользователя
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Проверка авторизации
     */
    public boolean isAuthenticated() {
        return currentUser != null;
    }
    
    /**
     * Сохранение email для автозаполнения
     */
    public void rememberEmail(String email) {
        preferences.put(PREF_REMEMBERED_EMAIL, email);
    }
    
    /**
     * Получение сохраненного email
     */
    public String getRememberedEmail() {
        return preferences.get(PREF_REMEMBERED_EMAIL, null);
    }
    
    /**
     * Удаление сохраненного email
     */
    public void forgetEmail() {
        preferences.remove(PREF_REMEMBERED_EMAIL);
    }
}
