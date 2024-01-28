package com.sample.movie.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.model.BookRequest;
import com.sample.movie.demo.repository.ShowRepository;

@RestController
@RequestMapping("/user")
public class BookingController {

    @Autowired
    private ShowRepository showRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/avail/{id}")
    public ResponseEntity<?> availSeats(@PathVariable Long id) {
        try {
            String availableSeats = showRepository.getAvailSeats(id);
            return createResponseEntity(availableSeats);
        } catch (BookingException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/book/{id}")
    public ResponseEntity<?> bookShow(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        ArrayList<String> bookList = new ArrayList<>();
        try {
            String[] seats = bookRequest.getBookings().split(",");
            for (String seat : seats) {
                //cancel seats
                bookList.add(seat);
            }
            String ticketNumber = showRepository.bookShow(id, bookRequest.getPhone(), bookList);
            return createResponseEntity(ticketNumber);
        } catch (BookingException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelSeats(@PathVariable String id, @RequestBody String phoneNumber) {
        try {
            showRepository.cancelSeats(id, phoneNumber);
            return createResponseEntity("Success");
        } catch (BookingException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    private ResponseEntity<JsonNode> createResponseEntity(Object body) {
        JsonNode responseNode = objectMapper.valueToTree(body);
        return ResponseEntity.ok(responseNode);
    }
}