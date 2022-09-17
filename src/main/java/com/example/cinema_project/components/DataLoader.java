package com.example.cinema_project.components;

import com.example.cinema_project.models.*;
import com.example.cinema_project.repositories.*;
import com.example.cinema_project.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner{

        @Autowired
        CustomerRepository customerRepository;

        @Autowired
        MovieRepository movieRepository;

        @Autowired
        ScreeningRepository screeningRepository;

        @Autowired
        ScreenRepository screenRepository;

        @Autowired
        CinemaRepository cinemaRepository;

        @Autowired
        CinemaService cinemaService;

        public void run(ApplicationArguments args) throws Exception {

            Customer customer = new Customer("Tariq");
            Customer customer1 = new Customer("Yongran");
            Customer customer2 = new Customer("Kat");
            Customer customer3 = new Customer("Guy");
            Customer customer4 = new Customer("Jim");
            Customer customer5 = new Customer("Jane");
            Customer customer6 = new Customer("Janet");
            Customer customer7 = new Customer("Jeffery");
            Customer customer8 = new Customer("John");
            Customer customer9 = new Customer("Joe");
            Customer customer10 = new Customer("Jack");

            customerRepository.save(customer);
            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
            customerRepository.save(customer4);
            customerRepository.save(customer5);
            customerRepository.save(customer6);
            customerRepository.save(customer7);
            customerRepository.save(customer8);
            customerRepository.save(customer9);
            customerRepository.save(customer10);

            Cinema cinema = new Cinema("Not Cineworld");
            cinemaRepository.save(cinema);

            Movie movie = new Movie("Titanic", 194, 1997, "Historical Drama");
            Movie movie1 = new Movie("300", 117, 2006, "Action");
            Movie movie2 = new Movie("The Godfather", 175, 1972, "Action");
            Movie movie3 = new Movie("The Incredibles", 105, 2004, "Childrens");
            Movie movie4 = new Movie("I, Robot", 115, 2004, "Sci-fi");
            Movie movie5 = new Movie("xXx", 124, 2002, "Action");
            Movie movie6 = new Movie("The Green Mile", 189, 1999, "Drama");
            Movie movie7 = new Movie("Avengers Endgame", 180, 2019, "Action");
            Movie movie8 = new Movie("Crazy Stupid Love", 120, 2011, "Comedy");
            Movie movie9 = new Movie("The Shawshank Redemption", 142, 1994, "Drama");
            Movie movie10 = new Movie("Inception", 140, 2010, "Sci-fi");
            Movie movie11 = new Movie("The Ugly Truth", 96, 2009, "Comedy");
            Movie movie12 = new Movie("Hacksaw Ridge", 139, 2016, "Historical Drama");

            movie.getCinemas().add(cinema);
            movie1.getCinemas().add(cinema);
            movie2.getCinemas().add(cinema);
            movie3.getCinemas().add(cinema);
            movie4.getCinemas().add(cinema);
            movie5.getCinemas().add(cinema);
            movie6.getCinemas().add(cinema);
            movie7.getCinemas().add(cinema);
            movie8.getCinemas().add(cinema);
            movie9.getCinemas().add(cinema);
            movie10.getCinemas().add(cinema);
            movie11.getCinemas().add(cinema);
            movie12.getCinemas().add(cinema);

            movieRepository.save(movie);
            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
            movieRepository.save(movie4);
            movieRepository.save(movie5);
            movieRepository.save(movie6);
            movieRepository.save(movie7);
            movieRepository.save(movie8);
            movieRepository.save(movie9);
            movieRepository.save(movie10);
            movieRepository.save(movie11);
            movieRepository.save(movie12);

            Screen screen1 = new Screen(4, cinema);
            Screen screen2 = new Screen(5, cinema);
            Screen screen3 = new Screen(6, cinema);

            screenRepository.save(screen1);
            screenRepository.save(screen2);
            screenRepository.save(screen3);

            Screening screening1 = new Screening(movie1, screen1, 8.20,15);
            Screening screening2 = new Screening(movie2, screen1, 9.30,12);
            Screening screening3 = new Screening(movie3, screen1, 16.00,16);
            Screening screening4 = new Screening(movie4, screen2,9.30,10);
            Screening screening5 = new Screening(movie5, screen2, 13.00,12);
            Screening screening6 = new Screening(movie6, screen2, 16.00,13);
            Screening screening7 = new Screening(movie7, screen3, 10.00,10);
            Screening screening8 = new Screening(movie8, screen3, 14.30,14);
            Screening screening9 = new Screening(movie9, screen3, 17.40,15);
            Screening screening10 = new Screening(movie10, screen3, 20.30,15);
            Screening screening11= new Screening(movie11, screen2, 21.30,14);
            Screening screening12= new Screening(movie12, screen1, 20.45,12);

            screeningRepository.save(screening1);
            screeningRepository.save(screening2);
            screeningRepository.save(screening3);
            screeningRepository.save(screening4);
            screeningRepository.save(screening5);
            screeningRepository.save(screening6);
            screeningRepository.save(screening7);
            screeningRepository.save(screening8);
            screeningRepository.save(screening9);
            screeningRepository.save(screening10);
            screeningRepository.save(screening11);
            screeningRepository.save(screening12);
        }
    }

