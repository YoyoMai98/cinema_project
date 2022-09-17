package com.example.cinema_project;

import com.example.cinema_project.models.Cinema;
import com.example.cinema_project.models.Movie;
import com.example.cinema_project.models.Screen;
import com.example.cinema_project.models.Screening;
import com.example.cinema_project.repositories.CinemaRepository;
import com.example.cinema_project.services.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ScreeningServiceTest {

    private Screening screening1;
    private Screening screening2;
    private Screening screening3;

    private Movie movie1;
    private Movie movie2;
    private Movie movie3;

    private Screen screen1;
    private Cinema cinema;

    @Autowired
    ScreeningService screeningService;

    @Autowired
    CinemaRepository cinemaRepository;

    @BeforeEach
    public void setUp(){
        cinema = new Cinema("vue");
        cinemaRepository.save(cinema);
        movie1 = new Movie("movie1",80,2001,"Action");
        movie2 = new Movie("movie2",120,2012,"Action");
        movie3 = new Movie("movie3", 98, 2008, "Action");
        screen1 = new Screen(4,cinema);
        screening1 = new Screening(movie1, screen1, 9.30);
        screening2 = new Screening(movie2, screen1, 10);
        screening3 = new Screening(movie3, screen1, 14);
        List<Screening> screenings = screen1.getScreenings();
        screenings.add(screening1);
        screenings.add(screening2);
        screenings.add(screening3);
        screen1.setScreenings(screenings);
    }

    @Test
    public void canShow(){
        boolean actual = screeningService.canShow(screening1,movie2);
        boolean expected = false;
        assertEquals(actual,expected);
    }
}
