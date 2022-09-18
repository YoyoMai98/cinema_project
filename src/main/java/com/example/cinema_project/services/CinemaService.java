package com.example.cinema_project.services;

import com.example.cinema_project.models.*;
import com.example.cinema_project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    ScreenRepository screenRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    BookingRepository bookingRepository;

    public List<Movie> getAllMovies(long cinemaId){
        Cinema cinema= cinemaRepository.findById(cinemaId).get();
        return cinema.getMovies();
    }

    public Cinema addNewMovieToCinema(Movie movie, long cinemaId){
        Cinema cinema = cinemaRepository.findById(cinemaId).get();
        List<Movie> movies = cinema.getMovies();
        movies.add(movie);
        cinema.setMovies(movies);
        movieRepository.save(movie);
        cinemaRepository.save(cinema);
        return cinema;
    }

    public Cinema addCurrentMovieToCinema(long movieId, long cinemaId){
        Cinema cinema = cinemaRepository.findById(cinemaId).get();
        Movie movie = movieRepository.findById(movieId).get();
        List<Movie> movies = cinema.getMovies();
        movies.add(movie);
        cinema.setMovies(movies);
        movieRepository.save(movie);
        cinemaRepository.save(cinema);
        return cinema;
    }

    public Cinema addScreenToCinema(Screen screen, long cinemaId){
        Cinema cinema = cinemaRepository.findById(cinemaId). get();
        List<Screen> screens = cinema.getScreens();
        screens.add(screen);
        cinema.setScreens(screens);
        screen.setCinema(cinema);
        screenRepository.save(screen);
        cinemaRepository.save(cinema);
        return cinema;
    }

    public void cancelMovie(long id, long cinemaId){
        Optional<Cinema> cinema = getCinemaById(cinemaId);
        Movie movie = getMovieById(id, cinemaId);
        if(!cinema.isPresent() || movie == null) return;
        List<Movie> movies = cinema.get().getMovies();
        List<Screening> screenings = new ArrayList<>();
        List<Screen> screens = cinema.get().getScreens();
        for(Screen screen : screens){
            screenings.addAll(screeningRepository.findByScreenIdAndMovieId(screen.getId(),id));
        }
        for(Screening screening : screenings){
            screeningRepository.delete(screening);
        }
        movies.remove(movie);
        List<Cinema> cinemas = movie.getCinemas();
        cinemas.remove(cinema);
        cinema.get().setMovies(movies);
        movie.setCinemas(cinemas);
        cinemaRepository.save(cinema.get());
        movieRepository.save(movie);
    }
    
    public Movie getMovieById(long id, long cinemaId){
        Cinema cinema = cinemaRepository.findById(cinemaId).get();
        List<Movie> movies = cinema.getMovies();
        for(Movie movie : movies){
            if(movie.getId() == id){
                return movie;
            }
        }
        return null;
    }

    public List<Screen> getAllScreens(long cinemaId){
        return screenRepository.findByCinemaId(cinemaId);
    }

    public List<Movie> getMovieByGenre(String genre){
        return movieRepository.findByGenreContainingIgnoreCase(genre);
    }

    public List<Movie> getMovieByTitle(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Cinema> getAllCinemas(){
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getCinemaById(Long id){
        return cinemaRepository.findById(id);
    }

    public Cinema addNewCinema(Cinema cinema){
        cinemaRepository.save(cinema);
        return cinema;
    }

    public boolean checkTicket(long screeningId, long customerId, String seatNumber, long cinemaId, long movieId){
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if(!screening.isPresent()) return false;
        Booking booking = bookingRepository.findByCustomerIdAndScreeningIdAndSeatNumber(customerId,screeningId,seatNumber.toUpperCase());
        Movie movie = movieRepository.findByCinemasIdAndId(cinemaId, movieId);
        Movie providedMovie = screening.get().getMovie();
        if(movie != null && movie.equals(providedMovie) && booking != null) return true;
        else return false;
    }
}
