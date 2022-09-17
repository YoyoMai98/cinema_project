package com.example.cinema_project.services;


import com.example.cinema_project.models.*;
import com.example.cinema_project.repositories.MovieRepository;
import com.example.cinema_project.repositories.ScreenRepository;
import com.example.cinema_project.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            screeningsInOrderByShowTime(screen);
            screening.get().setScreen(screen);
            screeningRepository.save(screening.get());
            screenRepository.save(screen);
        }
        return screen;
    }

    public Screen addNewScreeningToScreen(Screening screening, long screenId, long cinemaId){
        Screen screen = screenRepository.findByCinemaIdAndId(cinemaId,screenId);
        if(screen == null) return null;
        List<Screening> screenings = screen.getScreenings();
        screenings.add(screening);
        screen.setScreenings(screenings);
        screeningsInOrderByShowTime(screen);
        screening.setScreen(screen);
        screeningRepository.save(screening);
        screenRepository.save(screen);
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
            screeningsInOrderByShowTime(screen);
            screening.get().setScreen(null);
            screeningRepository.save(screening.get());
            screenRepository.save(screen);
        }
    }

    public List<Screen> getAllScreens(){
        return screenRepository.findAll();
    }


    public Screen getScreenById(long id, long cinemaId){
        return screenRepository.findByCinemaIdAndId(cinemaId, id);
    }

    public Screen addNewScreen(Screen screen) {
        screenRepository.save(screen);
        return screen;
    }

    public void screeningsInOrderByShowTime(Screen screen){
        List<Screening> screenings = screen.getScreenings();
        Collections.sort(screenings, new ShowTimeComparator());
        screen.setScreenings(screenings);
        screenRepository.save(screen);
    }

    }





