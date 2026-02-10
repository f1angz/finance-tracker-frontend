package com.financetracker.controllers;

import com.financetracker.services.AuthService;
import com.financetracker.utils.SceneManager;
import com.financetracker.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Контроллер для экрана входа
 */
public class LoginController {
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private CheckBox rememberMeCheckbox;
    
    private final AuthService authService;
    
    public LoginController() {
        this.authService = AuthService.getInstance();
    }
    
    /**
     * Инициализация контроллера
     */
    @FXML
    public void initialize() {
        setupValidation();
        loadRememberedCredentials();
    }
    
    /**
     * Настройка валидации полей
     */
    private void setupValidation() {
        // Валидация email в реальном времени
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal && !emailField.getText().isEmpty()) {
                if (!ValidationUtils.isValidEmail(emailField.getText())) {
                    emailField.setStyle("-fx-border-color: #EF4444; -fx-border-width: 2px;");
                } else {
                    emailField.setStyle("");
                }
            }
        });
        
        // Очистка стиля при вводе
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!emailField.getStyle().isEmpty() && ValidationUtils.isValidEmail(newVal)) {
                emailField.setStyle("");
            }
        });
    }
    
    /**
     * Загрузка сохраненных учетных данных
     */
    private void loadRememberedCredentials() {
        String rememberedEmail = authService.getRememberedEmail();
        if (rememberedEmail != null) {
            emailField.setText(rememberedEmail);
            rememberMeCheckbox.setSelected(true);
        }
    }
    
    /**
     * Обработка входа в систему
     */
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        
        // Валидация
        if (!validateInputs(email, password)) {
            return;
        }
        
        // Попытка входа
        try {
            boolean success = authService.login(email, password);
            
            if (success) {
                // Сохранение email если выбрано "Запомнить меня"
                if (rememberMeCheckbox.isSelected()) {
                    authService.rememberEmail(email);
                } else {
                    authService.forgetEmail();
                }
                
                // Переход на главный экран
                SceneManager.switchScene("dashboard");
                
            } else {
                showError("Неверный email или пароль");
            }
            
        } catch (Exception e) {
            showError("Ошибка при входе: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Валидация введенных данных
     */
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showError("Пожалуйста, заполните все поля");
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
        
        return true;
    }
    
    /**
     * Обработка "Забыли пароль?"
     */
    @FXML
    private void handleForgotPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Восстановление пароля");
        alert.setHeaderText(null);
        alert.setContentText("Функция восстановления пароля будет доступна после подключения бекенда.");
        alert.showAndWait();
    }
    
    /**
     * Переход на экран регистрации
     */
    @FXML
    private void handleGoToRegister() {
        SceneManager.switchScene("register");
    }
    
    /**
     * Показ ошибки
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка входа");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
