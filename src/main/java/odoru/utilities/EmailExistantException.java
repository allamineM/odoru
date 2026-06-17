package odoru.utilities;
public class EmailExistantException extends RuntimeException {
    public EmailExistantException(String s) {
        super(s);
    }
}