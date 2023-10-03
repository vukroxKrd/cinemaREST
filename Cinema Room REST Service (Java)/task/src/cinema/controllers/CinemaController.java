package cinema.controllers;

import cinema.controllers.exceptions.*;
import cinema.controllers.requests.SeatPurchaseRequest;
import cinema.controllers.responses.AdminStatisticsResponse;
import cinema.controllers.responses.PurchaseResponse;
import cinema.model.ReturnedTicket;
import cinema.model.Seat;
import cinema.services.CinemaHall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CinemaController {
    CinemaHall cinemaHall = CinemaHall.getInstance();

    @GetMapping("/seats")
    public ResponseEntity<CinemaHall> printAllSeats() {
        return new ResponseEntity<>(cinemaHall, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStatisticsResponse> adminStatistics(@RequestParam(required = false) String password) {
        if (password == null) {
            throw new AuthenticationException();
        }
        if (!password.isEmpty() && !"super_secret".equals(password) ) {
            throw new WrongAdminPasswordException();
        } else {
            int income = CinemaHall.purchasedSeats.values()
                    .stream()
                    .mapToInt(Seat::getPrice)
                    .sum();
            int bookedSeats = CinemaHall.purchasedSeats.size();
            int freeSeats = 81 - bookedSeats;

            AdminStatisticsResponse adminStatisticsResponse = new AdminStatisticsResponse(income, freeSeats, bookedSeats);
            return new ResponseEntity<>(adminStatisticsResponse, HttpStatus.OK);
        }

    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchaseSeat(@RequestBody SeatPurchaseRequest request) {
        List<Seat> seats = cinemaHall.getSeats();
        Seat seatInfo;
        int row = request.getRow();
        int column = request.getColumn();
        String token = UUID.randomUUID().toString();

        Optional<Seat> potentiallyExistingSeat = findSeatByRowAndColumn(seats, row, column);
        if (potentiallyExistingSeat.isEmpty()) {
            throw new SeatNotExistsException();
        } else if (potentiallyExistingSeat.isPresent() && potentiallyExistingSeat.get().isBooked()) {
            throw new SeatPurchasedException();
        } else {
            seatInfo = potentiallyExistingSeat.get();
            int index = seats.indexOf(seatInfo);
            seatInfo.setBooked(true);
            seats.add(index, seatInfo);

            CinemaHall.purchasedSeats.put(token, seatInfo);
        }
        return new ResponseEntity<>(new PurchaseResponse(token, seatInfo), HttpStatus.OK);
    }

    private static Optional<Seat> findSeatByRowAndColumn(List<Seat> seats, int row, int column) {
        return seats.stream().filter(seat -> row == seat.getRow() && column == seat.getColumn()).findFirst();
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnedTicket> returnSeat(@RequestBody String body) {
        String token;
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(body);
            token = jsonNode.get("token").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Seat seat = CinemaHall.purchasedSeats.get(token);
        if (seat != null) {
            ReturnedTicket returnedTicket = new ReturnedTicket(seat.getRow(), seat.getColumn(), seat.getPrice());
            Seat sameSeat = findSeatByRowAndColumn(cinemaHall.getSeats(), seat.getRow(), seat.getColumn()).get();
            sameSeat.setBooked(false);
            CinemaHall.purchasedSeats.remove(token);
            return new ResponseEntity<>(returnedTicket, HttpStatus.OK);
        } else {
            throw new WrongTokenException();
        }
    }
}
