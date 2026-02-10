package com.financetracker.controllers;

import com.financetracker.services.AuthService;
import com.financetracker.utils.SceneManager;
import com.financetracker.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Контроллер для экрана регистрации
 */
public class RegisterController {
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    private final AuthService authService;
    
    public RegisterController() {
        this.authService = AuthService.getInstance();
    }
    
    /**
     * Инициализация контроллера
     */
    @FXML
    public void initialize() {
        setupValidation();
    }
    
    /**
     * Настройка валидации полей
     */
    private void setupValidation() {
        // Валидация email
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && !emailField.getText().isEmpty()) {
                if (!ValidationUtils.isValidEmail(emailField.getText())) {
                    emailField.setStyle("-fx-border-color: #EF4444; -fx-border-width: 2px;");
                } else {
                    emailField.setStyle("");
                }
            }
        });
        
        // Валидация подтверждения пароля
        confirmPasswordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && !confirmPasswordField.getText().isEmpty()) {
                if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                    confirmPasswordField.setStyle("-fx-border-color: #EF4444; -fx-border-width: 2px;");
                } else {
                    confirmPasswordField.setStyle("");
                }
            }
        });
        
        // Очистка стилей при вводе
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!emailField.getStyle().isEmpty() && ValidationUtils.isValidEmail(newVal)) {
                emailField.setStyle("");
            }
        });
        
        confirmPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!confirmPasswordField.getStyle().isEmpty() && newVal.equals(passwordField.getText())) {
                confirmPasswordField.setStyle("");
            }
        });
    }
    
    /**
     * Обработка регистрации
     */
    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Валидация
        if (!validateInputs(name, email, password, confirmPassword)) {
            return;
        }
        
        // Попытка регистрации
        try {
            boolean success = authService.register(name, email, password);
            
            if (success) {
                // Показываем успех
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Регистрация успешна");
                alert.setHeaderText(null);
                alert.setContentText("Ваш аккаунт успешно создан! Теперь вы можете войти.");
                alert.showAndWait();
                
                // Переход на экран входа
                SceneManager.switchScene("login");
                
            } else {
                showError("Ошибка при регистрации. Возможно, этот email уже используется.");
            }
            
        } catch (Exception e) {
            showError("Ошибка при регистрации: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Валидация введенных данных
     */
    private boolean validateInputs(String name, String email, String password, String confirmPassword) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Пожалуйста, заполните все поля");
            return false;
        }
        
        if (name.length() < 2) {
            showError("Имя должно содержать минимум 2 символа");
            nameField.requestFocus();
            return false;
        }
        
        if (!ValidationUtils.isValidEmail(email)) {
            showError("Пожалуйста, введите корректный email");
            emailField.requestFocus();
            return false;
        }
        
        if (password.length() < 6) {
            showError("Пароль должен содержать минимум 6 символов");
            passwordField.requestFocus();
            return false;
        }
        
        if (!ValidationUtils.isStrongPassword(password)) {
            showError("Пароль должен содержать буквы и цифры");
            passwordField.requestFocus();
            return false;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Пароли не совпадают");
            confirmPasswordField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Переход на экран входа
     */
    @FXML
    private void handleGoToLogin() {
        SceneManager.switchScene("login");
    }
    
    /**
     * Показ ошибки
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка регистрации");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
