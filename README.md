#API Shipping status with SpringBoot (java) Backend

## About
This is an RESTfull application to validates the status of a shipping and return a description about last sub-status.

### Technology Stack
Component         | Technology
---               | ---
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
In Memory DB      | H2
Persistence       | JPA (Using Spring Data)
Server Build Tools| Maven(Java)

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
To modify the database schema or the data you can modify [schema.sql](./src/main/resources/schema.sql) and [data.sql](./src/main/resources/data.sql) which can be found at `/src/main/resources`



### Build Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present
mvn clean install
```

## Logical Business:
### The statuses (in order) and sub-statuses (and message of those) available are:
    * Handling
        * Null (​"Le notificamos al vendedor sobre tu compra​").
        * Manufacturing ("El vendedor tendrá listo tu producto pronto y comenzará el envío​").
    * Ready To Ship
        * Ready To Print ("El vendedor está preparando tu paquete​").
        * Printed ("El vendedor debe despachar tu paquete​").
    * Shipped
        * Null ("En Camino").
        * Soon Deliver ("Pronto a ser entregado​").
        * Waiting For Withdrawal ("En agencia")
    * Delivered
         * Null ("Entregado​")
    * Not Delivered
        * Lost ("Perdido​")
        * Stolen ("Robado​")


### Accessing Application

| Concept               | Type                                              | Description                                                                       |
| ------                | ------------                                      | ----------                                                                        |
| H2 Database           | http://localhost:9001/h2-console                  | Driver:`org.h2.Driver` <br/> JDBC URL:`jdbc:h2:mem:demodb
`<br/> User Name:`sa` <br/> Password: M3li2020  |
| (POST) package        | http://localhost:9001/api-shipping/package        | End-point to validate the status of a Shipping.                                   |
| (GET) health          | http://localhost:9001/api-shipping/health         | End-point to know if the application response.                                    |



If you get the status about some shipping have to send a JSON with the next information and structure:

```json
{
    "id": "28123B",
    "inputs": [
        {
            "status": "ready_to_ship",
            "substatus": "printed"
        },
        {
            "status": "shipped",
            "substatus": null
        },
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


If you want to know if the application still response, the end-point is:

```bash
curl -X GET --header 'Accept: application/json' 'http://localhost:9001/api-shipping/health'
```

And if the application is active, you should see the message:

```
I'm alive!
```
