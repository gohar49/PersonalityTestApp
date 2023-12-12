// PersonalityQuestion.java
public class PersonalityQuestion {
    private String question;
    private int answer;

    public PersonalityQuestion(String question) {
        this.question = question;
        this.answer = 3; // Default answer, you can change it based on your requirements
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
