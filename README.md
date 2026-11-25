# Запись к врачу (Doctor Appointment)

Веб-сервис записи пациентов на приём к врачу.

Пациент оставляет заявку на странице записи, администратор просматривает список заявок с фильтрами и постраничной навигацией.

## Возможности

- **Запись на приём**: ФИО, телефон с маской, email и специализация врача.
- **Список записей** с фильтрами и постраничной навигацией.
- **REST API** для создания, редактирования, получения по id и списка записей, описан в Swagger.

## Стек

| Слой | Технологии |
|---|---|
| Backend | Java 21, Spring Boot 4.0, Spring Data JPA, Liquibase, springdoc (Swagger) |
| Frontend | React 19, Vite, Ant Design |
| База данных | PostgreSQL 18, схема через Liquibase |
| Инфраструктура | Docker Compose, nginx (HTTPS в проде) |

## Быстрый старт

Нужен только Docker с Compose. JDK, Node.js и PostgreSQL на машине не нужны.

```bash
cp .env.example .env     # задайте DB_USER, DB_PASSWORD, DB_NAME
docker compose up -d --build
```

Приложение откроется на http://localhost, Swagger на http://localhost/swagger-ui/index.html.

Остановка:

```bash
docker compose down        # данные БД сохраняются
docker compose down -v     # вместе с данными БД
```

Контейнеры сами не запускаются вместе с Docker Desktop, их нужно поднимать командой выше.

Логи бэкенда: `docker compose logs -f da-backend`. Тесты бэкенда: `cd backend && ./gradlew test`.

## Продакшен

Нужны сертификаты в `certs/` (`fullchain.pem` и `privkey.pem`):

```bash
docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d --build
```

В проде у БД и бэкенда включён `restart: unless-stopped`.

## Структура репозитория

```
backend/     Spring Boot API (Gradle wrapper внутри)
frontend/    Vite + React
structurizr/ архитектурная модель (Structurizr DSL)
nginx*.conf  конфиги nginx для dev и prod
```
