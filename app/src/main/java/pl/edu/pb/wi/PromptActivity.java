package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    private boolean correctAnswer;
    private TextView wantSeeAnswerTextView;
    private Button showPromptButton;
    private TextView answerTextView;
    private Button backButton;

    public static final String KEY_EXTRA_ANSWER_SHOWN = "asfsdfs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PromptActivity","PromptActivity Wywolana zostala metoda cyklu zycia: onCREATE");
        setContentView(R.layout.activity_prompt);

        wantSeeAnswerTextView = findViewById(R.id.wantSeeAnswerTextView);
        showPromptButton = findViewById(R.id.showAnswerButton);
        answerTextView = findViewById(R.id.answerTextView);
        backButton = findViewById(R.id.backButton);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);

        showPromptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PromptActivity","Wywolano: showPromptButton");
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShowResult(true);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PromptActivity","Wywolano: backButton");
                finish();
            }
        });
    }
    private void setAnswerShowResult(boolean answerWasShown){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }
}