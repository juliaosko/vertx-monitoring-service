Monitor Service API
===============

Getting started
--------------
This is a demo API for basic service monitoring,
which is built using Vert.x to expose a HTTP/JSON web API to create, read, and delete services and checking the status of the listed services every minute.  

The API is deployed with [Heroku](https://www.heroku.com/) and is configured to be automatically built and deployed on changes to the master branch. 
There's also a demo UI, repo [here](https://github.com/juliaosko/vertx-monitoring-client), ànd feel free to checkout the [live demo](https://vertx-monitoring-client.herokuapp.com/). 


Running it locally
--------------
#### Prerequisites
* Apache Maven
* JDK 8+

Checkout the code.  

Once you have cloned the repo, you can check that it works with:

```
mvn package exec:java
```
This compiles the project and launches the application. 

Browse to http://localhost:8080/services. You should see a list of services. 


#### Project in more detail

The project contains of:

* a `pom.xml` file which defines vert.x dependencies 
* a `main verticle` file (src/main/java/se/unicodr/MainVerticle.java), which deploys:
  * two verticles to deal with HTTP requests (src/main/java/se/unicodr/HttpServerVerticle.java) 
  * one verticle for encapsulating persistence through a file on disk (src/main/java/se/unicodr/ServicePersistenceVerticle.java)
  * one periodic verticle which checks service status in the background (src/main/java/se/unicodr/ServicePersistenceVerticle.java)
* a script `redeploy.sh` that enables automatic compilation and redeployment on code changes

#### Building the project

To build the project, use:

```
mvn clean package
```

which generates a fat-jar` in the `target` directory.
