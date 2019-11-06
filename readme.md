
## Домашняя бухгалтерия
##### Приложение для ведения учета домашнего бюджета


##### Используемые технологии:

- Maven
- Spring Boot
- Spring Data JPA
- Spring MVC
- Spring Security Database Authentication
- Postresql
- Tomcat
- Bootstrap

#### Модули:
- dao 
- service 
- web

#### Сборка проекта:

##### Для сборки нужен *Maven 3.3.9* и выше

собирается командой: 
```yaml
mvn package
```
или если в модулях нужно удалить папку `target`:
```yaml
mvn clean package
```
Если при сборке нужно пропустить юнит тесты, то нужно добавить `-DskipTests`

#### Скриншоты приложения:

##### Страница авторизации:
![alt text](images/login.jpg)

##### Страница счетов:
![alt text](images/account.jpg)

##### Страница валют:
![alt text](images/currency.jpg)

##### Страница категорий транзакций:
![alt text](images/category.jpg)

##### Страница транзакций:

Для пользователя с ролью `admin`, в шапке доступно меню `пользователи`
![alt text](images/transaction.jpg)

Для простого пользователя с ролью `user`, этого пункта меню нет
![alt text](images/transaction1.jpg)

##### Страница пользователи, видно только администратору:
![alt text](images/users.jpg)

##### Форма создания новой транзакции: 
![alt text](images/create_transaction.jpg)