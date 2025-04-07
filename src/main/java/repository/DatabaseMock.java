// setti virkni hingað inn líka, ekki bara mock 

package repository;

import model.Tour;

import java.math.BigDecimal; 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class DatabaseMock implements DatabaseInterface {

    // Geymir ferðirnar í minni
    private final Map<String, Tour> tours = new ConcurrentHashMap<>();
    // kannski svona
    // private final Map<String, Booking> bookings = new ConcurrentHashMap<>();

    public DatabaseMock() {
        // Búa til upphafsferðirnar þegar mock-ið er búið til
        generateInitialTours();
    }

    // ferðirnar 12
    private void generateInitialTours() {
        addTourInternal(new Tour("Golden Circle Classic", 8, "South Iceland", "Visit Þingvellir, Geysir, and Gullfoss.", LocalDate.now().plusDays(10), new BigDecimal("11000"), new BigDecimal("5500"), 50, true, "img/golden_circle.jpg"));
        addTourInternal(new Tour("Blue Lagoon Comfort", 6, "Reykjanes Peninsula", "Relax in the geothermal spa.", LocalDate.now().plusDays(12), new BigDecimal("13500"), new BigDecimal("6750"), 40, false, "img/blue_lagoon.jpg"));
        addTourInternal(new Tour("South Coast Adventure", 10, "South Iceland", "Waterfalls, black sand beaches, Reynisfjara.", LocalDate.now().plusDays(10), new BigDecimal("16000"), new BigDecimal("8000"), 30, true, "img/south_coast.jpg"));
        addTourInternal(new Tour("Northern Lights Hunt", 4, "Reykjavik Area", "Search for the Aurora Borealis (Winter).", LocalDate.now().plusMonths(6), new BigDecimal("9500"), new BigDecimal("4750"), 60, true, "img/northern_lights.jpg"));
        addTourInternal(new Tour("Reykjavik City Walk", 3, "Reykjavik", "Explore the capital city highlights.", LocalDate.now().plusDays(5), new BigDecimal("6000"), new BigDecimal("3000"), 25, false, null));
        addTourInternal(new Tour("Jökulsárlón Glacier Lagoon", 14, "Southeast Iceland", "Visit the stunning glacier lagoon.", LocalDate.now().plusDays(20), new BigDecimal("22000"), new BigDecimal("11000"), 25, true, "img/jokulsarlon.jpg"));
        addTourInternal(new Tour("Akureyri & Lake Mývatn", 9, "North Iceland", "Explore the northern capital and unique nature.", LocalDate.now().plusDays(30), new BigDecimal("19000"), new BigDecimal("9500"), 35, true, "img/akureyri_myvatn.jpg"));
        addTourInternal(new Tour("Snaefellsnes Peninsula", 10, "West Iceland", "Journey through 'Iceland in Miniature'.", LocalDate.now().plusDays(15), new BigDecimal("17500"), new BigDecimal("8750"), 30, true, "img/snaefellsnes.jpg"));
        addTourInternal(new Tour("Westfjords Wonders", 12, "Westfjords", "Explore remote fjords and cliffs (Summer).", LocalDate.now().plusMonths(2), new BigDecimal("25000"), new BigDecimal("12500"), 15, false, "img/westfjords.jpg"));
        addTourInternal(new Tour("Diamond Beach Photography", 4, "Southeast Iceland", "Capture icebergs on the black sand.", LocalDate.now().plusDays(21), new BigDecimal("12000"), new BigDecimal("6000"), 20, false, "img/diamond_beach.jpg"));
        addTourInternal(new Tour("Into the Glacier", 5, "West Iceland", "Explore man-made ice tunnels in Langjökull.", LocalDate.now().plusDays(18), new BigDecimal("29000"), new BigDecimal("14500"), 40, true, "img/into_glacier.jpg"));
        addTourInternal(new Tour("Highland Hiking Adventure", 8, "Highlands", "Hike through Iceland's interior (Summer).", LocalDate.now().plusMonths(1), new BigDecimal("21000"), new BigDecimal("10500"), 18, false, "img/highlands.jpg"));

        // Prenta út til að staðfesta fjölda
        System.out.println("DatabaseMock initialized with " + tours.size() + " tours.");
    }

    // Innra fall til að setja ferð í 'tours' Mapið
    private void addTourInternal(Tour tour) {
        this.tours.put(tour.getId(), tour);
    }


    @Override
    public List<Tour> getAllTours() {
        return new ArrayList<>(tours.values()); // Skila afriti
    }

    @Override
    public Optional<Tour> findTourById(String tourId) {
        return Optional.ofNullable(tours.get(tourId));
    }

    @Override
    public List<Tour> findToursByCriteria(String location, LocalDate date, Integer minDuration, Double maxPrice) {
        // Filter logic 
        return tours.values().stream()
                .filter(tour -> location == null || tour.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(tour -> date == null || tour.getDate().equals(date))
                .filter(tour -> minDuration == null || tour.getDurationHours() >= minDuration)
                .filter(tour -> maxPrice == null || tour.getPriceAdult().compareTo(BigDecimal.valueOf(maxPrice)) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateTour(Tour tour) {
        // Einföld uppfærsla fyrir mock (skiptir út hlutnum)
        if (tour != null && tours.containsKey(tour.getId())) {
            tours.put(tour.getId(), tour);
            return true;
        }
        return false;
    }

   
}