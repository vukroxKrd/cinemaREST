package cinema.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WrongTokenException extends RuntimeException {
    public WrongTokenException() {
    }
}
