# Cinema API

A seamless backend experience among customers, screens and movies for the sector-leading BrightCinema chain of cinemas.

## Who we are
We are the Debug Demons team behind Cinema - 4 members of Cohort 6 from the Bright Network Technology Academy!

- Yongran
	- [Github](https://github.com/YoyoMai98)
	- [LinkedIn](https://www.linkedin.com/in/yongran-mai/)

- Tariq
	- [Github](https://github.com/Tariq-Hennache)
	- [LinkedIn](https://www.linkedin.com/in/tariq-hennache/)
- Guy
	- [Github](https://github.com/GuyTheCoder)
	- [LinkedIn](https://www.linkedin.com/in/guy-chalk/)
- Kat
	- [Github](https://github.com/katfagg)
	- [LinkedIn](https://www.linkedin.com/in/katfagg/)
	- [Email](katjfagg@gmail.com)


## Table of Contents
- [Project Overview](#project-overview)
- [Install and Run](#install-and-run)
- [Project Structure](#project-structure)
- [Minimum Viable Product (MVP)](#minimum-viable-product-mvp)
- [Extensions](#extensions)
- [Using the API - HTTP Requests](#using-the-api---http-request)
- [Further Extensions](#further-extensions)
- [Acknowledgements](#acknowledgments)


## Project Overview
Given the broad scope to our cinema-management-API:
- We have designed a cinema API which uses POJOs and allows you to select information from our different models and get this brought up in Postman and Postico.
- A handy API allows cinemas to manage revenue, screens, movies and show time. It also allows customers to book movies' tickets.
- We have used Java as our primary Backend language, but have also used SQL for the data.

How we worked together:
- As part of the Bright Network Technology Academy, we were assigned a group project to research, plan and develop an API within a week.
- Our main challenges included removing dependency loops between screening, screen, movie and cinema models, fixing foreign key constraints, and version control errors. 
- To mitigate our issues we regularly discussed them with one another, and would ask for help when we needed it. Our teamwork was key for getting us to complete this project together.

## Install and Run

1. This Spring Boot API runs on Java 17. Ensure you have an IDE, API platform and PostgreSQL client before running this API. We used IntelliJ, Postman & Postico, respectively. 
2. Clone the repository `git clone git@github.com:YoyoMai98/cinema_project.git`. Open project in IDE.
3. Create a database called “cinema_app” (`createdb cinema_app`); this will allow you to use your PostgreSQL client to view your tables.
4. Run the application and open your API platform. Interact with the API and make HTTP queries via `localhost:8080/{query}` in the CLI.

## Project Structure
### Unified Modelling Language Diagrams (UML):

- MVP

![UML](https://github.com/YoyoMai98/cinema_project/blob/main/Class_Diagram_MVP.png)

![UML](https://github.com/YoyoMai98/cinema_project/blob/main/UML_MVP.png)

- Extension

![UML](https://github.com/YoyoMai98/cinema_project/blob/main/extension_uml_model.png)

![UML](https://github.com/YoyoMai98/cinema_project/blob/main/extension_uml_controller.png)

### Entity Relationship Diagram (ERD):

- MVP

![ERD](https://github.com/YoyoMai98/cinema_project/blob/main/ERD_MVP.png)

- Extension

![ERD](https://github.com/YoyoMai98/cinema_project/blob/main/extension_erd.png)

## Minimum Viable Product (MVP)

For our MVP, we created an API with partial functionalities to manage cinemas. It focuses on one cinema and allows cinemas to add new movies, cancel movies, search for movies, add new screens and manage customers.

It includes 5x models classes:

- Cinema
- Customer
- Movie
- Screen
- Screening

## Extensions

What Yongran has expanded on this API:

- Handle multiple cinemas with different branches
- Change `Cinema` and `Movie` relationship to many-to-many
- Add `showTime` and `endTime` properties in `Screening` model
- Create `Booking` model to handle seats and price for a ticket
- Add `revenue` property in cinema
- Ticket Authentication

It allows users to manage revenue, manage multiple cinemas, cancel movies for one cinema, manage show time and end time for movies, book movies' tickets with chosen seats and validate a ticket.


## Using the API - HTTP Requests
Notes:

- Filters for String - this allowed us to use derived queries with "ContainingIgnoreCase" to reduce user input error and offer multiple options in e.g. movies genres.

`localhost:8080/…`

| HTTP Request Path                                                  | Request Type | Description                                      |
|:-----------------------------------------------------------|:-------------|:------------------------------------------------------|
| `.../cinemas` |`GET` | Get All Cinemas |
|`.../cinemas/{id}`| `GET` | Get Cinema By ID |
|`.../cinemas/branch/{branch}`| `GET` | Get Cinema By Branch |
|`.../cinemas/{id}/movies` |`GET` | Get All Movies  |
|`.../cinemas/{id}/movies/{movieId}`|`GET`| Get Movie By ID|
|`.../cinemas/{id}/movies?genre={genre}`|`GET`| Get Movie By Genre |
|`.../cinemas/{id}/movies?title={title}`|`GET`| Get Movie By Title|
|`.../cinemas/{id}/screens`|`GET`| Get All Screens by Cinema ID |
|`.../cinemas/{id}/revenue`|`GET`| Get Total Revenue |
|`.../cinemas/{cinemaId}/authentication/{movieId}?customerId={customerId}&screeningId={screeningId}&seat={seat}`|`GET`| Authenticate Ticket |
| `.../cinemas/{id}/movies/{movieId}`  | `DELETE`  | Cancel Movie  |
| `.../cinemas/{id}/movies`  | `POST`  | Add Movie To Cinema  |
| `.../cinemas/{id}/movies/{movieId}`  | `POST`  | Add Existed Movie To Cinema  |
| `.../cinemas/{id}/screens`  | `POST`  | Add Screen To Cinema |
| `.../cinemas`  | `POST`  | Create Cinema  |
| `.../customers` | `GET` | Get All Customers |
|`.../customers/{id}` | `GET` | Get Customer By ID |
| `.../customers` | `POST` | Create New Customers |
| `.../screens` | `GET` | Get All Screens 
| `.../screens/{id}?cinemaId={cinemaId}` | `GET` | Get Screen By ID |
| `.../screens/{screenId}/screenings` | `GET` | Get All Screenings |
| `.../screens/{screenId}/screenings/{id}?cinemaId={cinemaId}` | `GET` | Get Screening By ID |
| `.../screens/screenings/{id}/bookings`  | `GET`  | Get All Bookings By Screening ID |
| `.../screens/{screenId}/screenings/{screenId}/seats?cinemaId={cinemaId}`  | `GET`  | Get All Occupied Seats By Screening ID |
| `.../screens` | `POST` | Create New Screen |
| `.../screens/{screenId}?screeningId={screeningId}&cinemaId={cinemaId}` | `POST` | Create/Add Existed Screening To Screen |
| `.../screens/{screenId}/screenings?cinemaId={cinemaId}` | `POST` | Create New Screening To Screen |
| `.../screens/{screenId}/screenings/{screeningId}/customers/{customerId}?cinemaId={cinemaId}&seat={seat}` | `POST` | Add Customer To Screening |
| `.../screens/{screenId}/screenings/{screeningId}/movies/{movieId}?cinemaId={cinemaId}` | `POST` | Add Movie To Screening |
| `.../screens/{screenId}?cinemaId={cinemaId}&screeningId={screeningId}` | `DELETE` | Delete Screening By ID |
| `.../screens/{screenId}?cinemaId={cinemaId}&screeningId={screeningId}&movieId={movieId}` | `DELETE` | Delete Movie By ID |


## Further Extensions

Here are a few ideas on how we would like to expand on this API:
- Create `Genre` table to list movies’ genres in one cinema


## Acknowledgements
We would like to thank the [BNTA](https://techacademy.brightnetwork.co.uk/) team and especially to our trainers Anna, Colin, Eoan, Richard and Zsolt!
