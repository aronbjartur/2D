package controller;

import model.Booking;
import model.Checkout; 
import model.Tour;
import model.User;
import repository.DatabaseInterface; 
import repository.UserRepository;   
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class CheckoutController {

    private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);
    private final DatabaseInterface database;       // Fyrir Tours, Bookings
    private final UserRepository userRepository; // Fyrir Users

    
    public CheckoutController(DatabaseInterface database, UserRepository userRepository) {
        this.database = Objects.requireNonNull(database, "DatabaseInterface cannot be null");
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository cannot be null");
    }

    
    //checkout og notar userid, tourid, numberOfAdults, numberOfChildren, og check out details
    public Optional<Booking> processCheckout(String userId, String tourId, int numberOfAdults, int numberOfChildren, Checkout checkoutDetails) {

        log.info("Attempting checkout for User ID: {}, Tour ID: {}, Adults: {}, Children: {}",
                 userId, tourId, numberOfAdults, numberOfChildren);

        // error höndlun
        if (userId == null || tourId == null || checkoutDetails == null || (numberOfAdults < 0) || (numberOfChildren < 0) || (numberOfAdults + numberOfChildren <= 0)) {
            log.error("Checkout failed: Invalid input parameters. UserID: {}, TourID: {}, Adults: {}, Children: {}", userId, tourId, numberOfAdults, numberOfChildren);
            return Optional.empty();
        }
        int totalParticipants = numberOfAdults + numberOfChildren;

        // Find user and tour
        Optional<User> userOpt = userRepository.findUserById(userId); 
        if (userOpt.isEmpty()) {
            log.error("Checkout failed: User with ID {} not found.", userId);
            return Optional.empty();
        }

        Optional<Tour> tourOpt = database.findTourById(tourId); 
        if (tourOpt.isEmpty()) {
            log.error("Checkout failed: Tour with ID {} not found.", tourId);
            return Optional.empty();
        }
        Tour tour = tourOpt.get();

        // check availability, þetta ætti kannski að vera í db-inu sjálfu
        synchronized (tour) {
            if (tour.getAvailableSpots() < totalParticipants) {
                log.warn("Checkout failed: Not enough spots available for Tour '{}'. Required: {}, Available: {}",
                         tour.getName(), totalParticipants, tour.getAvailableSpots());
                return Optional.empty();
            }

            // reikna verð
            BigDecimal adultPriceTotal = tour.getPriceAdult().multiply(BigDecimal.valueOf(numberOfAdults));
            BigDecimal childPriceTotal = tour.getPriceChild().multiply(BigDecimal.valueOf(numberOfChildren));
            BigDecimal totalPrice = adultPriceTotal.add(childPriceTotal);

            // borga, þetta er bara simulation
            // kannski breytta þessu
            boolean paymentSuccessful = simulatePayment(checkoutDetails, totalPrice);
            if (!paymentSuccessful) {
                log.error("Checkout failed: Payment processing failed for Tour '{}', Amount: {}", tour.getName(), totalPrice);
                return Optional.empty(); // Stoppa ef greiðsla mistekst
            }
            log.info("Simulated payment successful for amount: {}", totalPrice);


            // Update Tour Availability (ef greiðsla tókst)
            boolean spotsBooked = tour.bookSpots(totalParticipants);
            if (!spotsBooked) {
                // auka öryggi, ætti ekki að gerast
                 log.error("CRITICAL: Checkout failed: Could not book spots for Tour '{}' AFTER successful payment simulation. Availability check/update failed.", tour.getName());
                 // Hér þyrfti að reyna að "rollback" greiðslu ef hægt væri
                 return Optional.empty();
            }
            // Vista uppfærða ferð (minnkað pláss)
            boolean updated = database.updateTour(tour);
            if (!updated) {
                 log.error("CRITICAL: Failed to update tour availability in database for tour ID {} after booking.", tour.getId());
                 // Hér gæti þurft að reyna "rollback" á bókun/greiðslu
                 // Í mockinu er þetta ólíklegt en mikilvægt í alvöru
                 tour.releaseSpots(totalParticipants); // Reyna að laga stöðuna í minni
                 return Optional.empty();
            }


            // create and svae booking records
            Booking booking = new Booking(
                    tour.getId(),
                    tour.getName(), 
                    userId,
                    numberOfAdults,
                    numberOfChildren,
                    totalPrice
                    // Hægt að bæta við fleiri upplýsingum ef þarf
            );
            Booking savedBooking = database.saveBooking(booking); // Vista bókun í DB

            log.info("Checkout successful! Booking created: {}", savedBooking);

            // kannski þetta: 
            // senda staðfestingarpóst...
            // sendConfirmationEmail(checkoutDetails.getEmail(), savedBooking, tour);

            return Optional.of(savedBooking); // Skila bókuninni sem tókst
        } 
    }

    // Simulate payment processing
    // Í alvöru kerfi væri þetta kallað á greiðsluveitu
    private boolean simulatePayment(Checkout details, BigDecimal amount) {
        log.debug("Simulating payment for {} using details: {}", amount, details);
        // Einföld eftirlíking: skilar true ef kortanúmer er ekki tómt
        boolean success = details.getCreditCardNumber() != null && !details.getCreditCardNumber().isBlank();
        if (!success) log.warn("Payment simulation failed for amount {}", amount);
        return success;
    }

    
}