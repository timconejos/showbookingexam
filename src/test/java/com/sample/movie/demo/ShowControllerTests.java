package com.sample.movie.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sample.movie.demo.controller.ShowController;
import com.sample.movie.demo.exception.BookingException;
import com.sample.movie.demo.model.BookRequest;
import com.sample.movie.demo.model.Show;
import com.sample.movie.demo.model.ViewResponse;
import com.sample.movie.demo.repository.ShowRepository;

@SpringBootTest
class ShowControllerTests {

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private ShowController showController;

    @Test
    void adminViewShow_ValidId_ReturnsOKResponse() throws BookingException {
        ViewResponse mockViewResponse = new ViewResponse(1L, new ArrayList<>());
        Mockito.when(showRepository.getViewResponse(anyLong())).thenReturn(mockViewResponse);

        ResponseEntity<?> response = showController.adminViewShow(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void adminViewShow_ValidId_ReturnsBadResponse() throws BookingException {
        ViewResponse mockViewResponse = null;
        Mockito.when(showRepository.getViewResponse(anyLong())).thenReturn(mockViewResponse);

        ResponseEntity<?> response = showController.adminViewShow(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void adminSetupShow_ValidShow_ReturnsOkResponse() throws BookingException {
        Show mockShow = new Show(/* Mock your Show here */);

        when(showRepository.setupShow(any(Show.class))).thenReturn(mockShow);

        ResponseEntity<?> response = showController.adminSetupShow(mockShow);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions if needed
    }

    @Test
    void adminSetupShow_ExceptionThrown_ReturnsBadRequestResponse() throws BookingException {
        when(showRepository.setupShow(any(Show.class))).thenThrow(new BookingException("Error"));

        ResponseEntity<?> response = showController.adminSetupShow(new Show());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void userAvailSeats_ValidId_ReturnsOkResponse() throws BookingException{
        String mockAvailableSeats = "A1,A2,A3";
        when(showRepository.getAvailSeats(anyLong())).thenReturn(mockAvailableSeats);

        ResponseEntity<?> response = showController.userAvailSeats(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions if needed
    }

    @Test
    void userAvailSeats_ExceptionThrown_ReturnsBadRequestResponse() throws BookingException{
        when(showRepository.getAvailSeats(anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = showController.userAvailSeats(2L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void userBookShow_ValidRequest_ReturnsOkResponse() throws BookingException {
        BookRequest mockBookRequest = new BookRequest("123456", "A1,A2,A3");
        ArrayList<String> mockBookList = new ArrayList<>();
        String mockTicketNumber = "T123";

        when(showRepository.bookShow(anyLong(), anyString(), anyList())).thenReturn(mockTicketNumber);

        ResponseEntity<?> response = showController.userBookShow(1L, mockBookRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions if needed
    }

    @Test
    void userBookShow_ExceptionThrown_ReturnsBadRequestResponse() throws BookingException {
        BookRequest mockBookRequest = new BookRequest("123456", "A1,A2,A3");
        when(showRepository.bookShow(anyLong(), anyString(), anyList()))
                .thenThrow(new BookingException("Error"));

        ResponseEntity<?> response = showController.userBookShow(1L, mockBookRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void userCancelSeats_ValidRequest_ReturnsOkResponse() throws BookingException{
        doNothing().when(showRepository).cancelSeats(anyString(), anyString());
        ResponseEntity<?> response = showController.userCancelSeats("showId", "123456");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions if needed
    }

    @Test
    void userCancelSeats_ExceptionThrown_ReturnsBadRequestResponse() throws BookingException{
        // Mock the void method to throw an exception
        doThrow(new RuntimeException("Error")).when(showRepository).cancelSeats(anyString(), anyString());

        // Perform the operation that calls the mocked method
        ResponseEntity<?> response = showController.userCancelSeats("showId", "123456");

        // Add assertions based on your application logic
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
