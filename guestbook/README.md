
# «Книга відгуків».

## Технології

- Java 21
- Maven
- Jakarta Servlet 5
- Jetty 11
- JDBC
- H2 Database
- Jackson
- SLF4J + Logback

## Запуск

Потрібен JDK 21 та Maven.

Перевірка:

```bash
java -version
mvn -version
```

Перейти в корінь проєкту:

```bash
cd guestbook
```

Зібрати:

```bash
mvn clean compile
```

Запустити:

```bash
mvn jetty:run
```

Відкрити:

```text
http://localhost:8080/
```

Зупинити сервер:

```text
Ctrl+C
```

## База даних

URL:

```text
jdbc:h2:file:./data/guest;AUTO_SERVER=TRUE
```

База створюється автоматично в папці `data`.

Користувач H2: `sa`

Пароль: порожній.

Таблиця:

```text
comments
```

Поля:

```text
id
author
text
created_at
```

## API

### GET /comments

Повертає JSON-масив коментарів у порядку від нових до старих.

Успіх:

```text
200 OK
```

### POST /comments

Приймає поля:

```text
author
text
```

Успіх:

```text
204 No Content
```

Помилки:

```text
400 Bad Request
500 Internal Server Error
```

## Валідація

`author` — обов'язковий, максимум 64 символи.

`text` — обов'язковий, максимум 1000 символів.

## Логування

Після успішного додавання:

```text
INFO ... New comment: id=1, author=Олена, length=25
```

