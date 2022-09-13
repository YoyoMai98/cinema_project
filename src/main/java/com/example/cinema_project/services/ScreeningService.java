package com.example.cinema_project.services;

import com.example.cinema_project.models.Customer;
import com.example.cinema_project.models.Movie;
import com.example.cinema_project.models.Screen;
import com.example.cinema_project.models.Screening;
import com.example.cinema_project.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ScreeningService {

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ScreenService screenService;

    public Screening addCustomerToScreening(Long customerId, Long screeningId){
        Screening screening = screeningRepository.findById(screeningId).get();
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if(customer.isPresent()){
            List<Customer> customers = screening.getCustomers();
            customers.add(customer.get());
            screening.setCustomers(customers);
            screeningRepository.save(screening);
        }
        return screening;
    }

//    todo: if movie is not present
//    screening -> movie Screen 1 => screening 1 (9 am), screening 2 (2.30 pm)
//    screening 1 (9 am) plays 1 movie
    public Screening addMovieToScreening(long movieId, long screeningId, long screenId){
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        Optional<Movie> movie = screenService.getMovieById(movieId);
        if(screening.isPresent()){
            if(movie.isPresent()){
                screening.get().setMovie(movie.get());
                screeningRepository.save(screening.get());
            }
            return screening.get();
        }else{
            Screen screen = screenService.getScreenById(screenId);
            Screening newScreening = new Screening(movie.get(), screen);
            return newScreening;
        }
    }
}
