import controller.CheckoutController;
import controller.LoginController;
import controller.TourController;
import model.Booking;
import model.Checkout;
import model.Tour;
import model.User;
import repository.DatabaseInterface;
import repository.DatabaseMock;
import repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("Starting application...");

        DatabaseInterface tourDatabase = new DatabaseMock();
        UserRepository userRepository = new UserRepository(); 

        TourController tourController = new TourController(tourDatabase);
        LoginController loginController = new LoginController(userRepository);
        CheckoutController checkoutController = new CheckoutController(tourDatabase, userRepository);

        log.info("Controllers initialized.");


        // Sýna allar ferðir
        System.out.println("\n All Available Tours ");
        List<Tour> allTours = tourController.getAllTours();
        allTours.forEach(System.out::println);

        // Skrá nýjan notanda (eða nota mock notanda)
        User bookingUser = null;
        try {
            // Reynum að skrá nýjan
            // bookingUser = loginController.register("Booker", "pass1", "booker@mail.com");
            // System.out.println("Registered new user: " + bookingUser.getName());

            // Eða notum þann sem er þegar í UserRepository mock
             Optional<User> existingUser = loginController.login("testuser", "password123");
             if(existingUser.isPresent()) {
                 bookingUser = existingUser.get();
                 System.out.println("Logged in existing user: " + bookingUser.getName());
             } else {
                 System.out.println("Could not log in mock user 'testuser'.");
                 return; // Förum ekki lengra ef innskráning mistekst
             }

        } catch (IllegalArgumentException e) {
            System.err.println("Error with user: " + e.getMessage());
            // Reynum að logga inn default notanda ef skráning mistókst
             Optional<User> existingUser = loginController.login("testuser", "password123");
              if(existingUser.isPresent()) {
                 bookingUser = existingUser.get();
                 System.out.println("Logged in existing user instead: " + bookingUser.getName());
             } else {
                 System.out.println("Could not log in mock user 'testuser' after registration fail.");
                 return;
             }
        }

        if (bookingUser == null) {
             System.out.println("Failed to get a user for booking.");
             return;
        }


        // Velja ferð til að bóka (tökum bara fyrstu ferðina úr listanum)
        if (allTours.isEmpty()) {
            System.out.println("No tours to book.");
            return;
        }
        Tour tourToBook = allTours.get(0); // Tökum fyrstu ferðina
        System.out.println("\n Attempting to Book ");
        System.out.println("User: " + bookingUser.getName());
        System.out.println("Tour: " + tourToBook.getName() + " (ID: " + tourToBook.getId() + ")");
        System.out.println("Available spots: " + tourToBook.getAvailableSpots());

        // Setja upp bókunarupplýsingar dæmi
        int adults = 1;
        int children = 0;
        Checkout checkoutInfo = new Checkout(
                bookingUser.getName(), // Nota nafn notanda
                bookingUser.getEmail(), // Nota email notanda
                "123-4567",            // Símanúmer dæmi
                "4444-5555-6666-7777", // Kreditkort dæmi 
                "12/25",               // Gildistími dæmi
                "123"                  // CVV dæmi
        );

        // Reyna að framkvæma bókunina
        Optional<Booking> bookingResult = checkoutController.processCheckout(
                bookingUser.getId(),
                tourToBook.getId(),
                adults,
                children,
                checkoutInfo
        );

        // Athuga útkomuna
        if (bookingResult.isPresent()) {
            System.out.println("\n BOOKING SUCCESSFUL");
            System.out.println("Confirmation: " + bookingResult.get());

             // Sýnum að plássið á ferðinni hefur minnkað
             Optional<Tour> updatedTourOpt = tourController.getTourById(tourToBook.getId());
             if(updatedTourOpt.isPresent()) {
                 System.out.println("Updated available spots for " + updatedTourOpt.get().getName() + ": " + updatedTourOpt.get().getAvailableSpots());
             }

        } else {
            System.out.println("\n BOOKING FAILED ");
            System.out.println("Check logs for details (e.g., not enough spots, payment failed simulation).");
             // Sýnum plássið aftur til samanburðar
             Optional<Tour> failedTourOpt = tourController.getTourById(tourToBook.getId());
             if(failedTourOpt.isPresent()) {
                 System.out.println("Available spots remain: " + failedTourOpt.get().getAvailableSpots());
             }
        }

        // við viljum kannski bæta við þannig að notendur geta skoðað bókanir sínar
         // Sýna bókanir notandans (ef saveBooking/findBookingsByUserId eru útfærð)
         
      

        log.info("Finnished Application");
    }
}