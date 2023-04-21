package com.example.homeworkout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TotalActivity extends AppCompatActivity {

    private TextView workoutNameTextView;
    private TextView workoutTimeTextView;
    private TextView workout2NameTextView;
    private TextView workout2TimeTextView;
    private Button backButton;
    private Button totalButton;
    private String workoutName;
    private int totalTime;

    private String workout2Name;
    private int total2Time;
    private int secondSetsTime;
    private CountDownTimer secondSetsCountDownTimer;
    private int totalWorkoutSets = 0;
    private int totalTimeWorkout = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_activity);

        workoutNameTextView = findViewById(R.id.workoutNameTextView);
        workout2NameTextView = findViewById(R.id.workout2NameTextView);
        workoutTimeTextView = findViewById(R.id.workoutTimeTextView);
        workout2TimeTextView = findViewById(R.id.workout2TimeTextView);
        backButton = findViewById(R.id.BackButton);
        totalButton = findViewById(R.id.TotalButton);

        //String workoutName = getIntent().getStringExtra("workoutName");


        workoutName = getIntent().getStringExtra("workoutName");
        totalTime = getIntent().getIntExtra("totalTime", 0);

        workout2Name = getIntent().getStringExtra("workoutName");
        total2Time = getIntent().getIntExtra("totalTime", 0);

        workoutNameTextView.setText(workoutName + " Workout");
        workoutTimeTextView.setText(String.format("%s %s", getClassName(), formatTime(totalTime)));

        //workout2NameTextView.setText(workoutName + " Workout");
        //workout2TimeTextView.setText(String.format("%s %s", getClassName(), formatTime(totalTime)));

        totalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalWorkoutSets++;
                totalTimeWorkout += totalTime;
                Intent intent = new Intent(getApplicationContext(), TotalActivity.class);
                intent.putExtra("activityName", "PushUpActivity");
                intent.putExtra("workoutName", "Push-ups");
                intent.putExtra("totalTime", totalTime);
                startActivity(intent);
                Toast.makeText(TotalActivity.this, getClassName() + " - Total button clicked. Total workout sets: " + totalWorkoutSets + ". Total time: " + formatTime(totalTimeWorkout), Toast.LENGTH_SHORT).show();
            }
        });

        totalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalWorkoutSets++;
                totalTimeWorkout += totalTime;
                Intent intent = new Intent(getApplicationContext(), TotalActivity.class);
                intent.putExtra("activityName", "BurpeesActivity");
                intent.putExtra("workoutName", "Burpees");
                intent.putExtra("totalTime", totalTime);
                startActivity(intent);
                Toast.makeText(TotalActivity.this, getClassName() + " - Total button clicked. Total workout sets: " + totalWorkoutSets + ". Total time: " + formatTime(totalTimeWorkout), Toast.LENGTH_SHORT).show();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Start the second sets timer after the first one finishes
        secondSetsCountDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                secondSetsTime = (int) (60000 - millisUntilFinished);
                workout2TimeTextView.setText("Second sets: " + formatTime(secondSetsTime));
            }

            public void onFinish() {
                workout2TimeTextView.setText("Second sets: " + formatTime(60000));
            }
        };
        secondSetsCountDownTimer.start();
    }

    private String formatTime(int millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String getClassName() {
        String className = "";
        String activityName = getIntent().getStringExtra("activityName");
        if (activityName != null && !activityName.isEmpty()) {
            className = activityName;
        } else {
            // Default to JumpingJack Activity
            if (workout2Name.equals("Push-ups")) {
                className = "PushUpActivity";
            } else if (workout2Name.equals("Sit-ups")) {
                className = "SitUpActivity";
            } else if (workout2Name.equals("Squat")) {
                className = "SquatActivity";
            } else if (workout2Name.equals("Burpees")) {
                className = "BurpeesActivity";
            } else {
                className = "JumpingJackActivity";
            }
        }
        return className;
    }





}
