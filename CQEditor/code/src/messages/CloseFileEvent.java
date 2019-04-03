package messages;

public class CloseFileEvent {

    private String filename;

    public CloseFileEvent(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
