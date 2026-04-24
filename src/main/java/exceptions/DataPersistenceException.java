package exceptions;
public class DataPersistenceException extends UniversityException {
    
    public DataPersistenceException(String message) {
        super(message);
    }
    
    public DataPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}

