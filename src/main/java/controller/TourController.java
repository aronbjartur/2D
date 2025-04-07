package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.Tour;
import repository.DatabaseInterface;

public class TourController {
    private final DatabaseInterface database;

    public TourController(DatabaseInterface database) {
        this.database = database;
    }

    // allir tours
    public List<Tour> getAllTours() {
        return database.getAllTours();
    }

     // all information frá tour út frá id
    public Optional<Tour> getTourById(String tourId) {
        return database.findTourById(tourId);
    }


    
    // filterar (2,3,9) location, dagsetning, mininum duration, maximum price
    public List<Tour> searchTours(String location, LocalDate date, Integer minDuration, Double maxPrice) {
        // vantar kannski eitthvað validation hér
        return database.findToursByCriteria(location, date, minDuration, maxPrice);
    }

    public List<Tour> getToursByDate(LocalDate date) {
         return database.findToursByCriteria(null, date, null, null);
    }

    public List<Tour> getToursByLocation(String location) {
         return database.findToursByCriteria(location, null, null, null);
    }
}