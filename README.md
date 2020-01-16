#API Shipping status with SpringBoot (java) Backend

## About
This is an RESTfull application to validates the status of a shipping and return a description about last sub-status.

### Technology Stack
Component           | Technology
---                 | ---
Backend (REST)      | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
In Memory DB        | H2
Persistence         | JPA (Using Spring Data)
Server Build Tools  | Maven(Java)
Server Hosted       | [heroku](https://www.heroku.com/)

### Heroku hosted
The url of the application to connect with the API is:
[https://app-shipping-status.herokuapp.com/api-shipping]

## Folder Structure
```bash
PROJECT_FOLDER
│  README.md
│  pom.xml
└──[src]
│  └──[main]
│  │   └──[java]
│  │   └──[resources]
│  │      └──[db]
│  │         └──[changelog]
│  │            └──[schema]
│  │                historicalQueryShippingSchema.sql  # Contains DB Script to create tables that executes during the App Startup.
│  │            db.changelog-master.yaml  # It's a Script to orchestra how to create the tables on the database.
│  │  application.yml #contains springboot configurations
│  │
│  └──[test] # Contains the test cases to validate the logic implemented in the application.
└──[target]              #Java build files, auto-created after running java build: mvn install
      └──[classes]
```

## Prerequisites
Ensure you have this installed before proceeding further
- Java 8
- Maven 3.3.9+

## In Memory DB (H2)
I have included an in-memory database for the application. Database schema and sample data for the app is created everytime the app starts, and gets destroyed after the app stops, so the changes made to to the database are persistent only as long as the app is running
<br/>
Creation of database schema and data are done using sql scripts that Springs runs automatically.
To modify the database schema or the data you can modify [schema.sql](./src/main/resources/db/changelog/historicalQueryShippingSchema.sql) which can be found at
 `/src/main/resources/db`


### Build Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present
mvn clean install
```

## Logical Business:
***
### The statuses (in order) and sub-statuses (and message of those) available are:

- Handling
    - Null (​"Le notificamos al vendedor sobre tu compra​").
    - Manufacturing ("El vendedor tendrá listo tu producto pronto y comenzará el envío​").
- Ready To Ship
    - Ready To Print ("El vendedor está preparando tu paquete​").
    - Printed ("El vendedor debe despachar tu paquete​").
- Shipped
    - Null ("En Camino").
    - Soon Deliver ("Pronto a ser entregado​").
    - Waiting For Withdrawal ("En agencia")
- Delivered
    - Null ("Entregado​")
- Not Delivered
    - Lost ("Perdido​")
    - Stolen ("Robado​")


### Accessing Application

| Concept               | URL                                               | Description                                                                                               |
| ------                | ------------                                      | ----------                                                                                                |
| H2 Database           | http://localhost:9001/h2-console                  | Driver:`org.h2.Driver` <br/> JDBC URL:`jdbc:h2:mem:demodb` <br/> User Name:`sa` <br/> Password: M3li2020  |
| (POST) package        | http://localhost:9001/api-shipping/package        | End-point to validate the status of a Shipping.                                                           |
| (GET) health          | http://localhost:9001/api-shipping/health         | End-point to know if the application response.                                                            |
| (GET) statistics      | http://localhost:9001/api-shipping/statistics     | End-point to query information about the movements of shipments.                                          |


### Package (POST)
This endpoint allows to get the last status about some shipping. The request-body(JSON) to send to the endpoint is:

```
id      ->  identifier of shipping
inputs  -> List of status of shipping
```
### Request Example
```json
{
    "id": "28123B",
    "inputs": [
       {
           "status": "delivered",
           "substatus": null
       }
    ]
}
```

And the application response with the description of the last sub-status:
```json
{
    "package" : "Entregado"
}
```

### Health (GET)
You can to know if the application is still responding:

```bash
curl -X GET --header 'Accept: application/json' 'http://localhost:9001/api-shipping/health'
```

And if the application is active, you should can see the message:
```
I'm alive!
```

### Statistics (GET)
This endpoint provides information on how many requests were processed and the number of those were successful or error.
If you want, you can provide a range of dates (Format yyyy-MM-dd) to filter the result, but if you set the attributes to "null", the service
It will process all the information.

### Request example
```bash
curl -X GET --header 'Accept: application/json' 'http://localhost:9001/api-shipping/statistics?dateFrom=2020-01-12&dateTo=2020-01-17'
```
And the application will response:
```json
{
    "successfulRequests": 2,
    "errorRequests": 2,
    "totalRequests": 4,
    "dateFrom": "12-01-2020",
    "dateTo": "17-12-2020"
}
```


### Nivel 4 (Challenge):
- ¿Qué necesita el sistema para poder rastrear los paquetes en tiempo real?
    - Sería necesario tener el número de trackeo del vehículo que transporta el envío, para consultar a la api del empresa de transporte por
     es estado del vehículo que lleva ese paquete.

- ¿Cómo implementarías la geolocalización de los paquetes?
    - Lo implementaría agregando las coordenadas geográficas de los lugares por donde el paquete va circulando, cada vez que cambia de
    estado.