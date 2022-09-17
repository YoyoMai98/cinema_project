package com.example.cinema_project.services;


import com.example.cinema_project.models.Booking;
import com.example.cinema_project.models.Customer;
import com.example.cinema_project.models.Screening;
import com.example.cinema_project.repositories.BookingRepository;
import com.example.cinema_project.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    public List<Booking> getAllBookingsByScreeningId(long screeningId){
        return bookingRepository.findByScreeningId(screeningId);
    }

    public double calculateRevenueForOneScreening(long screeningId){
        List<Booking> bookings = getAllBookingsByScreeningId(screeningId);
        if(bookings == null) return 0;
        double partialRevenue = 0;
        for(Booking booking : bookings){
            partialRevenue += booking.getPrice();
        }
        return partialRevenue;
    }

    public Booking addNewBooking(Customer customer, Screening screening, int seatNumber){
        Booking booking = new Booking(seatNumber, customer, screening);
        List<Booking> bookings = screening.getBookings();
        bookings.add(booking);
        screening.setBookings(bookings);
        bookingRepository.save(booking);
        screeningRepository.save(screening);
        return booking;
    }
}
