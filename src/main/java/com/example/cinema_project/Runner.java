package com.example.cinema_project;

import java.time.LocalTime;

public class Runner {
    public static void main(String[] args) {
        LocalTime showTime = LocalTime.of(8,20);
        int hour = showTime.getHour(); // 8
        int minute = showTime.getMinute(); // 20
        double lengthToHour = Math.floor((double)117/60);
        double lengthToMinute = Math.floor(117%60);
        // 117 => 117/60 = 1...57
        double endHour = hour + lengthToHour;
        System.out.println(endHour);
        double endMinute = minute + lengthToMinute;
        System.out.println(endMinute);
        if(endMinute >= 60){
            double addToEndHour = Math.floor(endMinute / 60);
            endMinute %= 60;
            endHour += addToEndHour;
        }
        LocalTime endTime = LocalTime.of((int)endHour,(int)endMinute);
        System.out.println(endTime);
    }
}
