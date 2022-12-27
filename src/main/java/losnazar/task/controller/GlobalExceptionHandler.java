package losnazar.task.controller;

import jakarta.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import losnazar.task.exception.CsvFileWritingException;
import losnazar.task.exception.ExternalDataFetchingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERRORS = "errors";

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(getBodyMap(status, ex), status);
    }

    @ExceptionHandler(value =
            {ExternalDataFetchingException.class, CsvFileWritingException.class})
    public ResponseEntity<Object> handleException(RuntimeException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(getBodyMap(status, ex), status);
    }

    private Map<String, Object> getBodyMap(HttpStatus status, Exception ex) {
        Map<String, Object> errorBodyMap = new LinkedHashMap<>();
        errorBodyMap.put(TIMESTAMP, LocalDateTime.now().toString());
        errorBodyMap.put(STATUS, status.value());
        errorBodyMap.put(ERRORS, String.join(", ",
                ex.getClass().getName(), ex.getMessage()));
        return errorBodyMap;
    }
}
