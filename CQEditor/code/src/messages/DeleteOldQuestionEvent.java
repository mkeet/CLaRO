package messages;

public class DeleteOldQuestionEvent {

    private String question;

    public DeleteOldQuestionEvent(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
