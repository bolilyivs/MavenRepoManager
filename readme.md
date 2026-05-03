Первый модуль c помощью [ivy](https://javadoc.io/doc/org.apache.ivy/ivy/latest/index.html):

- Поиск артефакта в указанном репозитории (можно реализовать через ivy SearchEngine)
- Получение дерева всех зависимостей указанного артефакта (уже реализовано с использованием ivy ResolveEngine)
- Скачивание артефакта в указанный каталог в виде: каталог/org/company/artefact/version/ (можно реализовать через ivy
  или через http client)

Второй модуль:

- API для управления репозиториями (CRUD), которые хранятся в БД
- Доступ к каталогу репозитория с артефактами по url вида: https:
  //{webserever}/{repository}/org/springframework/boot/spring-boot-starter-data-jpa/4.0.2/spring-boot-starter-data-jpa-4.0.2.pom
- API для загрузки артефакта из удалённого репозитория (maven central) в указанный, используя первый модуль
- API для поиска артефакта из удалённого/локального репозитория (maven central) в указанный, используя второй модуль

Технологический стек такой:
JDK 25, Micronaut, Ivy, OpenApi, Database(h2, sqlite, hsqldb, Derby)

- Доступ к репозиторию для Gradle, Maven:

* GET /repo/reponame/org/company/artefact/version/file