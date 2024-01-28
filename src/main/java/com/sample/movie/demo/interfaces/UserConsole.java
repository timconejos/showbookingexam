package com.sample.movie.demo.interfaces;

import java.io.IOException;

import com.sample.movie.demo.exception.BookingException;

public interface UserConsole {
    void interact() throws BookingException, IOException;    
}
