package com.example.cinema_project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double price;

    @Column(name = "show_time")
    private LocalTime showTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column
    @ElementCollection
    private List<Integer> seats;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnoreProperties({"screenings","cinema"})
    private Movie movie;
    
    @ManyToOne
    @JoinColumn(name = "screen_id")
    @JsonIgnoreProperties({"screenings","cinema"})
    private Screen screen;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties({"screening"})
    private List<Booking> bookings;

    public Screening(Movie movie, Screen screen, LocalTime showTime, double price) {
        this.movie = movie;
        this.screen = screen;
        this.showTime = showTime;
        this.price = price;
        this.bookings = new ArrayList<>();
        this.seats = new ArrayList<>();

        int hour = showTime.getHour();
        int minute = showTime.getMinute();
        double lengthToHour = Math.floor((double)movie.getLength()/60);
        double lengthToMinute = Math.floor(movie.getLength()%60);
        double endHour = hour + lengthToHour;
        double endMinute = minute + lengthToMinute;
        if(endMinute >= 60){
            double addToEndHour = Math.floor(endMinute / 60);
            endMinute %= 60;
            endHour += addToEndHour;
        }
        if(endHour >= 24){
            endHour %= 24;
        }
        this.endTime = LocalTime.of((int)endHour,(int)endMinute);
    }

    public Screening(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

//    public int calculateEndTime(){}
}
