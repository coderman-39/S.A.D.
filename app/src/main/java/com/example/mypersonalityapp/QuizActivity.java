package com.example.mypersonalityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private RadioButton radioOption1, radioOption2, radioOption3, radioOption4;
    private Button buttonNext, buttonBack;

    // Hardcoded questions, options, and their respective scores
    private String[] questions = {
            "What is your favorite hobby?",
            "How do you react in stressful situations?",
            "What kind of music do you prefer?"
    };

    private String[][] options = {
            {"Reading (Score: 1)", "Sports (Score: 2)", "Traveling (Score: 3)", "Gaming (Score: 4)"},
            {"Calm and analytical (Score: 1)", "Get a bit anxious (Score: 2)", "Remain energetic (Score: 3)", "Panic (Score: 4)"},
            {"Classical (Score: 1)", "Rock (Score: 2)", "Pop (Score: 3)", "Jazz (Score: 4)"}
    };

    private int[][] scores = {
            {1, 2, 3, 4},
            {1, 2, 3, 4},
            {1, 2, 3, 4}
    };

    // Current question index and an array to store selected answer (option index) for each question
    private int currentQuestionIndex = 0;
    private int[] selectedAnswers; // -1 if not answered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioOption1 = findViewById(R.id.radioOption1);
        radioOption2 = findViewById(R.id.radioOption2);
        radioOption3 = findViewById(R.id.radioOption3);
        radioOption4 = findViewById(R.id.radioOption4);
        buttonNext = findViewById(R.id.buttonNext);
        buttonBack = findViewById(R.id.buttonBack);

        // Initialize selectedAnswers array with -1 (i.e. unanswered)
        selectedAnswers = new int[questions.length];
        for (int i = 0; i < questions.length; i++) {
            selectedAnswers[i] = -1;
        }

        loadQuestion();

        // Next Button Handler
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedOptionId = radioGroupOptions.getCheckedRadioButtonId();
                if (selectedOptionId == -1) {
                    // No option selected; optionally inform the user.
                    return;
                }
                int optionIndex = radioGroupOptions.indexOfChild(findViewById(selectedOptionId));
                selectedAnswers[currentQuestionIndex] = optionIndex;

                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    loadQuestion();
                } else {
                    // End of quiz; compute total score.
                    int totalScore = computeTotalScore();
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("TOTAL_SCORE", totalScore);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Back Button Handler
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    loadQuestion();
                }
            }
        });
    }

    // Load current question and preselect answer if already chosen
    private void loadQuestion() {
        textViewQuestion.setText(questions[currentQuestionIndex]);
        radioOption1.setText(options[currentQuestionIndex][0]);
        radioOption2.setText(options[currentQuestionIndex][1]);
        radioOption3.setText(options[currentQuestionIndex][2]);
        radioOption4.setText(options[currentQuestionIndex][3]);
        radioGroupOptions.clearCheck();

        // If user had already answered this question, pre-select that option
        if (selectedAnswers[currentQuestionIndex] != -1) {
            RadioButton previouslySelected = (RadioButton) radioGroupOptions.getChildAt(selectedAnswers[currentQuestionIndex]);
            if (previouslySelected != null) {
                previouslySelected.setChecked(true);
            }
        }

        // Enable back button only if not on the first question
        buttonBack.setEnabled(currentQuestionIndex > 0);
    }

    // Compute the total score by summing scores from selected answers
    private int computeTotalScore() {
        int total = 0;
        for (int i = 0; i < questions.length; i++) {
            if (selectedAnswers[i] != -1) {
                total += scores[i][selectedAnswers[i]];
            }
        }
        return total;
    }
}
