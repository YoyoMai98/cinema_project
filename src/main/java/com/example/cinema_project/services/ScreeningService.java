package com.example.cinema_project.services;

import com.example.cinema_project.models.*;
import com.example.cinema_project.repositories.MovieRepository;
import com.example.cinema_project.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ScreenService screenService;

    @Autowired
    CinemaService cinemaService;

    @Autowired
    BookingService bookingService;

    public List<Screening> getAllScreenings(long screenId){
        return screeningRepository.findByScreenId(screenId);
    }

    public Screening getScreeningById(Long screeningId, Long screenId, long cinemaId){
        Optional<Cinema> cinema = cinemaService.getCinemaById(cinemaId);
        if(!cinema.isPresent()) return null;
        Screen screen = screenService.getScreenById(screenId, cinemaId);
        if(screen == null) return null;
        List<Screening> screenings = screen.getScreenings();
        for(Screening screening : screenings){
            if(screening.getId() == screeningId){
                return screening;
            }
        }
        return null;
    }

    public Screening addNewScreening(Screening screening){
        screeningRepository.save(screening);
        return screening;
    }

    public boolean canShow(Screening screening, Movie movie){
        Screen screen = screening.getScreen();
        screenService.screeningsInOrderByShowTime(screen);
        List<Screening> screenings = screen.getScreenings();
        Screening nextScreening = new Screening();
        for(int i = 0; i < screenings.size(); i++){
            if(screenings.get(i) == screening && i != screenings.size()-1){
                nextScreening = screenings.get(i+1);
                break;
            }else if(i == screenings.size()-1){
                return true;
            }
        }
        double lengthToHour = Math.floor((double)movie.getLength()/60 * 100)/100;
        double endTime = lengthToHour + screening.getShowTime();
        double decimalPart = Math.floor((endTime * 100) % 100);
        endTime = Math.floor((endTime * 100) / 100);
        if(decimalPart >= 60){
            double addToEndTime = Math.floor(decimalPart / 60);
            decimalPart %= 60;
            endTime = endTime + addToEndTime + decimalPart/100;
        }
        if(endTime >= nextScreening.getShowTime()){
            return false;
        }
        return true;
    }

    public Screening addCustomerToScreening(long customerId, Long screeningId, int seatNumber){
        Screening screening = screeningRepository.findById(screeningId).get();
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if(customer.isPresent()){
            if( (!isSeatOccupied(seatNumber, screening.getSeats())) ||
                (screening.getSeats().size() > screening.getScreen().getCapacity())||
                (seatNumber > screening.getScreen().getCapacity())
            ){
                return null;
            }
            bookingService.addNewBooking(customer.get(),screening,seatNumber);
            addNewSeat(seatNumber,screening);
        }
        return screening;
    }


    public Screening addMovieToScreening(long movieId, long screeningId, long screenId, long cinemaId){
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        Movie movie = cinemaService.getMovieById(movieId, cinemaId);
        Screen screen = screenService.getScreenById(screenId, cinemaId);
        if (screen == null){
            return null;
        }
        if(screening.isPresent()){
            if(movie != null && canShow(screening.get(), movie)){
                screening.get().setMovie(movie);
                movie.getScreenings().add(screening.get());
                List<Screening> screenings = movie.getScreenings();
                screenings.add(screening.get());
                movie.setScreenings(screenings);
                movieRepository.save(movie);
                screeningRepository.save(screening.get());
                screenService.addScreeningToScreen(screenId, screeningId, cinemaId);
            }
            return screening.get();
        }else{
            return null;
        }
    }




    public Screening removeMovieFromScreening(long movieId, long screeningId, long screenId, long cinemaId){
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        Movie movie = cinemaService.getMovieById(movieId, cinemaId);
        if(screening.isPresent()){
            if(movie != null){
                screening.get().setMovie(null);
                screeningRepository.save(screening.get());
                screenService.addScreeningToScreen(screenId, screeningId, cinemaId);
            }
            return screening.get();
        }else{
            return null;
        }
    }

    public List<Integer> getSeatList(Screening screening){
        return screening.getSeats();
    }

    public boolean isSeatOccupied(int seatNumber, List<Integer> seats){
        for(int seat : seats){
            if(seatNumber == seat) return false;
        }
        return true;
    }

    public List<Integer> addNewSeat(int seatNumber, Screening screening){
        List<Integer> seats = screening.getSeats();
        seats.add(seatNumber);
        screening.setSeats(seats);
        screeningRepository.save(screening);
        return seats;
    }
}
