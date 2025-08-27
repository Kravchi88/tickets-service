# Tickets Service

REST-сервис для покупки транспортных билетов.  
Поддерживает регистрацию пользователей, поиск доступных билетов с фильтрами, покупку билетов, аутентификацию по JWT (access/refresh), роли (BUYER/ADMIN) и админ-CRUD для сущностей перевозчик/маршрут/билет.

## Стек
- Java 21, Spring Boot
- Spring Web, Spring Security, Validation
- JDBC (без Hibernate/Spring Data)
- PostgreSQL
- JWT (access/refresh)
- Swagger/OpenAPI (springdoc-openapi)
- Maven
- Docker, Docker Compose

## Переменные окружения

### Обязательные
Нужно задать, например через `.env` для `docker-compose`:

- **`DB_USER`** — имя пользователя PostgreSQL.
- **`DB_PASSWORD`** — пароль к бд.
- **`JWT_SECRET`** — секрет для подписи JWT.

### Опциональные
Уже есть дефолты в `application.yml`:

- **`APP_JWT_ISSUER`** — кто выпускает токены.
- **`APP_JWT_ACCESS_TTL_MINUTES`** — время жизни access-токена (минуты).
- **`APP_JWT_REFRESH_TTL_DAYS`** — время жизни refresh-токена (дни).

### Подключение к базе данных
Используется одна и та же PostgreSQL в контейнере, но с разными адресами:

- **Если сервис запускается через Docker Compose**: внутри сети Docker:  
  → `jdbc:postgresql://postgres:5432/tickets`.

- **Если сервис запускается локально (из IDE)**: через проброшенный порт:  
  → `jdbc:postgresql://localhost:5433/tickets`.

- **Тесты**: в профиле `test` используется отдельная база с суффиксом `_test`:  
  → `jdbc:postgresql://localhost:5433/tickets_test`.

## Запуск
- **Через Docker Compose**:

```bash
docker compose up -d --build
```

- **Локально (из IDE)**:

с профилем `dev` или для Postman профиль `test`

## Swagger / OpenAPI

Все эндпоинты задокументированы в Swagger.  
После запуска сервис доступен по адресу: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).  
Авторизация через кнопку **Authorize** (Bearer Token). Вставить access token из логина.

## Аутентификация и роли

Используются JWT-токены (access/refresh):

- **BUYER**: может регистрироваться, искать и покупать билеты.
- **ADMIN**: дополнительно имеет доступ к CRUD для перевозчиков, маршрутов и билетов.

## Postman

Есть коллекция с тестами.  
Файл `Tickets-Service.postman_collection.json` лежит в корне репозитория.

**Примечание:** тесты создавались параллельно с реализацией функционала.  
После добавления аутентификации старые тесты скорее всего упадут,  
потому что в них нет нужного заголовка и могут быть переназначены некоторые переменные коллекции.  
Я всё исправлю, но не факт, что успею к моменту проверки.