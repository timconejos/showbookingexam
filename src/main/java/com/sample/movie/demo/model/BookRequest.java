package com.sample.movie.demo.model;

public class BookRequest {
    private String phone;
    private String bookings;

    public BookRequest(String phone, String bookings) {
        this.phone = phone;
        this.bookings = bookings;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBookings() {
        return bookings;
    }

    public void setBookings(String bookings) {
        this.bookings = bookings;
    }

}
