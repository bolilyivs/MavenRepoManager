## Первый модуль c помощью ivy:

- Поиск артефакта в указанном репозитории (можно реализовать через ivy SearchEngine)
- Получение дерева всех зависимостей указанного артефакта (уже реализовано с использованием ivy ResolveEngine)
- Скачивание артефакта в указанный каталог в виде: каталог/org/company/artefact/version/ (можно реализовать через ivy или через http client)

## Второй модуль:

- API для управления репозиториями (CRUD), которые хранятся в БД
- Доступ к каталогу репозитория с артефактами по url вида: https: //{webserever}/{repository}/org/springframework/boot/spring-boot-starter-data-jpa/4.0.2/spring-boot-starter-data-jpa-4.0.2.pom
- API для загрузки артефакта из удалённого репозитория (maven central) в указанный, используя первый модуль
- API для поиска артефакта из удалённого/локального репозитория (maven central) в указанный, используя второй модуль
- Технологический стек такой: JDK 25, Micronaut, Ivy, Swagger, Database(h2, sqlite, hsqldb, Derby)

## Технические момент:

Сервис будет работать на встроенной БД (но с возможностью заменить на PostgreSql)

## API управление репозиторием

```
POST /api/v1/repoDto/ -Добавляем
GET /api/v1/repoDto/{id} -получаем информацию
PATCH /api/v1/repoDto/{id} - Изменяем
DELETE /api/v1/repoDto/{id} - удаляем физически
API для загрузки артефакта из удалённого репозитория:
POST /api/v1/repoDto/{id}/remote/load
API для поиска артефакта
GET /api/v1/repoDto/{id}/remote/search
GET /api/v1/repoDto/{id}/local/search
Доступ к репозиторию для Gradle, Maven:
GET /repoDto/reponame/org/company/artefact/version/file
```
