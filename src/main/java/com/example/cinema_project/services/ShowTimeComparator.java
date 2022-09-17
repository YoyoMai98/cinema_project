package com.example.cinema_project.services;

import com.example.cinema_project.models.Screening;

import java.util.Comparator;

public class ShowTimeComparator implements Comparator<Screening> {
    @Override
    public int compare(Screening a, Screening b){
        return Double.compare(a.getShowTime(), b.getShowTime());
    }
}
