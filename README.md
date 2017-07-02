# Introduction
This is a web application for comparing the Base64 encoded binary value.

The repository location is: https://github.com/sasanksamudrala/RestCompare.git

All API related documentation can be found on [RestComparison](https://app.swaggerhub.com/apis/sasanksamudrala/RestComparison/v1)

Few examples of the REST API URI are:

	http://localhost:8081/v1/diff/1/left
	http://localhost:8081/v1/diff/1/right
	http://localhost:8081/v1/diff/1/
	http://localhost:8081/v1/diff/1/all
	http://localhost:8081/v1/diff/all

	
# Building the project
Building the project is as simple as:

    mvn clean install eclipse:clean eclipse:eclipse


# Running the project
To easily run the project locally you can the below command which will start the application on port 8081:
    
	mvn spring-boot:run

[markdown]: http://daringfireball.net/projects/markdown
