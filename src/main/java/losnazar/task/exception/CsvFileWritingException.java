package losnazar.task.exception;

public class CsvFileWritingException extends RuntimeException {
    public CsvFileWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
