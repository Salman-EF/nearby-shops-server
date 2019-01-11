# Nearby Shops API

The API of [Nearby shops](https://github.com/Salman-EF/nearby-shops-UI.git) React app with [MongoDB](https://www.mongodb.com/) + [Java Spring-boot](http://projects.spring.io/spring-boot/) and configured with JWT security.

## Requirements

For building and running the application you need:

- [JDK 1.8.x](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.x.x](https://maven.apache.org)

The Database it's already deployed on mongoDB Atlas cloud service but if you want to with

## Steps to Setup
##### 1. Clone the application
```
git clone https://github.com/Salman-EF/nearby-shops-server.git
```

##### 2. Build and run using maven
```
mvn package
java -jar target/nearbyshops-1.0.jar
```
Or you can run it without packaging
```
mvn spring-boot:run
```
The server will start at http://localhost:8080.

## Work with API
These are all the requests this API wait for :

##### 1. Authentication / JWT security
Sign Up : To create a new user account and Json object with email and password required.
```
POST    http://localhost:8080/api/register

{ "email":"user1@gmail.com", "password":"User1Pass" }
```
Sign In :
```
POST    http://localhost:8080/login

{ "email":"user1@gmail.com", "password":"User1Pass" }
```
After a successful sign-up or sign-in the response is a user JWT that you use in the header to access secured endpoints.
##### 2. Users (Secured)
User / Me : 
```
GET    http://localhost:8080/api/users/me
```
##### 3. Shops (Secured)
List of all Shops not the nearest neither sorted
```
GET    http://localhost:8080/api/shops
```
List of nearest Shops with user latitude/longitude and distance from these coordinates
```
GET    http://localhost:8080/api/shops/nearest?lat=<latitude>&lon=<longitude>&distance=<distance>
```
List of Preferred Shops of the user
```
GET    http://localhost:8080/api/shops/preferred
```
Add a User Preferred Shop and return list of all the preferred shops
```
POST    http://localhost:8080/api/shops/preferred

body    { "id": "<shops_id>"}
```
Remove User Preferred Shop and return list of all the preferred shops
```
DELETE    http://localhost:8080/api/shops/preferred

body    { "id": "<shops_id>"}
```
Add a User Disliked Shop and return list of all the disliked shops
```
POST    http://localhost:8080/api/shops/disliked

body    { "id": "<shops_id>"}
```