package messages;

public class InformUserEvent {

    private String message;

    public InformUserEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
