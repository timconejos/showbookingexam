package com.sample.movie.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.movie.demo.constants.Constants;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.model.BookRequest;
import com.sample.movie.demo.model.Show;
import com.sample.movie.demo.model.ViewResponse;
import com.sample.movie.demo.repository.ShowRepository;


@RestController
public class ShowController {

    @Autowired
    private ShowRepository showRepository;

    @GetMapping("/admin/view/{id}")
    public ResponseEntity<?> adminViewShow(@PathVariable Long id) throws BookingException {
        ViewResponse show = showRepository.getViewResponse(id);
        if (show == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.INVALID_SEAT_NUMBER);
        }
        return ResponseEntity.ok(show);
    }

    @PostMapping("/admin/setup")
    public ResponseEntity<?> adminSetupShow(@RequestBody Show show) throws BookingException {
        try{
            Show setupShow = showRepository.setupShow(show);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.valueToTree("Success");
            return ResponseEntity.ok(responseNode);
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/user/avail/{id}")
    public ResponseEntity<?> userAvailSeats(@PathVariable Long id) {
        try{
            String availableSeats = showRepository.getAvailSeats(id);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.valueToTree(availableSeats);
            return ResponseEntity.ok(responseNode);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    

    @PostMapping("/user/book/{id}")
    public ResponseEntity<?> userBookShow(@PathVariable Long id, @RequestBody BookRequest bookRequest ) {
        ArrayList<String> bookList = new ArrayList<>();
        try {
            String[] seats = bookRequest.getBookings().split(",");
            for (String seat : seats) {
                //cancel seats
                bookList.add(seat);
            }

            String ticketNumber = showRepository.bookShow(id, bookRequest.getPhone(), bookList);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.valueToTree(ticketNumber);
            return ResponseEntity.ok(responseNode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/user/cancel/{id}")
    public ResponseEntity<?> userCancelSeats(@PathVariable String id, @RequestBody String phoneNumber) {
        try{
            showRepository.cancelSeats(id, phoneNumber);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.valueToTree("Success");
            return ResponseEntity.ok(responseNode);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
