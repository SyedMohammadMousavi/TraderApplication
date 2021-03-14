Online Trader Application
=========================

Application Users :-   TraderA, TraderB, TraderC, Admin
Common Password   :-   password

GitHub Repo - https://github.com/SyedMohammadMousavi/TraderApplication.git
-----------

DB Connection Details (Postgres):
--------------------------------

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres

Functionality:
-------------

1. TraderA Can Create Item.
2. TraderB Can Create Item.
3. TraderA Cannot View/Modify/Delete TraderB Items. (Spring Security)
4. Admin Can View All Items.
5. TraderA can View/Modify/Delete his own Items.

Application EndPoint Documentation:
-----------------------------------

http://localhost:8080/swagger-ui/index.html#/items-controller
http://localhost:8080/swagger-ui/index.html#/user-controller

Application Run Instructions:
-----------------------------

1. java -jar items-0.0.1-SNAPSHOT.jar

				OR
				
2. docker run -p 8080:8080 springio/gs-spring-boot-docker


Application Use Instructions:
-----------------------------

1. Launch PostMan
2. Set Basic Authentication Hearder (Username - TraderA, Password - password).
3. Hit Create Item EndPoint.
4. View List of Items by same user in "GBP" Currency.
5. Update/Delete any Item.
6. Set Basic Authentication Hearder(Username - Admin, Password - password)
7. View All Items from Any User.
8. Login with different user to create/view/update Items.

Application Features:
---------------------

1. Swagger UI
2. Docker
3. Spring Security
4. Spring Profiles
5. CustomException Handling
6. Spring JPA
7. TDD Approach (Unit Testcases)
8. Currency Conversion
9. Auto Run DB Scripts on Launch.
10. Rest EndPoints
