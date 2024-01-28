package com.sample.movie.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.sample.movie.demo.console.AdminConsole;
import com.sample.movie.demo.console.BuyerConsole;
import com.sample.movie.demo.constants.Constants;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.interfaces.UserConsole;

public class ConsoleApplication {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private UserConsole adminConsole = new AdminConsole();
    private UserConsole buyerConsole = new BuyerConsole();

    public void runConsole() {
        while(true) {
            try {
                System.out.print("Buyer or Admin? : ");
                String user = reader.readLine();
                if(user.toLowerCase().equals("buyer")){
                    buyerConsole.interact();
                }
                else if(user.toLowerCase().equals("admin")){
                    adminConsole.interact();
                }
                else{
                    throw new BookingException(Constants.INVALID_COMMAND);
                }
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}