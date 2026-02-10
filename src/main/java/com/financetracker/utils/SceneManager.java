package com.financetracker.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Менеджер для управления сценами приложения
 */
public class SceneManager {
    
    private static Stage primaryStage;
    private static Map<String, Scene> scenes = new HashMap<>();
    
    public static void initialize(Stage stage) {
        primaryStage = stage;
    }
    
    /**
     * Переключение на указанную сцену
     * @param sceneName имя FXML файла без расширения
     */
    public static void switchScene(String sceneName) {
        try {
            Scene scene = scenes.get(sceneName);
            
            if (scene == null) {
                scene = loadScene(sceneName);
                scenes.put(sceneName, scene);
            }
            
            primaryStage.setScene(scene);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка загрузки сцены: " + sceneName);
        }
    }
    
    /**
     * Загрузка сцены из FXML файла
     */
    private static Scene loadScene(String sceneName) throws IOException {
        String fxmlPath = "/fxml/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        // Загрузка глобальных стилей
        String cssPath = SceneManager.class.getResource("/css/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        
        return scene;
    }
    
    /**
     * Получение главного Stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * Очистка кэша сцен (для обновления)
     */
    public static void clearCache() {
        scenes.clear();
    }
}
