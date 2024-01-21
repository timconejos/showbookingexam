package com.sample.movie.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sample.movie.demo.constants.Constants;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.model.BookRequest;
import com.sample.movie.demo.model.Show;

@SpringBootApplication
public class BookApplication {
	final String API_URL = "http://localhost:8080/";

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);

		//separate thread for console.
		new Thread(() ->{
			BookApplication app = new BookApplication();
			app.runConsole();
		}).start();


	}

	public void runConsole(){
		boolean isAdmin = false, isBuyer = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		while(true){
			try{
				if(!isBuyer && !isAdmin){
					System.out.print("Buyer or Admin? : ");
					String user = reader.readLine();
					if(user.toLowerCase().equals("buyer")){
						isBuyer = true;
					}
					else if(user.toLowerCase().equals("admin")){
						isAdmin = true;
					}
					else{
						throw new BookingException(Constants.INVALID_COMMAND);
					}
				}
				else if(isAdmin){
					System.out.println("Welcome admin\nAvailable commands: Setup, View, Exit");
					System.out.print("Enter command: ");
					String adminCommand = reader.readLine();

					if(adminCommand.split(" ")[0].toLowerCase().equals("setup")){
						try{
							String[] setup = adminCommand.split(" ");
							Show show = new Show(Long.parseLong(setup[1]), Integer.parseInt(setup[2]), Integer.parseInt(setup[3]), Integer.parseInt(setup[4]));
							RestTemplate restTemplate = new RestTemplate();
							HttpEntity<Show> request = new HttpEntity<>(show, headers);
							ResponseEntity<?> response = restTemplate.postForEntity(API_URL + "admin/setup", request, Object.class);
							System.out.println(response.getBody());
						}
						catch(IndexOutOfBoundsException ex){
							throw new BookingException(Constants.INVALID_PARAMS + "\n:" + ex.getMessage());
						}
						catch(Exception ex){
							throw new BookingException(ex.getMessage());
						}
						
					}
					else if(adminCommand.split(" ")[0].toLowerCase().equals("view")){
						try{
							String[] view = adminCommand.split(" ");
							String id = view[1];
							RestTemplate restTemplate = new RestTemplate();
							ResponseEntity<?> response = restTemplate.getForEntity(API_URL + "admin/view/" + id, Object.class);
							System.out.println(response.getBody());
						}
						catch(IndexOutOfBoundsException ex){
							throw new BookingException(Constants.INVALID_PARAMS + "\n:" + ex.getMessage());
						}
						catch(Exception ex){
							throw new BookingException(ex.getMessage());
						}
					}
					else if(adminCommand.split(" ")[0].toLowerCase().equals("exit")){
						isAdmin = false;
					}
					else{
						throw new BookingException(Constants.INVALID_COMMAND);
					}
				}
				else if(isBuyer){
					System.out.println("Welcome user\nAvailable commands: Availability, Book, Cancel, Exit");
					System.out.print("Enter command: ");
					String userCommand = reader.readLine();

					if(userCommand.split(" ")[0].toLowerCase().equals("availability")){
						try{
							String[] avail = userCommand.split(" ");
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
					else if(userCommand.split(" ")[0].toLowerCase().equals("book")){
						try{
							String[] book = userCommand.split(" ");
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
					else if(userCommand.split(" ")[0].toLowerCase().equals("cancel")){
						try{
							String[] cancel = userCommand.split(" ");
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
					else if(userCommand.split(" ")[0].toLowerCase().equals("exit")){
						isBuyer = false;
					}
					else{
						throw new BookingException(Constants.INVALID_COMMAND);
					}
				}
				
			}
			catch(Exception e){
				System.err.println(e.getMessage());
			}
			
		}
		
	}

}
