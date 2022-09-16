package com.example.cinema_project.repositories;

import org.springframework.stereotype.Repository;
import com.example.cinema_project.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

        List<Screen> findByCinemaId(long cinemaId);

        Screen findByCinemaIdAndScreenId(long cinemaId, long screenId);
    }


