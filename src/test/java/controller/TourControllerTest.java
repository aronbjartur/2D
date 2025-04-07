// svo margt sem mig langar að test seinna 
//serstaklega login og checkout

package controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse; // ef það er database mock
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Tour;
import repository.DatabaseMock;

public class TourControllerTest {

    private TourController tourController;

    @BeforeEach
    public void setUp() {
        // ef við notum mock databaseið
        tourController = new TourController(new DatabaseMock());
    }

    @Test
    public void testDisplayAllTours() {
        List<Tour> tours = tourController.getAllTours();
        assertNotNull(tours, "ekki vera null");
        assertFalse(tours.isEmpty(), "listinn á ekki að vera tómur");
        assertEquals(12, tours.size(), "bjóst við 12 tours"); // 12 væri fjöldi tours í database
    }
}