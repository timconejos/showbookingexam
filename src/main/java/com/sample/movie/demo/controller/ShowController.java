package com.sample.movie.demo.controller;

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
import com.sample.movie.demo.constants.Constants;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.model.Show;
import com.sample.movie.demo.repository.ShowRepository;

@RestController
@RequestMapping("/admin")
public class ShowController {

    @Autowired
    private ShowRepository showRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewShow(@PathVariable Long id) throws BookingException {
        Show show = showRepository.getShow(id);
        if (show == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.INVALID_SHOW_NUMBER);
        }
        return createResponseEntity(show);
    }

    @PostMapping("/setup")
    public ResponseEntity<?> setupShow(@RequestBody Show show) {
        try {
            Show setupShow = showRepository.setupShow(show);
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