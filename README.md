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
Module containing REST Api, database connection and server side web socket logic (sendind new messages to all users with this subject and sending old messages to user that has connected to the client)

### *message-broker-client
Module contains client that runs on Spring and when started it creates socket that listens for new messages and also socket to get all old messages from server

### *message-broker-client
Module contains constants etc.

Application is using web socket to deliver messages to the user, using addres passed by them during registering
JWTToken is used to verify user creditentials
Based on JWTToken spring framework checks user priviliges to access some of Admin only endpoints

Broker is implemented to be high availability application, every message is sent by another thread so when multiple requests are recived, application still can respond to all of them at the same time.

Messages have their expiration date and are deleted from database after expiring. 
Application has it's hardcoded testmessage thread that creates new message on fixed time period.


SQL added to project is a database export that can be used to run application in local envinroment
