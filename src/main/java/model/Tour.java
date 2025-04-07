package model;

import java.math.BigDecimal; // Use BigDecimal for currency
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Tour {
    private final String id; // hafa id á tour
    private String name;
    private int durationHours; //(User Story 7)
    private String location;
    private String description; // (User Story 4)
    private String imageUrl; // (User Story 4) þá vantar mynd
    private LocalDate date; // (User Story 2)
    private BigDecimal priceAdult; // (User Story 12)
    private BigDecimal priceChild; // (User Story 12)
    private int capacity; // (User Story 5)
    private int bookedSpots; // (User Story 5)
    private boolean transportationIncluded; // (User Story 8)
    // bæta seinna reviews (User Story 6) List<Review> reviews;
    

    public Tour(String name, int durationHours, String location, String description,
                LocalDate date, BigDecimal priceAdult, BigDecimal priceChild,
                int capacity, boolean transportationIncluded, String imageUrl) {
        this.id = UUID.randomUUID().toString(); // býr til random ID fyrir tour
        // sama null check á allar breytur. allir reitir þurfa að vera fylltir
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.durationHours = durationHours;
        this.location = Objects.requireNonNull(location, "Location cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.priceAdult = Objects.requireNonNull(priceAdult, "Adult price cannot be null");
        this.priceChild = Objects.requireNonNull(priceChild, "Child price cannot be null");
        if (priceAdult.compareTo(BigDecimal.ZERO) < 0 || priceChild.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }
        this.capacity = capacity;
        if (capacity <= 0) {
             throw new IllegalArgumentException("Capacity must be positive");
        }
        this.bookedSpots = 0; // byrjar tóm, þetta má kannski breytta og skoða til að prufa að hafa fullt tour
        this.transportationIncluded = transportationIncluded;
        this.imageUrl = imageUrl; // myndir meiga vera null
    }

    // Getters 
    public String getId() { return id; }
    public String getName() { return name; }
    public int getDurationHours() { return durationHours; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public BigDecimal getPriceAdult() { return priceAdult; }
    public BigDecimal getPriceChild() { return priceChild; }
    public int getCapacity() { return capacity; }
    public int getBookedSpots() { return bookedSpots; }
    public boolean isTransportationIncluded() { return transportationIncluded; }
    public String getImageUrl() { return imageUrl; }

    

    public int getAvailableSpots() { // (User Story 5)
        return capacity - bookedSpots;
    }

    // true ef spotts eru bókaðir, false ef ekki
    public boolean bookSpots(int spots) { // (booking hjalp fyrir 5, 10, 11)
        if (spots > 0 && getAvailableSpots() >= spots) {
            this.bookedSpots += spots;
            return true;
        }
        return false;
    }

     // releaseSpots fyrir canelation
    public void releaseSpots(int spots) {
        if (spots > 0) {
            this.bookedSpots = Math.max(0, this.bookedSpots - spots); // passar að fer aldrei undir 0
        }
    }

    //checkar hvort tour sé sama með því að skoða id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id.equals(tour.id); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Tour{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", date=" + date +
               ", location='" + location + '\'' +
               ", duration=" + durationHours + "h" +
               ", available=" + getAvailableSpots() + "/" + capacity +
               ", priceAdult=" + priceAdult +
               '}';
    }
}