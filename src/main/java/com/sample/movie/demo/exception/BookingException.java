package com.sample.movie.demo.exception;

public class BookingException extends Exception{
    public BookingException(String message){
        super(String.format("Error: %s", message));
    }
}
