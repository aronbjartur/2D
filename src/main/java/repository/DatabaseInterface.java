package repository;

import model.Tour;
import java.util.List;

public interface DatabaseInterface {
    List<Tour> generateTours();
}