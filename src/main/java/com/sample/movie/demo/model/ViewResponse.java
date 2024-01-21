package com.sample.movie.demo.model;

import java.util.ArrayList;

public class ViewResponse {
    private Long showNumber;
    private ArrayList<Bookings> bookings;

    

    public ViewResponse(Long showNumber, ArrayList<Bookings> bookings) {
        this.showNumber = showNumber;
        this.bookings = bookings;
    }

    public Long getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(Long showNumber) {
        this.showNumber = showNumber;
    }

    public ArrayList<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Bookings> bookings) {
        this.bookings = bookings;
    }

    
    
}
