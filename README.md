Overview
I choose Java 8, Maven 3, and Spring Boot 2.2.5 to implement this project. It is a sprint boot applicaiton. It could be import to Eclipse as a Maven project. 

REST APIs:
1. Register a user: POST, /register/user.
{
	"name": "guanlei"
}

2. Register a car: POST, /register/car.
{
	"plate": "ABC123"
}

3. Register car availablity: POST, /register/car/availablity
{
	"id": "1", 
	"availableFrom": "2020-02-06T20:58:52",
	"availableTo": "2020-03-06T20:58:52",
	"rentalPrice": "200"
}

4. Search car availability: GET, /search/cars
{
	"availableFrom": "2020-02-10T00:00:00",
	"availableTo": "2020-03-01T20:58:52",
	"maxRentalPrice": "200"
}

5. Book a car: POST, /book/car
{
	"carId": "1", 
	"userId": "2",
	"bookFrom": "2020-02-07T20:58:52",
	"bookTo": "2020-02-10T20:58:52"
}
