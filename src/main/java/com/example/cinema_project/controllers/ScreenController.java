package com.example.cinema_project.controllers;

import com.example.cinema_project.models.Booking;
import com.example.cinema_project.models.Screen;
import com.example.cinema_project.models.Screening;
import com.example.cinema_project.repositories.CustomerRepository;
import com.example.cinema_project.services.BookingService;
import com.example.cinema_project.services.ScreenService;
import com.example.cinema_project.services.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/screens")
public class ScreenController {

    @Autowired
    ScreenService screenService;

    @Autowired
    ScreeningService screeningService;

    @Autowired
    BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens(){
        List<Screen> screens = screenService.getAllScreens();
        return new ResponseEntity<>(screens, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long id, @RequestParam long cinemaId){
        Screen screen = screenService.getScreenById(id, cinemaId);
        if(screen != null){
            return new ResponseEntity<>(screen, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{screenId}/screenings")
    public ResponseEntity<List<Screening>> getAllScreenings(@PathVariable long screenId){
        List<Screening> screenings = screeningService.getAllScreenings(screenId);
        return new ResponseEntity<>(screenings, HttpStatus.OK);
    }

    @GetMapping("/{screenId}/screenings/{id}")
    public ResponseEntity<Screening> getScreeningById(
            @PathVariable Long screenId, @PathVariable Long id, @RequestParam long cinemaId
            ){
        Screening screening = screeningService.getScreeningById(id, screenId, cinemaId);
        if(screening != null){
            return new ResponseEntity<>(screening, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/screenings/{id}/bookings")
    public ResponseEntity<List<Booking>> getAllBookingsByScreeningId(@PathVariable long id){
        List<Booking> bookings = bookingService.getAllBookingsByScreeningId(id);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{screenId}/{screenings}/{screeningId}/seats")
    public ResponseEntity<List<String>> getOccupiedSeatsByScreeningId(
            @PathVariable long screenId, @PathVariable long screeningId, @RequestParam long cinemaId
    ){
        List<String> seats = screeningService.getSeatList(screenId,screeningId,cinemaId);
        return new ResponseEntity<>(seats, seats == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Screen> addNewScreen(@RequestBody Screen screen){
        Screen savedScreen = screenService.addNewScreen(screen);
        return new ResponseEntity<>(savedScreen, HttpStatus.CREATED);
    }


    @PostMapping(value = "/{screenId}")
    public ResponseEntity<Screen> addScreeningToScreen(
            @PathVariable long screenId, @RequestParam long screeningId, @RequestParam long cinemaId
    ){
        Screen screen = screenService.addScreeningToScreen(screenId, screeningId, cinemaId);
        return new ResponseEntity<>(screen, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{screenId}/screenings")
    public ResponseEntity<Screen> addNewScreeningToScreen(
            @PathVariable long screenId, @RequestBody Screening screening, @RequestParam long cinemaId
    ){
        Screen screen = screenService.addNewScreeningToScreen(screening, screenId, cinemaId);
        return new ResponseEntity<>(screen, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{screenId}/screenings/{screeningId}/customers/{customerId}")
    public ResponseEntity<Screening> addCustomerToScreening(
            @PathVariable long screeningId,
            @PathVariable long screenId,
            @PathVariable long customerId,
            @RequestParam long cinemaId,
            @RequestParam String seat
    ) {
        Screening screening = screeningService.getScreeningById(screeningId, screenId, cinemaId);
        if(screening != null){
            Screening updatedScreening = screeningService.addCustomerToScreening(customerId, screeningId, seat, cinemaId);
            return new ResponseEntity<>(updatedScreening, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/{screenId}/screenings/{screeningId}/movies/{movieId}")
    public ResponseEntity<Screening> addMovieToScreening(
            @PathVariable long screeningId,
            @PathVariable long screenId,
            @PathVariable long movieId,
            @RequestParam long cinemaId
    ) {
        Screening screening = screeningService.getScreeningById(screeningId, screenId, cinemaId);
        if(screening != null){
            Screening updatedScreening = screeningService.addMovieToScreening(movieId,screeningId, screenId,cinemaId);
            if (updatedScreening != null){
                return new ResponseEntity<>(updatedScreening, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{screenId}")
    public ResponseEntity removeScreeningOrMovieFromScreen(
            @PathVariable long screenId,
            @RequestParam long cinemaId,
            @RequestParam long screeningId,
            @RequestParam Optional<Long> movieId
    ){
        if(movieId.isPresent()){
            screeningService.removeMovieFromScreening(movieId.get(),screeningId,screenId,cinemaId);
        }else{
            screenService.removeScreeningFromScreen(screenId, screeningId, cinemaId);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
