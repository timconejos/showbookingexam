package com.sample.movie.demo.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sample.movie.demo.constants.Constants;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.interfaces.UserConsole;
import com.sample.movie.demo.model.BookRequest;

public class BuyerConsole implements UserConsole {
    private BufferedReader reader;
    private HttpHeaders headers;
    private final String API_URL = "http://localhost:8080/";

    public BuyerConsole() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void interact() throws BookingException, IOException {
        System.out.println("Welcome user\nAvailable commands: Availability, Book, Cancel, Exit");
        System.out.print("Enter command: ");
        String userCommand = reader.readLine();

        // Buyer-specific code
        if(userCommand.split(" ")[0].toLowerCase().equals("availability")){
            checkAvailability(userCommand);
        }
        else if(userCommand.split(" ")[0].toLowerCase().equals("book")){
            bookShow(userCommand);
        }
        else if(userCommand.split(" ")[0].toLowerCase().equals("cancel")){
            cancelBooking(userCommand);
        }
        else if(userCommand.split(" ")[0].toLowerCase().equals("exit")){
            return;
        }
        else{
            throw new BookingException(Constants.INVALID_COMMAND);
        }
    }

    private void checkAvailability(String command) throws BookingException {
        try{
            String[] avail = command.split(" ");
            String id = avail[1];
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.getForEntity(API_URL + "user/avail/" + id, Object.class);
            System.out.println(response.getBody());
        }
        catch(IndexOutOfBoundsException ex){
            throw new BookingException(Constants.INVALID_PARAMS + "\n:" + ex.getMessage());
        }
        catch(Exception ex){
            throw new BookingException(ex.getMessage());
        }
    }

    private void bookShow(String command) throws BookingException {
        try{
            String[] book = command.split(" ");
            RestTemplate restTemplate = new RestTemplate();
            BookRequest bookRequest = new BookRequest(book[2], book[3]);
            HttpEntity<BookRequest> request = new HttpEntity<>(bookRequest, headers);
            ResponseEntity<?> response = restTemplate.postForEntity(API_URL + "user/book/" + book[1], request, Object.class);
            String value = response.getBody().toString();
            System.out.println(String.format("Booking success. Your ticket number is: %s", value));
        }
        catch(IndexOutOfBoundsException ex){
            throw new BookingException(Constants.INVALID_PARAMS + "\n:" + ex.getMessage());
        }
        catch(Exception ex){
            throw new BookingException(ex.getMessage());
        }
    }

    private void cancelBooking(String command) throws BookingException {
        try{
            String[] cancel = command.split(" ");
            String id = cancel[1];
            String phoneNumber = cancel[2];
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<>(phoneNumber, headers);
            ResponseEntity<?> response = restTemplate.postForEntity(API_URL + "user/cancel/" + id, request,  Object.class);
            System.out.println(response.getBody());
        }
        catch(IndexOutOfBoundsException ex){
            throw new BookingException(Constants.INVALID_PARAMS + "\n:" + ex.getMessage());
        }
        catch(Exception ex){
            throw new BookingException(ex.getMessage());
        }
    }
}