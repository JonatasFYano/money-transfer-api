[![Build Status](https://travis-ci.org/JonatasFYano/money-transfer-api.svg?branch=master)](https://travis-ci.org/JonatasFYano/money-transfer-api) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=JonatasFYano_money-transfer-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=JonatasFYano_money-transfer-api)

# Akelius Code Challenge

This is a Code Challenge for Akelius. The purpose of this challenge is to **design** and **create** a simple implementation of a RESTful API that can perform money transfers between user accounts, assuming the API is invoked by multiple systems and services on behalf of end users. 

# Money Transfer API

## Table of Contents

 - [About the Project](#about-the-project)
 - [Technologies](#technologies)
 - [API Reference](#api-reference)
	 - [Available Services](#available-services)
	 - [HTTP Status Codes](#http-status-codes)
	 - [Swagger Documentation](#swagger-documentation)
 - [Getting Started](#getting-started)
	 - [Requirements](#requirements)
	 - [Installation](#installation) 
	 - [Tests](#tests)
 - [Contributing](#contributing)
 - [Notes](#notes)

## About the Project 

This is a project for money transfer between user accounts. The purpose of it is to **simulate the API calls** performed during the task of transferring money to a different user.  

A money transfer can be considered successful and will not trigger any unwanted behaviour only if:

-   The source and destination accounts both exists
-   There is a positive or sufficient balance on the source account
-   The transfer status is not FAILED

If one or more of these conditions are not satisfied by any means, the money transfer will fail.

> **Note:** The Money Transfer API is implemented not taking into account currency operations or convertions. 

## Technologies

All the platforms, technologies, frameworks and libraries which were used at the time of implementation of this project are listed above: 

- Java 8  
- Maven 3.6.2
- JUnit 4.12
- H2 in-memory Database 1.4.192
- Sonarcloud
- Travis CI

## API Reference

All the developed and available RESTful API Services, HTTP Status Codes and Documentation can be found in this section, in topics above. 

### Available Services

| HTTP METHOD 	| PATH             	| USAGE                 	        |
|-------------	|------------------	|-----------------------	                |
| GET      	| /account   	| Get all accounts from system         	|
| POST         	| /account/{usernameAccount} 	| Get specific account by its username 	|
| POST         	| /account/create     	| Create a new account     	|
| PUT        	| /account/{usernameAccount}/deposit   	| Deposit amount of money in specific account         	|
| PUT        	| /account/{usernameAccount}/withdraw   	| Withdraw amount of money from specific account         	|
| PUT         	| /transaction 	| Perform a transaction between accounts 	|

### HTTP Status Codes

| CODE 	| STATUS             	| MEANING                 	|
|-------------	|------------------	|-----------------------	|
| 200         	| OK 	| The request has succeeded 	|
| 204         	| NO CONTENT 	| The server could not find any content  	|
| 400      	    | BAD REQUEST    	| The request could not be understood by the server        	|
| 500        	| INTERNAL SERVER ERROR   	| The server encountered an unexpected condition         	|

### Swagger Documentation

Link to the Money Transfer Swagger Documentation can be found [here]([https://app.swaggerhub.com/apis-docs/JonatasFYano/money_transfer_api/1.0.0#/Transaction]). 

## Getting Started 

### Requirements 

It is important that you have at least Java 8 as well as Maven 3 installed by the time you will try this project out.  

### Installation

First, you will need to checkout the project from this repository running:

   ```git clone https://github.com/moureauf/moneytransfer.git```

Since this project is built using Maven, you will need to execute with a Maven command:

   ```mvn clean package```

And then, to run the application just execute:

   ```mvn java:exec```

It is also possible to execute instead:

   ```java -jar money_transfer_api-1.0-SNAPSHOT-jar-with-dependencies.jar```

And with that, you will have the Money Transfer API up and running.

### Tests

To execute the tests you will need, with a Maven command, to run: 

   ```mvn clean test```


### Docker Image

It is also possible to download a docker image from Dockerhub witch is generated whenever the CI pipeline is triggered. To do that, run:

   ```docker pull jonatasfyano/money-transfer-api:latest```

This will download the image and to run the container with this project application execute:

   ```docker run -d -p 8001:8001 jonatasfyano/money-transfer-api:latest```

To make sure the application is running properly you can make a GET request to the /account endpoint:

   ```curl http://localhost:8001/account```

## Contributing


If you want to contribute, please ensure your Pull Request follows to the guidelines above:

-  Be aware if you suggestion is not going to be a duplicate one by searching it before. 
-  Make an individual PR for each suggestion, keeping descriptions short and simple but yet descriptive.
-  New categories or improvements are always welcome.

Thank you for your suggestions!

## Notes

Following the requirement to keep things simple, this application produces a working API with a lot of real-world features missing:

-  Authentication
-  Real storage
-  Better separation between repositories and services
-  Error documentation
