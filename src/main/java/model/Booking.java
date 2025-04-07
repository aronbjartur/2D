package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Booking {

    public enum Status { PENDING, CONFIRMED, CANCELLED }

    private final String id;
    private final String tourId;
    private final String userId;
    private final LocalDateTime bookingTimestamp;
    private int numberOfAdults;
    private int numberOfChildren;
    private BigDecimal totalPrice;
    private Status status;
    private String tourName; 

    public Booking(String tourId, String tourName, String userId, int numberOfAdults, int numberOfChildren, BigDecimal totalPrice) {
        this.id = UUID.randomUUID().toString();
        this.tourId = Objects.requireNonNull(tourId, "Tour ID cannot be null");
        this.tourName = Objects.requireNonNull(tourName, "Tour name cannot be null");
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
        if (numberOfAdults < 0 || numberOfChildren < 0 || (numberOfAdults + numberOfChildren <= 0)) {
            throw new IllegalArgumentException("Amount of people must be positive.");
        }
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.totalPrice = Objects.requireNonNull(totalPrice, "Total price cannot be null");
         if (totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        this.bookingTimestamp = LocalDateTime.now();
        this.status = Status.CONFIRMED; 
    }

    // Getters 
    public String getId() { return id; }
    public String getTourId() { return tourId; }
    public String getUserId() { return userId; }
    public LocalDateTime getBookingTimestamp() { return bookingTimestamp; }
    public int getNumberOfAdults() { return numberOfAdults; }
    public int getNumberOfChildren() { return numberOfChildren; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public Status getStatus() { return status; }
    public String getTourName() { return tourName; }


    // segir sig sjálft
     public int getTotalParticipants() {
        return numberOfAdults + numberOfChildren;
     }

    public void setStatus(Status status) {
        this.status = Objects.requireNonNull(status);
    }


    // sama equals 
     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id.equals(booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Booking{" +
               "id='" + id + '\'' +
               ", tourId='" + tourId + '\'' +
               ", userId='" + userId + '\'' +
               ", adults=" + numberOfAdults +
               ", children=" + numberOfChildren +
               ", price=" + totalPrice +
               ", status=" + status +
               ", time=" + bookingTimestamp +
               '}';
    }
}