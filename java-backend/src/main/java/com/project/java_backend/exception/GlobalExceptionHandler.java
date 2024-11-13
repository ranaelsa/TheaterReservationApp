package com.project.java_backend.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity
			.status(HttpStatus.NOT_ACCEPTABLE)
			.body(ex.getMessage());
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
		return ResponseEntity
			.status(HttpStatus.NOT_ACCEPTABLE)
			.body(ex.getMessage());
	}

	@ExceptionHandler(SeatNotAvailableException.class)
	public ResponseEntity<Object> handleSeatNotAvailableException(SeatNotAvailableException ex) {
		return ResponseEntity
			.status(HttpStatus.NOT_ACCEPTABLE)
			.body(ex.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(ex.getMessage());
	}

	// Add more exception handlers here for other types of exception that you want to send to the client
}
