#API Shipping status with SpringBoot (java) Backend

## About
This is an RESTfull application to validates the status of a shipping, and return a description about last sub-status.

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
│  build.gradle
└──[src]
│  └──[main]
│     └──[java]
│     └──[resources]
│        │  application.yml #contains springboot cofigurations
│        │  schema.sql  # Contains DB Script to create tables that executes during the App Startup
│        │  data.sql    # Contains DB Script to Insert data that executes during the App Startup (after schema.sql)
│  └──[test] # Contains the test cases to validate the logic implemented in the application.
└──[target]              #Java build files, auto-created after running java build: mvn install
│  └──[classes]
│     └──[public]
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

The statuses (in order) and sub-status available are:
    ● Handling
        ○ Null
        ○ Manufacturing
    ● Ready To Ship
        ○ Ready To Print
        ○ Printed
    ● Shipped
        ○ Null
        ○ Soon Deliver
        ○ Waiting For Withdrawal
    ● Delivered
        ○ Null
    ● Not Delivered
        ○ Lost
        ○ Stolen

And the messages of each sub-statuses are:
    ● Handling
        ○ Null (​ “Le notificamos al vendedor sobre tu compra​ ”)
        ○ Manufacturing (“​ El vendedor tendrá listo tu producto pronto y comenzará el envío​ ”).
    ● Ready To Ship
        ○ Ready To Print (“​ El vendedor está preparando tu paquete​ ”)
        ○ Printed (“​ El vendedor debe despachar tu paquete​ ”)
    ● Shipped
        ○ Null (“​ En Camino​ ”)
        ○ Soon Deliver (“​ Pronto a ser entregado​ ”)
        ○ Waiting For Withdrawal (“​ En agencia​ ”)
    ● Delivered
        ○ Null (“​ Entregado​ ”)
    ● Not Delivered
        ○ Lost (“​ Perdido​ ”)
        ○ Stolen (“​ Robado​ ”)


The application receive a JSON with the next information and structure:

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
       },
    ]
}
```

And response with the description of the las sub-status:
```json
{
    "package" : "Entregado"
}
```

## Enabled End-Points

Link Tool supports these configuration parameters:

| Field    | Type        | Description                                    |
| ---------|-------------|------------------------------------------------|
| endpoint | `string`    | **Required:** endpoint for link data fetching. |
