# Annual Net Microservice

The microservice calculates the Annual net amount from the monthly gross salary.
The endpoints are available in the following endpoints using the HTTP POST method:

* http://localhost:8080/annual-net/calculation/8000
* http://localhost:8098/annual-net/calculation/8000

The response will look like as following:

{
"value": "391.99",
"description": "Annual net"
}

The actuator will be accessible via the following links:

* http://localhost:8080/annual-net/actuator
* http://localhost:8098/annual-net/actuator


The first endpoint is accessible via Spring api Gateway and the second one through the server port.

Swagger it is available via the following endpoints:

* http://localhost:8080/swagger-ui/?urls.primaryName=annual-net
* http://localhost:8098/annual-net/v3/api-docs

The first endpoints is accessible via Spring api Gateway and the second ones through the server port.