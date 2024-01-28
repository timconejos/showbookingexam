package com.sample.movie.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sample.movie.demo.console.AdminConsole;
import com.sample.movie.demo.console.BuyerConsole;
import com.sample.movie.demo.constants.Constants;
import com.sample.movie.demo.exception.BookingException;

@SpringBootApplication
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);

        //separate thread for console.
        new Thread(() ->{
            BookApplication app = new BookApplication();
            app.runConsole();
        }).start();
    }

    public void runConsole(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            try{
                System.out.print("Buyer or Admin? : ");
                String user = reader.readLine();
                if(user.toLowerCase().equals("buyer")){
                    BuyerConsole buyerConsole = new BuyerConsole();
                    buyerConsole.interact();
                }
                else if(user.toLowerCase().equals("admin")){
                    AdminConsole adminConsole = new AdminConsole();
                    adminConsole.interact();
                }
                else{
                    throw new BookingException(Constants.INVALID_COMMAND);
                }
            }
            catch(IOException | BookingException e){
                System.err.println(e.getMessage());
            }
        }
    }
}