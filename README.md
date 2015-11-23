# introsde-2015-assignment-2

**Introduction to Service Design and Engineering | University of Trento**

This is the second assignment of the course **Introduction to Service Design and Engineering** of the **University of Trento**.

In this assignment we have to provide a RESTFUL web service, which manages People's Health Profile.

## Project Structure

The assignment is divided in 4 packages:

* `CLIENT`: which manages the Client part;
* `DAO`: which manages the comunication with the database;
* `MODEL`: which manages the representation of the database scructure;
* `RESOURCES`: which manages the operations in the database;
* The remaining Java classes manage the standalone server.

## Authors

This project was developed my Damiano Fossa. The client part is connecting to Charles Ferrari's server.

[Charles Ferrari's repository] [1]

## Servers

* [Server on Heroku] [3]
* [Server used by the client] [2]


## How to run the server

* GIT Clone to your machine
* Go to the project folder 
* Type `ant install`

#### REST Functions handle by the server (XML and JSON):

* GET {address}/person
* POST {address}/person
* GET {address}/person/{id}
* PUT {address}/person/{id}
* DELETE {address}/person/{id}
* GET {address}/person/{id}/{measureType}
* GET {address}/person/{id}/{measureType}/{id}
* GET {address}/measureTypes




## How to run the client

* GIT Clone to your machine
* Go to the project folder 
* Type `ant execute.client`
* The result is saved in log files (one for XML and one for JSON)


[1]: https://github.com/ferraricharles/introsde-2015-assignment-2
[2]: https://agile-tundra-4340.herokuapp.com/sdelab
[3]: https://secret-bastion-8847.herokuapp.com/sdelab
