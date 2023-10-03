package cinema.services;

import cinema.model.Seat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CinemaHall {
    @JsonProperty("rows")
//    @JsonIgnore
    private final int totalRows = 9;
    @JsonProperty("columns")
//    @JsonIgnore
    private final int totalColumns = 9;
    @JsonProperty("seats")
    private final List<Seat> seats = new ArrayList<>();
    @JsonIgnore
    public static Map<String, Seat> purchasedSeats ;

    private CinemaHall() {
    }

    private static class HallFiller {
        private static final CinemaHall hall = new CinemaHall();

        static {
            assignSeats();
            purchasedSeats = new ConcurrentHashMap<>();
        }

        private static List<Seat> assignSeats() {
            for (int i = 1; i < (10); i++) {
                for (int j = 1; j < (10); j++) {
                    Seat seat = new Seat(i, j);
                    if (i <= 4) {
                        seat.setPrice(10);
                    } else {
                        seat.setPrice(8);
                    }
                    hall.seats.add(seat);
                }
            }
            return hall.seats;
        }
    }

    public static CinemaHall getInstance() {
        return HallFiller.hall;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
