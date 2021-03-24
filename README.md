# message-broker
##Springboot message broker application

Application allows to send messages to registered users based on interests choosed by them
Stack:
* Spring 
* Hibernate
* Maven
* mysql
* JSONWebToken

Modules:
### *message-broker-core
Module containing REST Api, database connection and web socket logic

Application is using web socket to deliver messages to the user, using addres passed by them during registering
JWTToken is used to verify user creditentials
Based on JWTToken spring framework checks user priviliges to access some of Admin only endpoints

Broker is implemented to be high availability application, every message is sent by another thread so when multiple requests are recived, application still can respond to all of them at the same time.

Messages have their expiration date and are deleted from database after expiring. 
Application has it's hardcoded testmessage thread that creates new message on fixed time period.


SQL added to project is a database export that can be used to run application in local envinroment
