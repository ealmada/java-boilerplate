# ASAPP Chat Backend Challenge v1
### Overview
This is a java based boilerplate which runs an HTTP Server configured to answer the endpoints defined in 
[the challenge you received](https://backend-challenge.asapp.engineering/).
All endpoints are configured in src/main/java/com/asapp/backend/challenge/Application.java and if you go deeper to the
Routes and Filters passed as second parameters, you will find a TODO comment where you are free to implement your solution.

### How to run it
```
./gradlew run
```
##### Note

I'm using port 8085, because I'm running other application on 8080. You can change this in the app.

You can pack it in a jar as well using gradle.

I have used the serialize/unserialize custom with Jackson as it was part of the boiler plate.

I have integrated Spring with SparkJava microframework pattern that is part of the boiler plate.

Integration tests have been added. (I should put more tests but didnt have enough time)

Some validations are pending.

I've used JWT for the authorization process.

It's dockerized.

A k8s manifest has been provided also.

I've exported a postman collection and it's located in the root directory.

There are things pending to improve like decoupling the scheduler for cleaning up the tokens and adding a queue for processing the messages.

##### Note
You can remove/modify this file for documenting your solution.

