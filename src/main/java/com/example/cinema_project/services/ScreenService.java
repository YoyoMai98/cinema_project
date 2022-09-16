package com.example.cinema_project.services;


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

    public Screen addScreeningToScreen(long screenId, long screeningId){
        Optional<Screen> screen = screenRepository.findById(screenId);
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if(!screen.isPresent()) return null;
        if(screening.isPresent()) {
            List<Screening> screenings = screen.get().getScreenings();
            screenings.add(screening.get());
            screen.get().setScreenings(screenings);
            screening.get().setScreen(screen.get());
            screeningRepository.save(screening.get());
            screenRepository.save(screen.get());

        }
        return screen.get();
    }

    public void removeScreeningFromScreen(long screenId, long screeningId){
        Optional<Screen> screen = screenRepository.findById(screenId);
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if(!screen.isPresent()) return;
        if(screening.isPresent()) {
            List<Screening> screenings = screen.get().getScreenings();
            screenings.remove(screening.get());
            screen.get().setScreenings(screenings);
            screening.get().setScreen(null);
            screeningRepository.save(screening.get());
            screenRepository.save(screen.get());
        }
    }

    public List<Screen> getAllScreens(long cinemaId){
        return screenRepository.findByCinemaId(cinemaId);
    }


    public Screen getScreenById(long id, long cinemaId){
        return screenRepository.findByCinemaIdAndScreenId(cinemaId, id);
    }

    public Screen addNewScreen(Screen screen){
        screenRepository.save(screen);
        return screen;
    }

    }





