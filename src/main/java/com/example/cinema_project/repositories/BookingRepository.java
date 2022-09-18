package com.example.cinema_project.repositories;

import com.example.cinema_project.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByScreeningId(long screeningId);

    Booking findByCustomerIdAndScreeningIdAndSeatNumber(long customerId, long screeningId, String seatNumber);
}
