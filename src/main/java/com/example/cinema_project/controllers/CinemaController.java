package com.example.cinema_project.controllers;


import com.example.cinema_project.models.*;
import com.example.cinema_project.services.CinemaService;
import com.example.cinema_project.services.CustomerService;
import com.example.cinema_project.services.ScreenService;
import com.example.cinema_project.services.ScreeningService;
import org.apache.catalina.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cinemas")
public class CinemaController {
    

    @Autowired
    CinemaService cinemaService;

    @Autowired
    ScreeningService screeningService;

    @GetMapping
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getCinemaById(@PathVariable long id){
        Optional<Cinema> cinema = cinemaService.getCinemaById(id);
        if(cinema.isPresent()){
            return new ResponseEntity<>(cinema.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<Cinema> getCinemaByBranch(@PathVariable String branch){
        Cinema cinema = cinemaService.getCinemaByBranch(branch);
        if(cinema != null){
            return new ResponseEntity<>(cinema, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<List<Movie>> getAllMovies(
            @PathVariable long id,
            @RequestParam Map<String, String> requestParams) {
        String genre = requestParams.get("genre");
        String title = requestParams.get("title");
        List<Movie> movies;
        if(genre != null){
            movies = cinemaService.getMovieByGenre(genre);
        }else if(title != null){
            movies = cinemaService.getMovieByTitle(title);
        }else{
            movies = cinemaService.getAllMovies(id);
        }
        return new ResponseEntity<>(movies, movies.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}/movies/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable long id, @PathVariable long movieId){
        Movie movie = cinemaService.getMovieById(movieId, id);
        if(movie != null){
            return new ResponseEntity<>(movie, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cinemaId}/authentication/{movieId}")
    public ResponseEntity<String> checkTickets(
            @PathVariable long cinemaId,
            @PathVariable long movieId,
            @RequestParam long customerId,
            @RequestParam long screeningId,
            @RequestParam String seat
    ){
        if(cinemaService.checkTicket(screeningId,customerId,seat,cinemaId,movieId)){
            return new ResponseEntity<>("The ticket is authorised!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Fake ticket!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/screens")
    public ResponseEntity<List<Screen>> getAllScreensFromCinema(@PathVariable long id){
        List<Screen> screens = cinemaService.getAllScreens(id);
        return new ResponseEntity<>(screens, HttpStatus.OK);
    }

    @GetMapping("/{id}/revenue")
    public ResponseEntity<Double> getRevenue(@PathVariable long id){
        double revenue = screeningService.getTotalRevenue(id);
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/movies/{movieId}")
    public ResponseEntity<Long> cancelMovie(@PathVariable long id, @PathVariable long movieId) {
        cinemaService.cancelMovie(movieId, id);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{id}/movies")
    public ResponseEntity<Cinema> addMovieToCinema(@RequestBody Movie movie, @PathVariable long id){
        Cinema newMovie = cinemaService.addNewMovieToCinema(movie, id);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/movies/{movieId}")
    public ResponseEntity<Cinema> addCurrentMovieToCinema(@PathVariable long id, @PathVariable long movieId){
        Cinema updateCinema = cinemaService.addCurrentMovieToCinema(movieId, id);
        return new ResponseEntity<>(updateCinema, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/screens")
    public ResponseEntity<Cinema> addScreenToCinema(@RequestBody Screen screen, @PathVariable long id){
        Cinema newScreen = cinemaService.addScreenToCinema(screen, id);
        return new ResponseEntity<>(newScreen, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Cinema> createCinema(@RequestBody Cinema cinema) {
        Cinema savedCinema = cinemaService.addNewCinema(cinema);
        return new ResponseEntity<>(savedCinema, savedCinema == null ? HttpStatus.ALREADY_REPORTED : HttpStatus.CREATED);
    }

}