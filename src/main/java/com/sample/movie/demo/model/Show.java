package com.sample.movie.demo.model;

import java.util.HashMap;

public class Show {

    private Long showNumber;
    private HashMap<String, Seat> seats;
    private int cancellationWindow;

    public Show(){}
    
    public Show(Long showNumber, int row, int column, int cancellationWindow){
        this.showNumber = showNumber;
        this.seats = new HashMap<>();
        this.cancellationWindow = cancellationWindow;

        //generate seats with seat name.
        for(int i = 1; i <= row; i++){
            for(int j = 1; j <= column; j++){
                char rowName = (char) ('A' + i - 1);
                String seatName =  rowName + String.valueOf(j);
                this.seats.put(seatName, new Seat(seatName));
            }
        }
    }

    public Long getShowNumber() {
        return showNumber;
    }

    public HashMap<String, Seat> getSeats() {
        return seats;
    }

    public int getCancellationWindow() {
        return cancellationWindow;
    }

    public void setCancellationWindow(int cancellationWindow) {
        this.cancellationWindow = cancellationWindow;
    }

}
