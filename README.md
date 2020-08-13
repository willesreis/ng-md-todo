# ng-md-todo is a imitation of the To-Do App
The deal is to make as similar as possible to the To-Do Application from a **famous enterprise** :)

It is evolving little by little...

---

## Running tests and build
In a **terminal**, from the root directory type according OS system:
```
[windows]> mvnw.cmd clean install

[unix]$ ./mvnw clean install
``` 

## Running the application
In a **terminal**, from the root directory type according OS system:
```
[windows]> mvnw.cmd spring-boot:run

[unix]$ ./mvnw spring-boot:run
``` 

## How-to use the application
At bootstrap of the app, will be load a script to create tables in memory database, allowing the persistence of data while the application is running.

Access the [index](http://localhost:8080/ng-md-todo) (single) page to start.

## Built with
- Spring Boot (Tomcat standalone)
- Groovy
- AngularJS
- Material Design
