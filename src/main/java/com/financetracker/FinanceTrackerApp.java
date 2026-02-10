package com.financetracker;

import com.financetracker.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Главный класс приложения Finance Tracker
 */
public class FinanceTrackerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            SceneManager.initialize(primaryStage);
            SceneManager.switchScene("login");
            
            primaryStage.setTitle("Finance Tracker");
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(650);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
