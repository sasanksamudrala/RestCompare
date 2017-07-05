REST Compare
======================
This is a web application for comparing the Base64 encoded binary value.

## Table of content

- [Pre-requisites](#pre-requisites)
- [What I did](#what-i-did)
    - [Framework](#framework)
    - [Database](#database)
    - [Unit Test](#unit-test)
	- [User Interface](#user-interface)
- [Documentation](#documentation)
- [Building the project](#building-the-project)
- [Running the project](#running-the-project)
- [Testing the project](#testing-the-project)
    

## Pre-requisites

1. The [repository](https://github.com/sasanksamudrala/RestCompare.git) need to be checked out into the local machine. 
2. [Maven](https://maven.apache.org/) is required for building the project
3. Make sure you have [Java(Runtime Environment JRE)](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) installed

## What I did
I have developed the application for the specified requirement.
I have created 2 additional HTTP endpoints which can be used to fetch the list of records in the system and list of records for each ID (both the Left and Right window values).
I have implemented Paging for this end point.

### Framework
* I have used Spring boot since this provides easy way for getting started with setting up of a new application

### Database
* I have used the H2 in-memory database since we do not need to persist/retain the data after the testing

### Unit Test
* I have used RestAssured unit test since it has easy way of passing parameters and checking responses which is good enough keeping in mind the simplicity of this application

### User Interface
* Swagger UI is used to provide the GUI for testing the HTTP Endpoints of the application without the need of installing any additional tools by the user


## Documentation
All API related documentation can be found on [Swagger](https://app.swaggerhub.com/apis/sasanksamudrala/RestComparison/v1).
Swagger docs within localhost application are on this [URL](http://localhost:8081/v2/api-docs)

Few examples of the REST API URI are:
I have used the value "1" for the ID parameter in this example.

    http://localhost:8081/v1/diff/1/left
    http://localhost:8081/v1/diff/1/right
    http://localhost:8081/v1/diff/1/
    http://localhost:8081/v1/diff/1/all
    http://localhost:8081/v1/diff/all

    
## Building the project
Building the project is as simple as:

    mvn clean install


## Running the project
To easily run the project locally you can use the below command which will start the application on port 8081:
    
    mvn spring-boot:run

## Testing the project
Once the application is started in local machine, please visit [Swagger UI](http://localhost:8081/swagger-ui.html) page which can be used to send requests to the HTTP end points of the application.

Optionally, we can use [Postman](https://www.getpostman.com/) as well for testing the endpoints but this requires installation of Postman tool in local machine.

