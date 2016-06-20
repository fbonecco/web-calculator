# web-calculator

RESTful application that exposes four end-points to perform very basic mathematical operations: add, subtract, multiply and divide.

### Available end-points
* /add/{a}/{b}/{c}
* /subtract/{a}/{b}/{c}
* /multiply/{a}/{b}/{c}
   - All of these support two and three numbers in the same call
* /divide/{a}/{b}
   - This support exactly two numbers in the same call

**Note:** None of the end-points perform operations if zero or only one number is passed. 

Every operation is treated as a resource so requesting /add/1/2/3 means: "give me the resource located at /add/1/2/3". Non valid operations  - for instance /divide/1/0- will end up returning 404 instead.

## Project summary
* This application was built using Spring -mainly as an IoC container- and Jersey for implementing RESTful operations.
* Maven for dependecy/project management.
* Requires JDK8.

## Build the project
Execute the following Maven command from project's root folder:
```
mvn package
```
This will generate a war package that can be deployed in a application server (such as Tomcat)
## Run the project using Jetty
You can also run the application by executing the following command from project's root folder:
```
mvn jetty:run -Djetty.port=8888
```
Check the application is running at port 8888 on your localhost. Eg: http://localhost:8888/web-calculator/add/1/2
