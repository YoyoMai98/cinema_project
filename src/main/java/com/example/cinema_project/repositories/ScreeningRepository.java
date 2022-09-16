package com.example.cinema_project.repositories;

import com.example.cinema_project.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findByCinemaId(long cinemaId);
}
