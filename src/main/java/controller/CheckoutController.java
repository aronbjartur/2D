package controller;
import model.Booking;
import model.Checkout;
import model.Tour;

public class CheckoutController {
    private Tour tour;
    private Booking booking;
    private Checkout checkout;

    public CheckoutController(Tour tour, Booking booking, Checkout checkout) {
        this.tour = tour;
        this.booking = booking;
        this.checkout = checkout;
    }

    public void processCheckout() {
        // Logic to process the checkout
        // This could involve validating the booking, processing payment, etc.
    }

    public Tour getTour() {
        return tour;
    }

    public Booking getBooking() {
        return booking;
    }

    public Checkout getCheckout() {
        return checkout;
    }
}