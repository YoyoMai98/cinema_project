package com.example.cinema_project.services;

import com.example.cinema_project.models.Screening;

import java.util.Comparator;

public class ShowTimeComparator implements Comparator<Screening> {
    @Override
    public int compare(Screening a, Screening b){
        double aHour = a.getShowTime().getHour();
        double aMinute = a.getShowTime().getMinute();
        double bMinute = b.getShowTime().getMinute();
        double bHour = b.getShowTime().getHour();

        double aShowTime = aHour + aMinute/100;
        double bShowTime = bHour + bMinute/100;
        return Double.compare(aShowTime, bShowTime);
    }
}
