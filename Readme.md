# Currency Exchange and Discount Calculation

## Description
This application mainly focus on calculating price and discounts.The APIs for discount calculation is only supported now.
Remaining functionality will be added later

## Integration 
1. For real-time currency exchange calculations, the [Exchange Rate API](https://www.exchangerate-api.com/) is used.

## Configuration properties
1. Exchange Rate API
   1. exchange.api.key : Unique Key  has to be generated 
   2. exchange.api.url - API url


## How to Run
1. Clone the repository.
2. Run `mvn spring-boot:run`.
3. Use Postman or curl to test the API.

## How to test APIs
1. api/login is used to create token (user name = 'admin' and password = 'password')
2. /api/calculate is used to calculate discount 
3. Sample collection is attached under collection folder

## How to generate coverage reports
1. Run `mvn test`
2. Reports will be generated in target/jacoco-reports folder

## Testing
Run unit tests using:
```bash
mvn test
