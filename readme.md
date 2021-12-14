
## Home bookkeeping
##### Home budget accounting app


##### Technologies used:

- Maven
- Spring Boot
- Spring Data JPA
- Spring MVC
- Spring Security Database Authentication
- PostgreSQL
- Tomcat
- Bootstrap

#### Modules:
- dao 
- service 
- web

#### How to build project:

##### To build you need *Maven 3.3.9* or higher

command for build: 
```yaml
mvn package
```
or if you need to delete the `target` folder in modules:
```yaml
mvn clean package
```
If during assembly you need to skip unit tests, then you need to add `-DskipTests`

#### Application screenshots:

##### Login page:
![alt text](images/login.jpg)

##### Account page:
![alt text](images/account.jpg)

##### Currency page:
![alt text](images/currency.jpg)

##### Transaction categories page:
![alt text](images/category.jpg)

##### Transaction page:

For a user with the `admin` role, the `пользователи` menu is available in the header
![alt text](images/transaction.jpg)

For a simple user with the `user` role, there is no `пользователи` menu item
![alt text](images/transaction1.jpg)

##### Users page, visible only to the administrator:
![alt text](images/users.jpg)

##### New transaction creation form:
![alt text](images/create_transaction.jpg)
