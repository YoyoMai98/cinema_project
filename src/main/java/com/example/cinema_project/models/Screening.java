package com.example.cinema_project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
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
    private double showTime;

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

    public Screening(Movie movie, Screen screen, double showTime, double price) {
        this.movie = movie;
        this.screen = screen;
        this.showTime = showTime;
        this.price = price;
        this.bookings = new ArrayList<>();
        this.seats = new ArrayList<>();
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

    public double getShowTime() {
        return showTime;
    }

    public void setShowTime(double showTime) {
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
}
