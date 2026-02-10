# Finance Tracker - Frontend

Desktop приложение для учёта личных финансов на JavaFX.

## Технологии

- **Java 23**
- **JavaFX 21**
- **Maven**
- **ControlsFX** - расширенные UI компоненты
- **FontAwesome** - иконки
- **JFoenix** - Material Design компоненты
- **OkHttp** - HTTP клиент для API (готов к подключению)
- **Gson** - JSON обработка

## Архитектура

Проект использует **MVVM** (Model-View-ViewModel) паттерн:

```
src/main/java/com/financetracker/
├── FinanceTrackerApp.java          # Главный класс приложения
├── controllers/                     # View слой (контроллеры)
│   ├── LoginController.java
│   ├── RegisterController.java
│   └── DashboardController.java
├── models/                          # Model слой (модели данных)
│   └── User.java
├── services/                        # ViewModel слой (бизнес-логика)
│   └── AuthService.java
└── utils/                           # Утилиты
    ├── SceneManager.java
    └── ValidationUtils.java

src/main/resources/
├── fxml/                            # FXML разметка экранов
│   ├── login.fxml
│   ├── register.fxml
│   └── dashboard.fxml
└── css/                             # Стили
    └── styles.css
```

## Запуск проекта

### Требования

- JDK 23
- Maven 3.6+

### Команды

```bash
# Сборка проекта
mvn clean install

# Запуск приложения
mvn javafx:run
```

## Реализованные экраны

### 1. Экран входа (Login)
- Email и пароль
- Чекбокс "Запомнить меня"
- Ссылка "Забыли пароль?"
- Переход на регистрацию

**Тестовый аккаунт:**
- Email: `test@example.com`
- Пароль: `password123`

### 2. Экран регистрации (Register)
- Имя пользователя
- Email
- Пароль и подтверждение пароля
- Валидация всех полей
- Переход на вход

### 3. Dashboard (заглушка)
- Временный экран для проверки авторизации
- Кнопка выхода

## Особенности реализации

### Валидация
- Email проверяется по regex паттерну
- Пароль должен содержать минимум 6 символов, буквы и цифры
- Визуальная индикация ошибок (красная рамка)
- Валидация в реальном времени при потере фокуса

### Стили
- Современный минималистичный дизайн
- Цветовая схема с синим акцентом (#3B82F6)
- Плавные переходы и анимации
- Адаптивные компоненты
- Тени и скругленные углы

### Сервисы
- `AuthService` - управление аутентификацией (сейчас mock, готов к API)
- `SceneManager` - управление навигацией между экранами
- Паттерн Singleton для сервисов

## Подключение к бекенду

Сервис `AuthService` уже подготовлен для интеграции с REST API:

```java
// Закомментированный код в AuthService показывает будущую структуру:

public boolean login(String email, String password) {
    // TODO: Заменить mock на реальный API запрос
    LoginRequest request = new LoginRequest(email, password);
    Response<LoginResponse> response = apiService.login(request);
    // ...
}
```

### Что нужно добавить:

1. **API клиент** (уже добавлен OkHttp в зависимости)
2. **DTO классы** для запросов/ответов
3. **Конфигурация** с URL бекенда
4. **Обработка токенов** JWT

## Следующие шаги

- [ ] Главный экран Dashboard
- [ ] Экран транзакций
- [ ] Экран категорий
- [ ] Экран статистики
- [ ] Настройки профиля
- [ ] Интеграция с бекендом

## Структура проекта для бекенда

Когда бекенд будет готов, создайте пакеты:

```
com/financetracker/
├── api/                  # API клиенты
│   ├── ApiClient.java
│   └── AuthApi.java
├── dto/                  # Data Transfer Objects
│   ├── request/
│   └── response/
└── config/               # Конфигурация
    └── ApiConfig.java
```

## Контакты

При возникновении вопросов или предложений создавайте issue в репозитории.
