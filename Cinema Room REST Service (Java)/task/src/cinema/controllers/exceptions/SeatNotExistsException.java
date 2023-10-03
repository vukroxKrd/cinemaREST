package cinema.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SeatNotExistsException extends RuntimeException{

    public SeatNotExistsException() {
    }
    public SeatNotExistsException(String message) {
        super(message);
    }

    public SeatNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
