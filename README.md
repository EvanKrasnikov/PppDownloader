# PppDownloader
A web application for retrieving PPP GNSS data

## How to use
#### In order to run application:
```bash
git clone https://github.com/EvanKrasnikov/PppDownloader.git 
cd PppDownloader
./mvnw clean install -DskipTests
java -jar target/PppDownloader-0.0.1-SNAPSHOT.war
```
Web service will be available at **_localhost:8080_**

#### In order to run tests and see allure report:
```bash
git clone https://github.com/EvanKrasnikov/PppDownloader.git
cd PppDownloader
./mvnw clean install -DskipTests
./mvnw clean test
./mvnw allure:serve
```
Report will be opened in default browser

## TODO
- Add handler for all HTTP forms
- Add provider filtering
- Compose filename based on provider and date

