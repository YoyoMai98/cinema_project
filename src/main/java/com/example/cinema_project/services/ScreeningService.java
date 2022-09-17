package com.example.cinema_project.services;

import com.example.cinema_project.models.*;
import com.example.cinema_project.repositories.CinemaRepository;
import com.example.cinema_project.repositories.MovieRepository;
import com.example.cinema_project.repositories.ScreenRepository;
import com.example.cinema_project.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

    @Autowired
    CinemaRepository cinemaRepository;

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

        double nextHour = nextScreening.getShowTime().getHour();
        double nextMinute = nextScreening.getShowTime().getMinute();

        LocalTime endTime = calculateEndTime(movie, screening);

        int endHour = endTime.getHour();
        int endMinute = endTime.getMinute();

        if(endHour <= 3) endHour += 24;
        if(endHour >= nextHour || (endHour == nextHour && endMinute >= nextMinute)) return false;

        return true;
    }

    public Screening addCustomerToScreening(long customerId, Long screeningId, int seatNumber, long cinemaId){
        Optional<Cinema> cinema = cinemaService.getCinemaById(cinemaId);
        if(!cinema.isPresent()) return null;
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
            double revenueForOneScreening = cinema.get().getRevenue() + screening.getPrice();
            cinema.get().setRevenue(revenueForOneScreening);
            cinemaRepository.save(cinema.get());
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
                LocalTime endTime = calculateEndTime(movie,screening.get());
                screening.get().setMovie(movie);
                screening.get().setEndTime(endTime);
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

    public List<Integer> getSeatList(long screenId, long screeningId, long cinemaId){
        Screening screening = getScreeningById(screeningId,screenId,cinemaId);
        if(screening == null) return null;
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

    public double getTotalRevenue(long id){
        Optional<Cinema> cinema = cinemaService.getCinemaById(id);
        if(!cinema.isPresent()) return 0;
        List<Screen> screens = cinema.get().getScreens();
        double revenue = cinema.get().getRevenue();
        for(Screen screen : screens){
            revenue += bookingService.getRevenueOfOneScreen(screen);
        }
        cinema.get().setRevenue(revenue);
        cinemaRepository.save(cinema.get());
        return revenue;
    }

    public LocalTime calculateEndTime(Movie movie, Screening screening){
        double lengthToHour = Math.floor((double)movie.getLength()/60);
        double lengthToMinute = Math.floor(movie.getLength()%60);
        double endHour = lengthToHour + screening.getShowTime().getHour();
        double endMinute = lengthToMinute + screening.getShowTime().getMinute();
        if(endHour >= 24) endHour %= 24;
        if(endMinute >= 60){
            endHour += (endMinute / 60);
            endMinute %= 60;
        }
        return LocalTime.of((int)endHour, (int)endMinute);
    }
}
