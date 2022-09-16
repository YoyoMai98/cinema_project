package com.example.cinema_project.services;


import com.example.cinema_project.models.Cinema;
import com.example.cinema_project.models.Movie;
import com.example.cinema_project.models.Screen;
import com.example.cinema_project.models.Screening;
import com.example.cinema_project.repositories.MovieRepository;
import com.example.cinema_project.repositories.ScreenRepository;
import com.example.cinema_project.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenService {

    @Autowired
    ScreenRepository screenRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    public Screen addScreeningToScreen(long screenId, long screeningId, long cinemaId){
        Screen screen = screenRepository.findByCinemaIdAndId(cinemaId,screenId);
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if(screen == null) return null;
        if(screening.isPresent()) {
            List<Screening> screenings = screen.getScreenings();
            screenings.add(screening.get());
            screen.setScreenings(screenings);
            screening.get().setScreen(screen);
            screeningRepository.save(screening.get());
            screenRepository.save(screen);

        }
        return screen;
    }

    public void removeScreeningFromScreen(long screenId, long screeningId, long cinemaId){
        Screen screen = screenRepository.findByCinemaIdAndId(cinemaId,screenId);
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if(screen == null) return;
        if(screening.isPresent()) {
            List<Screening> screenings = screen.getScreenings();
            screenings.remove(screening.get());
            screen.setScreenings(screenings);
            screening.get().setScreen(null);
            screeningRepository.save(screening.get());
            screenRepository.save(screen);
        }
    }

    public List<Screen> getAllScreens(long cinemaId){
        return screenRepository.findByCinemaId(cinemaId);
    }


    public Screen getScreenById(long id, long cinemaId){
        return screenRepository.findByCinemaIdAndId(cinemaId, id);
    }

    public Screen addNewScreen(Screen screen){
        screenRepository.save(screen);
        return screen;
    }

    }





