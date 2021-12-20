## TaskManager
### Prerequisites
* npm 8.3.0
* Maven >= 3.6.0
* Java jdk 11
### Setup
* Inside the frontend\taskmanager-fs folder run `nmp install --force` to get the dependencies.
* Inside the frontend\taskmanager-fs folder run `nmp run generate:api` to generate the DTO classes and the communication services with the backend.
* Inside the backend\taskmanagerFS folder run `mvn clean install` to get the dependencies and run the tests.
### Run the backend
* Go to the backend\taskmanagerFS\boot folder and run `mvn clean spring-boot:run -Dspring-boot.run.fork=false` to run the backend application.
* Navigate to the http://localhost:8080/taskmanagerFS/swagger-ui.html url to access to the swagger view and test the services manually.
* Alternatively, you can import the openapi specification (backend\taskmanagerFS\api-rest\src\main\resources\openapi.yml) on postman and test the backend through it.
### Run the frontend
* Inside the frontend\taskmanager-fs folder run `nmp start`.
* Navigate to the http://localhost:4200/ url to access to the app view.
### Available Users
To authenticate in the application there are 2 available users:
* **user**/**pass**
* **admin**/**pass**
