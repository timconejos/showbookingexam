package com.sample.movie.demo.model;

public class Seat {
    private String seatName;
    private boolean isBooked;

    public Seat(){}

    public Seat(String seatName){
        this.seatName = seatName;
        isBooked = false;
    }

    public boolean isSeatBooked(){
        return isBooked;
    }
    
    public void bookSeat(){
        isBooked = true;
    }

    public void cancelSeat(){
        isBooked = false;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    } 
}
