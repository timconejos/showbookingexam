package com.sample.movie.demo.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.sample.movie.demo.constants.Constants;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.model.Bookings;
import com.sample.movie.demo.model.Seat;
import com.sample.movie.demo.model.Show;
import com.sample.movie.demo.model.ViewResponse;

@Repository
public class ShowRepository {
    private final HashMap<Long, Show> SHOWS = new HashMap<>();
    private final HashMap<String, Bookings> BOOKINGS = new HashMap<>();
    private Long showId = 1L;
    private Long ticketNumberCounter = 0L;

    public HashMap<Long, Show> getAllShows() {
        return SHOWS;
    }

    public Show getShow(Long id) {
        return SHOWS.get(id);
    }

    public Long getCurrentShowId() {
        return showId;
    }

    public Show setupShow(Show show) throws BookingException{
        if(SHOWS.keySet().contains(show.getShowNumber())) throw new BookingException(Constants.SHOW_NUMBER_EXISTS);
        return SHOWS.put(show.getShowNumber(), show);
    }

    public ViewResponse getViewResponse(Long showId) throws BookingException{
        Show currentShow = SHOWS.get(showId);
        if(currentShow == null){
            throw new BookingException(Constants.INVALID_SHOW_NUMBER);
        }
        ArrayList<Bookings> bookings = new ArrayList<>();
        for(Bookings b : BOOKINGS.values()){
            if(b.getShowNumber().equals(showId)) bookings.add(b);
        }
        ViewResponse viewResponse = new ViewResponse(showId, bookings);
        return viewResponse;

        
    }

    public String bookShow(Long showId, String phone, List<String> bookings) throws BookingException{
        Show currentShow = SHOWS.get(showId);
        if(currentShow == null){
            throw new BookingException(Constants.INVALID_SHOW_NUMBER);
        }

        //check phone number if exists
        for(Bookings booking : BOOKINGS.values()){
            String phoneNumber = booking.getPhoneNumber();
            if(phoneNumber.equals(phone) && booking.getShowNumber().equals(showId)){
                throw new BookingException(Constants.BUYER_ALREADY_BOOKED);
            }
        }
        
        for(String seat: bookings){
            Seat currentSeat = currentShow.getSeats().get(seat);
            if(currentSeat == null) throw new BookingException(Constants.INVALID_SEAT_NUMBER);
            if(SHOWS.get(showId).getSeats().get(seat).isSeatBooked()) throw new BookingException(Constants.SEAT_ALREADY_BOOKED);

            SHOWS.get(showId).getSeats().get(seat).bookSeat();
        }
        String ticketNumber = String.format("%010d", ++ticketNumberCounter);
        Bookings booking = new Bookings(ticketNumber, showId, phone, bookings);
        BOOKINGS.put(ticketNumber,booking);

        return ticketNumber;
    }

    public String getAvailSeats(Long showId) throws BookingException{
        Show show = SHOWS.get(showId);
        if(show == null) throw new BookingException(Constants.INVALID_SHOW_NUMBER);
        String seatsAvailable = "";
        Set<String> seatSet = SHOWS.get(showId).getSeats().keySet();
        ArrayList<String> seatList = new ArrayList<String>(seatSet);
        Collections.sort(seatList);
        for(String seat : seatList){
            if(!SHOWS.get(showId).getSeats().get(seat).isSeatBooked()){
                seatsAvailable += seat + ",";
            }
        }
        return seatsAvailable;

    }

    public void cancelSeats(String ticketNumber, String phoneNumber) throws BookingException{
        Bookings booking = BOOKINGS.get(ticketNumber);
        if(booking == null) throw new BookingException(Constants.INVALID_TICKET_ID);

        Show showWhereSeatsCancelled = SHOWS.get(booking.getShowNumber());
        //impossible case
        if(showWhereSeatsCancelled == null ) throw new BookingException(Constants.INVALID_SHOW_NUMBER);

        if(phoneNumber.equals(booking.getPhoneNumber())){
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - booking.getBookTimestamp();
            float minutesPassed = (float) (elapsedTime / (60 * 1000));

            if(minutesPassed > showWhereSeatsCancelled.getCancellationWindow()){
                throw new BookingException(Constants.CANCELLATION_WINDOW_PASSED);
            }
            else{                
                //cancel bookings.
                for(String seat : BOOKINGS.get(ticketNumber).getBookings()){
                    SHOWS.get(showWhereSeatsCancelled.getShowNumber()).getSeats().get(seat).cancelSeat();
                }
                BOOKINGS.remove(ticketNumber);
            }
        }
        else{
            throw new BookingException(Constants.INCORRECT_PHONE_NUMBER);
        }
    }

}