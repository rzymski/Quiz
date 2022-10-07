package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private TextView punctation;

    private Question[] questions = new Question[]{
            new Question(R.string.q_pizza, true),
            new Question(R.string.q_hawajska, true),
            new Question(R.string.q_survive, true),
            new Question(R.string.q_like, true),
            new Question(R.string.q_forgive, false),
            new Question(R.string.q_combination, false)
    };

    private int currentIndex = 0;
    private int actualPoints = 0, givenAnsewers = 0;
    private boolean givenAnswer = false;

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(userAnswer == correctAnswer){
            resultMessageId = R.string.correct_answer;
            actualPoints++;
        }
        else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        punctation.setText("Zdobyte punkty: "+actualPoints+"/"+givenAnsewers);
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        punctation = findViewById(R.id.actual_punctation);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(givenAnswer == false) {
                    givenAnswer = true;
                    givenAnsewers++;
                    checkAnswerCorrectness(true);
                }
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(givenAnswer == false) {
                    givenAnswer = true;
                    givenAnsewers++;
                    checkAnswerCorrectness(false);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(givenAnswer == false) {
                    return;
                }

                currentIndex++;
                if(currentIndex == questions.length){
                    currentIndex = 0;
                    actualPoints = 0;
                    givenAnsewers = 0;
                }
                givenAnswer = false;
                punctation.setText("Zdobyte punkty: "+ actualPoints +"/"+givenAnsewers);
                //currentIndex = (currentIndex+1)%questions.length;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }
}