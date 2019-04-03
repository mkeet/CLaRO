package messages;

public class SaveFileEvent {

    private String filename;

    public SaveFileEvent(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
