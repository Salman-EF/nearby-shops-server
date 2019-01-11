# Nearby Shops Back-end

The Back-end of [Nearby shops](https://github.com/Salman-EF/nearby-shops-UI.git), 
a React app built with [MongoDB](https://www.mongodb.com/) + 
[Java Spring-boot](http://projects.spring.io/spring-boot/) and configured with JWT security.

## Requirements

For building and running the application you need:

- [JDK 1.8.x](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.x.x](https://maven.apache.org)

The Database it's already deployed on mongoDB Atlas cloud service but if you want to work with a local one checkout 
[this part](#local-mongodb).

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
* Sign Up :
```
POST    http://localhost:8080/api/register

{ "email":"user1@gmail.com", "password":"User1Pass" }
```
* Sign In :
```
POST    http://localhost:8080/login

{ "email":"user1@gmail.com", "password":"User1Pass" }
```
After a successful sign-up or sign-in the response is a user JWT that you use in the header to access secured endpoints.
##### 2. Users (Secured)
* User / Me : The user email
```
GET    http://localhost:8080/api/users/me
```
##### 3. Shops (Secured)
* List of all Shops not the nearest neither sorted :
```
GET    http://localhost:8080/api/shops
```
* List of nearest Shops with user latitude/longitude and distance from these coordinates, filtered from this user preferred and disliked shops.
```
GET    http://localhost:8080/api/shops/nearest?lat=<latitude>&lon=<longitude>&distance=<distance>
```
* List of Preferred Shops of the user
```
GET    http://localhost:8080/api/shops/preferred
```
* Add a User Preferred Shop and return list of all the preferred shops
```
POST    http://localhost:8080/api/shops/preferred

body    { "id": "<shop_id>"}
```
* Remove User Preferred Shop and return list of all the preferred shops
```
DELETE    http://localhost:8080/api/shops/preferred

body    { "id": "<shop_id>"}
```
* Add a User Disliked Shop and return list of all the disliked shops
```
POST    http://localhost:8080/api/shops/disliked

body    { "id": "<shop_id>"}
```
## Local mongoDB
If you want to setup a local database and connect it with the application there are some requirements so you won't have a problem :

##### 1. Configuring mongoDB database
Firstly, You need to have mongoDB installed in your system,
checkout the [official mongodb doc](https://docs.mongodb.com/manual/administration/install-community/).
##### 2. Shops collection Data
You need to extract [this zip file](https://github.com/hiddenfounders/web-internship-cc/blob/master/dump-shops.zip) then execute :
```
mongorestore --db <databse-name> shops/
```
→→ A database will be created with the <databse-name> and shops collection.
##### 3. Application database properties
Change mongoDB properties inside the application:properties
```properties
spring.data.mongodb.host=<default: localhost>
spring.data.mongodb.port=<default: 27017>
spring.data.mongodb.database=<the name of database>
spring.data.mongodb.uri=mongodb://<host>/<database>
```

After you build and run the application a database with the name you specified and roles collection will be created with two documents USER and ADMIN.
