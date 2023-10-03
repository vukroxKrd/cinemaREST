package cinema.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SeatPurchasedException extends RuntimeException{

    public SeatPurchasedException() {
    }

    public SeatPurchasedException(String message) {
        super(message);
    }
}
