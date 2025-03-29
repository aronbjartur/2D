package controller;

import java.util.List;
import model.Tour;
import repository.DatabaseInterface;

public class TourController {
    private final DatabaseInterface database;

    public TourController(DatabaseInterface database) {
        this.database = database;
    }

    public List<Tour> getAllTours() {
        return database.generateTours();
    }
}