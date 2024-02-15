package com.example.myentertainment;

import java.util.ArrayList;

public class Booking {
    public static final String[] facilityName = {"Bowling", "Laser Arena", "Internet & Gaming Cafe", "Karaoke",
        "Magic Show", "Rock Climbing","Kid Playland"};
    public static ArrayList<Booking> bookings = new ArrayList<Booking>();
    int facilityID, bookingID;
    static int bookingNum = 1000;
    double startTime, finishTime;
    double bookingCost;

    Booking(int fID, double sTime, double fTime, double cost){
        facilityID = fID;
        bookingID = bookingNum + 1;
        startTime = sTime;
        finishTime = fTime;
        bookingCost = cost;

        bookingNum++;
    }

    String getFacilityName(){return facilityName[facilityID];};

    static void addBooking(Booking newBooking){
        bookings.add(newBooking);
    }

    int getFacilityID(){return facilityID;}

    int getBookingID(){return bookingID;}

    double getStartTime(){return startTime;}

    double getFinishTime(){return finishTime;}

    double getBookingCost(){return bookingCost;}

    public String toString(){
        return "#" + bookingID + " " + this.getFacilityName();
    }




}
