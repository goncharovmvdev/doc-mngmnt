package doc.mngmnt.service.impl.notification;

public class NoEmailSpecifiedException extends RuntimeException {

    public NoEmailSpecifiedException() {
        super();
    }

    public NoEmailSpecifiedException(String message) {
        super(message);
    }

    public NoEmailSpecifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}
