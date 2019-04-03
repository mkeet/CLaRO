package messages;

public class AddNewQuestionEvent {

    public String question;

    public AddNewQuestionEvent(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
