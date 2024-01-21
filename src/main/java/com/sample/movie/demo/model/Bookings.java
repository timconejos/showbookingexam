package com.sample.movie.demo.model;

import java.util.List;

public class Bookings {
    private String ticketNumber;
    private Long showNumber;
    private String phoneNumber;
    private List<String> bookings;
    private Long bookTimestamp;


    public Bookings(String ticketNumber, Long showNumber, String phoneNumber, List<String> bookings) {
        this.ticketNumber = ticketNumber;
        this.showNumber = showNumber;
        this.phoneNumber = phoneNumber;
        this.bookings = bookings;
        this.bookTimestamp = System.currentTimeMillis();
    }

    public String getTicketNumber() {
        return this.ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Long getShowNumber() {
        return this.showNumber;
    }

    public void setShowNumber(Long showNumber) {
        this.showNumber = showNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getBookings() {
        return this.bookings;
    }

    public void setBookings(List<String> bookings) {
        this.bookings = bookings;
    }

    public Long getBookTimestamp() {
        return bookTimestamp;
    }

    public void setBookTimestamp(Long bookTimestamp) {
        this.bookTimestamp = bookTimestamp;
    }

    

    
}
