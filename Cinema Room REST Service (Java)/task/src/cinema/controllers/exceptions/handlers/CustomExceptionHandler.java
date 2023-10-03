package cinema.controllers.exceptions.handlers;

import cinema.controllers.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SeatPurchasedException.class)
    public ResponseEntity<Object> handleSeatPurchasedExceptions(SeatPurchasedException ex) {
        // Create a custom error response JSON
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "The ticket has been already purchased!",
                "/purchase"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatNotExistsException.class)
    public ResponseEntity<Object> handleSeatNotExistsException(SeatNotExistsException ex) {
        // Create a custom error response JSON
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "The number of a row or a column is out of bounds!",
                "/purchase"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "The password is wrong!",
                "/stats"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.UNAUTHORIZED,
//                "Required parameter 'password' is not present.",
//                "/stats"
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(WrongTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ShortErrorResponse tokenNotFoundException(WrongTokenException ex) {
        return new ShortErrorResponse("Wrong token!");
    }

    @ExceptionHandler(WrongAdminPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> passwordIsWrongException(WrongAdminPasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "The password is wrong!",
                "/stats"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    public static class ShortErrorResponse {
        private final String error;

        public ShortErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }

    // Define a custom ErrorResponse class to structure your JSON response
    public static class ErrorResponse {
        private final String timestamp;
        private final int status;
        private final String error;
        private final String path;

        public ErrorResponse(HttpStatus status, String error, String path) {
            this.timestamp = String.valueOf(Instant.now());
            this.status = status.value();
            this.error = error;
            this.path = path;
        }


        public String getTimestamp() {
            return timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getPath() {
            return path;
        }
    }
}
