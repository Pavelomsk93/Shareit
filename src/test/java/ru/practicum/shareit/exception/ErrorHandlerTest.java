package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exceptions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorHandlerTest {

    private final ErrorHandler errorHandler = new ErrorHandler();

    @Test
    void defaultHandle() {
        Throwable exception = new Throwable("INTERNAL_SERVER_ERROR");
        ErrorResponse response = errorHandler.handleThrowable(exception);
        assertNotNull(response);
        assertEquals(response.getError(), exception.getMessage());
    }

    @Test
    void handleIncorrectParameterException() {
        ValidationException exception = new ValidationException("BAD_REQUEST");
        ErrorResponse response = errorHandler.handleIncorrectParameterException(exception);
        assertNotNull(response);
        assertEquals(response.getError(), exception.getMessage());
    }

    @Test
    void bookingException() {
        BookingException exception = new BookingException("BAD_REQUEST");
        ErrorResponse response = errorHandler.bookingException(exception);
        assertNotNull(response);
        assertEquals(response.getError(), exception.getMessage());
    }

    @Test
    void entityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("NOT_FOUND");
        ErrorResponse response = errorHandler.handleEntityNotFoundException(exception);
        assertNotNull(response);
        assertEquals(response.getError(), exception.getMessage());
    }
}
