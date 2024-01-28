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
import com.sample.movie.demo.model.Show;

public class AdminConsole implements UserConsole {
    private BufferedReader reader;
    private HttpHeaders headers;
    private final String API_URL = "http://localhost:8080/";

    public AdminConsole() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void interact() throws BookingException, IOException{
        System.out.println("Welcome admin\nAvailable commands: Setup, View, Exit");
        System.out.print("Enter command: ");
        String adminCommand = reader.readLine();

        // Admin-specific code
        if(adminCommand.split(" ")[0].toLowerCase().equals("setup")){
            setupShow(adminCommand);
        }
        else if(adminCommand.split(" ")[0].toLowerCase().equals("view")){
            viewShow(adminCommand);
        }
        else if(adminCommand.split(" ")[0].toLowerCase().equals("exit")){
            return;
        }
        else{
            throw new BookingException(Constants.INVALID_COMMAND);
        }
    }

    private void setupShow(String command) throws BookingException {
        try {
            String[] setup = command.split(" ");
            Show show = new Show(Long.parseLong(setup[1]), Integer.parseInt(setup[2]), Integer.parseInt(setup[3]), Integer.parseInt(setup[4]));
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Show> request = new HttpEntity<>(show, headers);
            ResponseEntity<?> response = restTemplate.postForEntity(API_URL + "admin/setup", request, Object.class);
            System.out.println(response.getBody());
        }
        catch(IndexOutOfBoundsException ex) {
            throw new BookingException(Constants.INVALID_PARAMS + "\n:" + ex.getMessage());
        }
        catch(Exception ex) {
            throw new BookingException(ex.getMessage());
        }
    }

    private void viewShow(String command) throws BookingException {
        try {
            String[] view = command.split(" ");
            String id = view[1];
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.getForEntity(API_URL + "admin/view/" + id, Object.class);
            System.out.println(response.getBody());
        }
        catch(IndexOutOfBoundsException ex) {
            throw new BookingException(Constants.INVALID_PARAMS + "\n:" + ex.getMessage());
        }
        catch(Exception ex) {
            throw new BookingException(ex.getMessage());
        }
    }
}