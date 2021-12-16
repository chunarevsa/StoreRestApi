## StoreRestApi ##
#### Выполнил: Чунарёв Сергей. ####

Системы управления товарами для площадки электронной коммерции.

####	Реализовано: ####

- Регистрация пользователя с подтверждением через почту
- Авторизация пользователя с использованием Spring Security и JWT
- Разграничение функциональности по ролям (USER, ADMIN)
- Поддержка входа и выхода с нескольких устройств.
- Возможность создания/редактировани/удаления товаров и внутренней валюты администратором
- Приобретение внутренней валюты со списанием средств ($) у пользователя
- Счета пользователя в разной внутренней валюте (gold: 100; silver:50...)
- Приобретение товаров за внутреннюю валюту
- Пользовательский инвентарь для хранения товаров
- Вадыча исключений при ошибках
- Миграция баз данных с помощью Flayway

---

## Что потребуется для запуска

* Java
* Maven
* MYSQL
* Git

## Запуск проекта ##

<h4> Загрузка проекта </h4>

```bash
$ git clone https://github.com/chunarevsa/StoreRestApi.git
$ cd StoreRestApi
```

<h4> Создание базы данных MYSQL </h4>

Необходимо запустить MYSQL сервер с портом `3306` 
База данных `websitechsa` создаётся автоматически при отсутствии
Если с этим возникли проблемы то:

```bash
$ create database websitechsa
```

<h4> Измените имя пользователя и пароль MySQL в application.properties </h4>

* `spring.datasource.username`
* `spring.datasource.password`

<h4> Измените имя пользователя и пароль для рассылки в mail.properties </h4>

* `spring.mail.username`
* `spring.mail.password` 

<h4> Запуск </h4>

```bash
$ ./mvnw spring-boot:run   # для UNIX/Linux 
$ mvnw.cmd spring-boot:run # для Windows 
```
Проект запускается с параметром `server.port:8088`

---

## API ##

Если вы используете Postman можно импортировать запросы из фаила 
`Website.postman_collection.json`

<h3> Аутентификация </h3>

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
* Почта и имя пользователя должны быть уникальными

</details>

---

<details>
<summary> Подстверждение регистрации </summary>

```
curl --location --request GET 'localhost:8088/auth/registrationConfirmation?token=bcbf8764-dbf2-4676-9ebd-2c74436293b9' \
```

* 
* 

</details>

---

<details>
<summary> Login </summary>

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

* 
* 

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

* 
* 

</details>

---

---

## Функции администратора 

<details>
<summary> Пополнение счёта пользователя </summary>

```
curl --location --request POST 'localhost:8088/uadmin/addmoney?amount=1000&username=admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA' 

```

* 
* 

</details>

---

---

<h3> Внутренняя валюта  </h3>

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

* 
* 

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

* 
* 

</details>

---

<details>
<summary> Удаление валюты </summary>

```
curl --location --request POST 'localhost:8088/currency/gold/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* 
* 

</details>

---

<details>
<summary> Получение списка всех валют </summary>

```
curl --location --request GET 'localhost:8088/currency/all' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Результат зависит от роли пользователя 
* 

</details>

---

<details>
<summary> Получение информации о валюте </summary>

```
curl --location --request GET 'localhost:8088/currency/gold' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Результат зависит от роли пользователя 
* 

</details>

---

<details>
<summary> Покупка валюты </summary>

```
curl --location --request POST 'localhost:8088/currency/buy?title=gold&amount=100' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* 
* 

</details>

---

---

<h3> Товары  </h3>

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

* 
* 

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

* 
* 

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

* 
* 

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

* 
* 

</details>

---

<details>
<summary> Удаление товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* 
* 

</details>

---

<details>
<summary> Получение списка всех товаров </summary>

```
curl --location --request GET 'localhost:8088/item/all' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Результат зависит от роли пользователя 
* 

</details>

---

<details>
<summary> Получение информации о товаре </summary>

```
curl --location --request GET 'localhost:8088/item/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Результат зависит от роли пользователя 
* 

</details>

---

<details>
<summary> Получение всех цен товара </summary>

```
curl --location --request GET 'localhost:8088/item/1/prices' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* Результат зависит от роли пользователя 
* 

</details>

---

<details>
<summary> Покупка товара </summary>

```
curl --location --request POST 'localhost:8088/item/1/buy?currencytitle=gold&amountitem=2' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* 
* 

</details>

---

---

<h3> Пользователь </h3>

<details>
<summary> Свой профиль </summary>

```
curl --location --request GET 'localhost:8088/user/profile' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'
```

* 
* 

</details>

---

<details>
<summary> Просмотр своего инвенторя  </summary>

```
curl --location --request GET 'localhost:8088/user/profile/inventory' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* 
* 

</details>

---

<details>
<summary> Просмотр профля любого пользователя  </summary>

```
curl --location --request GET 'localhost:8088/user/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* 
* 

</details>

---

<details>
<summary> Получение списка всех пользователей  </summary>

```
curl --location --request GET 'localhost:8088/user/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjM5Mjk2NTEwLCJleHAiOjE2NDE4ODg1MTAsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSLFJPTEVfQURNSU4ifQ.v-EYaLqelzIn0emvlRPTzg7LIA4-y-Q0zsa9NREAJvTmh38gugeN0WIdbAQMKI10ql87fs9A4EncNeH3WydLdA'

```

* 
* 

</details>

---

---



Аутентификация
* Регистрация на основе email с подтверждение через почту
* Авторизация с использование Spring Security и JWT
* Login и Logout с нескольких устройств
* Разграничение функциональности по ролям (USER, ADMIN)
Пользователь
* Инвентарь пользователя с наличием ячеек под Item
* Разделение счетов под внутренюю валюту
* Получение информации о состоянии своего профиля
* Получение краткой информации о другом пользователе
* Получение списка всех пользователей (ADMIN)
Внутренняя валюта
* Создание, изменение и выключение валюты (ADMIN)
* Получение списка валют и конкретной валюты. Разное предоставление информации в зависимости от роли
* Покупка валюты со списание баланса и добавлением в счет пользователя
Item
* Получение списка Item и конкретного Item. Разное предоставление информации в зависимости от роли
* Получение списка цен у конкретного Item.  Разное предоставление информации в зависимости от роли
* Покупка Item со списание внутренней валюты и добавлнием в инвентарь
* Создание Item c ценами во внутренней валюте (ADMIN). Возможно создать не активную цену.
* Изменение Item без изменения цен (ADMIN)
* Изменеие или выключение конкретной цены (ADMIN)
* Выключение Item с выключение всех цен (ADMIN)


