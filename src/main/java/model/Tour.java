package model;

import java.time.LocalDate;

public class Tour {
    private String name;
    private int duration;
    private String location;
    private String description;
    private LocalDate date;

    public Tour(String name, int duration, String location, String description, LocalDate date) {
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void proceedToCheckout() { 
        // TODO
    }
}