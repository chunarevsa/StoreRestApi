## StoreRestApi ##
#### Выполнил: Чунарёв Сергей. ####

Система управления товарами для площадки электронной коммерции.

####	Реализовано: ####

- Регистрация пользователя с подтверждением через почту
- Авторизация пользователя с использованием Spring Security и JWT
- Разграничение функциональности по ролям (USER, ADMIN)
- Поддержка входа и выхода с нескольких устройств.
- Возможность создания/редактирования/удаления товаров и внутренней валюты администратором
- Приобретение внутренней валюты со списанием средств ($) у пользователя
- Счета пользователя в разной внутренней валюте (gold:100; silver:50...)
- Приобретение товаров за внутреннюю валюту
- Пользовательский инвентарь для хранения товаров
- Выдача исключений при ошибках
- События (events и listeners)
- Миграция баз данных с помощью Flayway
- Запуск MYSQL сервера и проекта с помощью Docker

---

## Что потребуется для запуска

* Docker
* Git

или 

* Java
* Maven
* MYSQL
* Git

## Запуск проекта с помощью Docker ##

<h4> Загрузка  </h4>

```bash
git clone https://github.com/chunarevsa/StoreRestApi.git
cd StoreRestApi

```

<h4> Измените имя пользователя и пароль MySQL в application.properties </h4>

* `spring.datasource.username`
* `spring.datasource.password`

MYSQL сервер запускается с параметром ports:`3306:3306` 

База данных `websitechsa` создаётся автоматически

<h4> Измените имя пользователя и пароль для рассылки в mail.properties </h4>

* `spring.mail.username`
* `spring.mail.password` 

<h4> Запуск </h4>

```bash
docker-compose -f docker-compose.dev.yml up --build

```

Проект запускается с параметром `server.port:8088`

---

## Запуск проекта без Docker ##

<h4> Загрузка  </h4>

```bash
git clone https://github.com/chunarevsa/StoreRestApi.git
cd StoreRestApi

```

<h4> Подготовка </h4>

Необходимо запустить MYSQL сервер с портом `3306` 

<h4> Внесите изменения в application.properties </h4>

* `spring.datasource.username`
* `spring.datasource.password`

Переключить `spring.datasource.url` с "Для Docker" на "Без Docker"

<h4> Измените имя пользователя и пароль для рассылки в mail.properties </h4>

* `spring.mail.username`
* `spring.mail.password` 

<h4> Запуск через Bash </h4>

```bash
./mvnw spring-boot:run

```

Проект запускается с параметром `server.port:8088`

База данных `websitechsa` создаётся автоматически

---

## API ##

Если вы используете Postman - можно импортировать запросы из файла 
`Website.postman_collection.json`

> <h3> Аутентификация </h3>

<details>
<summary> Регистрация </summary>

```
curl --location --request POST 'localhost:8088//auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "admin@gmail.com",
    "password": "test1",
    "registerAsAdmin": true
}'
```

* `registerAsAdmin` - будет ли являться пользователь администратором
* `email` и `username` должны быть уникальными

</details>

---

<details>
<summary> Подтверждение регистрации </summary>

```
curl --location --request GET 'localhost:8088/auth/registrationConfirmation?token=bcbf8764-dbf2-4676-9ebd-2c74436293b9' \
```

* Выдаётся исключение, если `token` некорректный

</details>

---

<details>
<summary> Авторизация </summary>

```
curl --location --request POST 'localhost:8088/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "admin@gmail.com",
    "password": "test1",
    "deviceInfo": {
        "deviceId": "D1",
        "deviceType": "DEVICE_TYPE_ANDROID",
        "notificationToken": "N1"
    }
}'
```

* Авторизация осуществляется через `email` и `password`
* Необходимо указать информацию об устройстве 
* Поддерживается авторизация с нескольких устройств

</details>

---

<details>
<summary> Logout </summary>

```
curl --location --request POST 'localhost:8088/user/logout' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "deviceInfo": {
        "deviceId": "D1",
        "deviceType": "DEVICE_TYPE_ANDROID",
        "notificationToken": "N1"
    }
}'
```

* Logout c конкретного устройства

</details>

---

> <h3> Функции администратора </h3>

<details>
<summary> Пополнение счёта пользователя </summary>

```
curl --location --request POST 'localhost:8088/admin/addmoney?amount=1000&username=admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' 

```

* Добавляет пользователю средства `$`, на которые он может приобретать внутреннюю валюту, чтобы покупать товар 
* Выдаётся исключение, если такого пользователя не существут 

</details>

---


> <h3> Внутренняя валюта  </h3>

<details>
<summary> Добавление новый валюты </summary>

```
curl --location --request POST 'localhost:8088/currency/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "gold",
    "cost": "10",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Название `title` валюты должно быть уникальным
* Выдаётся исключение, если данные введены некорректно

</details>

---

<details>
<summary> Изменение валюты </summary>

```
curl --location --request POST 'localhost:8088/currency/gold/edit' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "gold",
    "cost": "12",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Выдаётся исключение, если такой валюты нет 
* Выдаётся исключение, если данные введены некорректно

</details>

---

<details>
<summary> Удаление валюты </summary>

```
curl --location --request POST 'localhost:8088/currency/gold/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Доступно только `ADMIN`
* Выдаётся исключение, если такой валюты нет

</details>

---

<details>
<summary> Получение списка всех валют </summary>

```
curl --location --request GET 'localhost:8088/currency/all' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER` - Получение списка активных валют. Выдаётся исключение, если таких нет
* `ADMIN` - Получение списка всех валют. Выдаётся исключение, если валюты отсутствуют

</details>

---

<details>
<summary> Получение информации о валюте </summary>

```
curl --location --request GET 'localhost:8088/currency/gold' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER`- Получение краткой информации о валюте. Выдаётся исключение, если она не активна или отсутствует
* `ADMIN`- Получение расширенной информации о валюте. Выдаётся исключение, если такая валюта отсутствует

</details>

---

<details>
<summary> Покупка валюты </summary>

```
curl --location --request POST 'localhost:8088/currency/buy?title=gold&amount=100' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если валюта неактивна или отсутствует
* Выдаётся исключение, если у пользователя недостаточно средств `$` для покупки указанного количества валюты `amount`
* Валюта добавлется пользователю в `account`. Если такой валюты у пользователя ещё не было - создаётся новый счёт для валюты

</details>

---

> <h3> Товары  </h3>

<details>
<summary> Добавление нового товара </summary>

```
curl --location --request POST 'localhost:8088/item/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Ломик Гордона Фримена",
    "type": "Weapon",
    "description": "Cool weapon ",
    "active": "true",
        "prices": [
            {
                "cost": "5",
                "currency": "gold",
                "active": "true"
            },
            {
                "cost": "50",
                "currency": "silver",
                "active": "true"
            }
                    ]
}'
```

* Доступно только `ADMIN`
* Товар создаётся, как минимум, с одной ценой во внутренней валюте
* У товара может быть только одна цена в одной валюте 
* Выдаётся исключение, если цена указана в отсутствующей валюте 
* Выдаётся исключение, если цены и товары введены некорректно 

</details>

---


<details>
<summary> Добавление новой цены товару </summary>

```
curl --location --request POST 'localhost:8088/item/1/prices/add' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cost": "10",
    "currency": "silver",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Выдаётся исключение, если цена указана в отсутствующей валюте 
* Выдаётся исключение, если цены введены некорректно 
* Выдаётся исключение, если у товара уже есть цена в такой валюте 

</details>

---

<details>
<summary> Изменение товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/edit' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Легендарный плащ",
    "type": "skin",
    "description": "обычный плащ",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Выдаётся исключение, если данные о товаре введены некорректно 

</details>

---

<details>
<summary> Изменение цены товара </summary>

```
curl --location --request POST 'localhost:8088/item/price/1/edit' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cost": "10",
    "currency": "silver",
    "active": "true"
}'
```

* Доступно только `ADMIN`
* Выдаётся исключение, если цена указана в отсутствующей валюте 
* Выдаётся исключение, если данные о цене введены некорректно 
* Выдаётся исключение, если у товара уже есть цена в такой валюте

</details>

---

<details>
<summary> Удаление товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Доступно только `ADMIN`
* Выдаётся исключение, если такой товар отсутствует

</details>

---

<details>
<summary> Получение списка всех товаров </summary>

```
curl --location --request GET 'localhost:8088/item/all' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER` - Получение списка активных товаров. Выдаётся исключение, если таких нет
* `ADMIN` - Получение списка всех товаров. Выдаётся исключение, если товары отсутствуют

</details>

---

<details>
<summary> Получение информации о товаре </summary>

```
curl --location --request GET 'localhost:8088/item/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER`- Получение краткой информации о товаре. Выдаётся исключение, если он неактивен или отсутствует
* `ADMIN`- Получение расширенной информации о товаре. Выдаётся исключение, если такой товар отсутствуют

</details>

---

<details>
<summary> Получение всех цен товара </summary>

```
curl --location --request GET 'localhost:8088/item/1/prices' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если такой товар отсутствует
* Разное предоставление информации в зависимости от роли (ADMIN, USER)
* `USER`- Получение списка цен с краткой информацией. Выдаётся исключение, если они не активны
* `ADMIN`- Получение списка цен с расширенной информацией.

</details>

---

<details>
<summary> Покупка товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/buy?currencytitle=gold&amountitem=2' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если товар неактивен или отсутствует
* Выдаётся исключение, если цена товара в такой валюте неактивна или отсутствует
* Выдаётся исключение, если у пользователя недостаточно валюты `gold` для покупки указанного количества товаров `amountitem`
* Валюта `gold` списывается со `account` 
* Товар добавляется пользователю в инвентарь `inventory`. Если такого товара у пользователя ещё не было - создаётся новая ячейка `inventoryUnit` 

</details>

---

> <h3> Пользователь </h3>

<details>
<summary> Свой профиль </summary>

```
curl --location --request GET 'localhost:8088/user/profile' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'
```

* Получение информации о своём профиле, балансе `$` и счетах `accounts`

</details>

---

<details>
<summary> Просмотр своего инвентаря  </summary>

```
curl --location --request GET 'localhost:8088/user/profile/inventory' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Получение информации о своём инвентаре `inventory`

</details>

---

<details>
<summary> Просмотр профиля любого пользователя  </summary>

```
curl --location --request GET 'localhost:8088/user/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Выдаётся исключение, если такой пользователь отсутствует
* Получение краткой информации о другом пользователе

</details>

---

<details>
<summary> Получение списка всех пользователей  </summary>

```
curl --location --request GET 'localhost:8088/user/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Доступно только `ADMIN`
* Получение списка с краткой информацией о всех пользователях

</details>

---

---
