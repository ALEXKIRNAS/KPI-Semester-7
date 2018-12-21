package client;

import java.util.List;

public class Question {

    private String questionStr;

    private List<String> answers;


    public Question(String questionStr, List<String> answers) {
        this.questionStr = questionStr;
        this.answers = answers;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public List<String> getAnswers() {
        return answers;
    }

}
