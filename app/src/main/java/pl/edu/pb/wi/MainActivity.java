package pl.edu.pb.wi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private TextView punctation;
    private Button promptButton;
    private boolean answerWasShown;

    private Question[] questions = new Question[]{
            new Question(R.string.q_pizza, true),
            new Question(R.string.q_hawajska, true),
            new Question(R.string.q_survive, true),
            new Question(R.string.q_like, true),
            new Question(R.string.q_forgive, false),
            new Question(R.string.q_combination, false)
    };

    private int currentIndex = 0;
    private int actualPoints = 0, givenAnswers = 0;
    private boolean givenLastAnswer = false;

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }
        else if(userAnswer == correctAnswer){
            resultMessageId = R.string.correct_answer;
            actualPoints++;
        }
        else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        punctation.setText("Zdobyte punkty: "+actualPoints+"/"+givenAnswers);
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","Wywolana zostala metoda cyklu zycia: onSTART");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","Wywolana zostala metoda cyklu zycia: onRESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","Wywolana zostala metoda cyklu zycia: onPAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","Wywolana zostala metoda cyklu zycia: onSTOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","Wywolana zostala metoda cyklu zycia: onDESTROY");
    }

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_ACTUAL_POINTS = "actualPoints";
    private static final String KEY_GIVEN_ANSWERS = "givenAnswers";
    private static final String KEY_GIVEN_LAST_ANSWER = "givenLastAnswer";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    public static final int REQUEST_CODE_PROMPT = 0;
    ActivityResultLauncher<Intent> PromptActivityResultLauncher;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("QUIZ_TAG", "Wywolana zostala metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putInt(KEY_ACTUAL_POINTS, actualPoints);
        outState.putInt(KEY_GIVEN_ANSWERS, givenAnswers);
        outState.putBoolean(KEY_GIVEN_LAST_ANSWER, givenLastAnswer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","Wywolana zostala metoda cyklu zycia: onCREATE");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            actualPoints = savedInstanceState.getInt(KEY_ACTUAL_POINTS);
            givenAnswers = savedInstanceState.getInt(KEY_GIVEN_ANSWERS);
            givenLastAnswer = savedInstanceState.getBoolean(KEY_GIVEN_LAST_ANSWER);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        punctation = findViewById(R.id.actual_punctation);

        ActivityResultLauncher<Intent> PromptActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == PromptActivity.RESULT_OK) {
                            Intent data = result.getData();
                            if(data == null) { return; }
                            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
                        }
                    }
                });

        promptButton = findViewById(R.id.promptButton);
        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                //startActivity(intent);
                PromptActivityResultLauncher.launch(intent);
            }
        });

        punctation.setText("Zdobyte punkty: "+ actualPoints +"/"+givenAnswers);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(givenLastAnswer == false) {
                    givenLastAnswer = true;
                    givenAnswers++;
                    checkAnswerCorrectness(true);
                }
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(givenLastAnswer == false) {
                    givenLastAnswer = true;
                    givenAnswers++;
                    checkAnswerCorrectness(false);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(givenLastAnswer == false) {
                    return;
                }

                currentIndex++;
                if(currentIndex == questions.length){
                    currentIndex = 0;
                    actualPoints = 0;
                    givenAnswers = 0;
                }
                givenLastAnswer = false;
                punctation.setText("Zdobyte punkty: "+ actualPoints +"/"+givenAnswers);
                //currentIndex = (currentIndex+1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }
}