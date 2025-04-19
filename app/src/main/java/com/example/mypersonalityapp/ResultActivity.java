package com.example.mypersonalityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewResult;
    private TextView textViewScore;
    private Button buttonRetake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        textViewResult = findViewById(R.id.textViewResult);
        textViewScore = findViewById(R.id.textViewScore);
        buttonRetake = findViewById(R.id.buttonRetake);

        Intent intent = getIntent();
        int totalScore = intent.getIntExtra("TOTAL_SCORE", 0);
        textViewScore.setText("Your Total Score: " + totalScore);

        String personalityType = determinePersonality(totalScore);
        textViewResult.setText("Personality Type: " + personalityType);

        // Retake Test: Restart the quiz when the button is clicked.
        buttonRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Simple mapping from score to personality type.
    private String determinePersonality(int score) {
        if (score <= 4) {
            return "Introvert - Calm, reflective and thoughtful.";
        } else if (score <= 8) {
            return "Ambivert - Balanced and adaptable.";
        } else if (score <= 11) {
            return "Extrovert - Energetic, outgoing and enthusiastic.";
        } else {
            return "Dynamic Leader - Bold, proactive and inspiring.";
        }
    }
}
