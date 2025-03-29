// veit ekki með þetta. þetta væri þá þar sem db væri geymt ef við erum ekki með það annars staðar eins og sql

package repository;
import model.Tour;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMock implements DatabaseInterface {

    @Override
    public List<Tour> generateTours() {
        List<Tour> tours = new ArrayList<>();
        tours.add(new Tour("Golden Circle", 8, "Iceland", "Visit the Golden Circle", LocalDate.of(2021, 6, 1)));
        tours.add(new Tour("Blue Lagoon", 6, "Iceland", "Relax in the Blue Lagoon", LocalDate.of(2021, 6, 2)));
        tours.add(new Tour("Northern Lights", 10, "Iceland", "See the Northern Lights", LocalDate.of(2021, 6, 3)));
        return tours;
    }

}