package repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional; 

import model.Booking;
import model.Tour;
import model.User; 

public interface DatabaseInterface {

    
    List<Tour> getAllTours(); // (User Story 1)

    
    // filterar (2,3,9) location, dagsetning, mininum duration, maximum price
    List<Tour> findToursByCriteria(String location, LocalDate date, Integer minDuration, Double maxPrice);

    
    Optional<Tour> findTourById(String tourId); 

    boolean updateTour(Tour tour); 

    void addUser(User user);
    Optional<User> findUserByName(String name);
    Optional<User> findUserById(String userId);

    // Saves a new booking to the database.
    Booking saveBooking(Booking booking); // (User Story 10)

    // bookings by user id
    List<Booking> findBookingsByUserId(String userId);

     // booking by id
    Optional<Booking> findBookingById(String bookingId);

    // removeTour, removeUser, removeBooking gæti verið hér
}