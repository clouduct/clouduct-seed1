Clouduct Seed Backend
==================================================

This sample code helps get you started with a simple Java web application
deployed by AWS Elastic Beanstalk.

Setup Environment
-----------

Prerequisites:

* install Postgres 9.6
* run scripts/init-db.sql


Running
---------------

Execute:

* ./mvnw spring-boot:run
* open http://127.0.0.1:8080/


REST comms format
-----------------
see https://blog.qmo.io/ultimate-guide-to-api-design/

Tests
-----
You have to add src/test/resources path to the test classpath (Don't know why Intellij does not do that out of the box)
