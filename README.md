# Booking a Show Program

This application is designed to facilitate the booking of show tickets, providing both a Spring-based web interface and a console for user interaction.
The program utilizes Spring Boot and runs on a Tomcat server. It offers a dual interface, allowing users to interact with the application through a web interface and a console that runs on parallel.

---

### How to Run

To run the program, execute the BookApplication class. This will start the Spring Boot application and launch the console. Users can choose between the roles of a buyer or an admin.


### Admin Commands:

**Setup: Configure a new show.******
```bash
setup [showId] [rowsAvailable] [columnsAvailable] [cancellationWindow]
```
Returns "Success" if successfully created, and an error if not.


**View: View details of a specific show.**
```bash
view [showId]
```
Returns the "show" and the details of the already booked for the show.


Exit: Exit admin mode.



### Buyer Commands:

**Availability: Check the availability of seats for a show.**
```bash
availability [showId]
```
Returns a comma separated list of the available seats for the specific show.


**Book: Reserve seats for a show.**
```bash
book [showId] [phoneNumber] [seats,comma seperated]
```
Returns "Success" if it is successfully booked, otherwise an error will appear.


**Cancel: Cancel a booking.**
```
cancel [ticketId] [phoneNumber]
```
Returns "Success" if it is successfully cancelled. Note: Cancellation can only happen within the time frame given on the setup show command on admin.

Exit: Exit buyer mode.

---

### To-do:

- Create more tests that has 100% code coverage
- Added a phone number regex checker
- Code clean-up
