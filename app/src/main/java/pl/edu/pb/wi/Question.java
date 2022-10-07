package pl.edu.pb.wi;

public class Question {
    private int questionId;
    private boolean trueAnswer;

    public Question(int questionId, boolean trueAnswer)
    {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
    }

    protected boolean isTrueAnswer()
    {
        return trueAnswer;
    }

    protected int getQuestionId()
    {
        return questionId;
    }
}
