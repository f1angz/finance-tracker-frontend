package com.financetracker.controllers;

import com.financetracker.services.AuthService;
import com.financetracker.utils.SceneManager;
import javafx.fxml.FXML;

/**
 * Контроллер для главного экрана (временный)
 */
public class DashboardController {
    
    private final AuthService authService;
    
    public DashboardController() {
        this.authService = AuthService.getInstance();
    }
    
    @FXML
    private void handleLogout() {
        authService.logout();
        SceneManager.switchScene("login");
    }
}
