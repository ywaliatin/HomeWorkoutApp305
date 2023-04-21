package com.example.homeworkout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class SquatActivity extends AppCompatActivity {

    private TextView SquatTextView;
    private ImageView SquatImageView;
    private TextView SquatExplanationTextView;
    private TextView SquatTimeRemainingLabelTextView;
    private EditText SquatTimeRemainingLabelEditText;
    private EditText workoutTimeEditText;
    private EditText restTimeEditText;
    private Button startButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.squat_activity);

        // Get the name entered by the user
        String name = getIntent().getStringExtra("name");
        Log.d("SquatActivity", "Name value is: " + name);

// Set the name as the text of the nameTextView
        //TextView nameTextView = findViewById(R.id.nameTextView);
        //nameTextView.setText("Hi " + name);

        // Set the name as the title of the activity
        setTitle("Hi " + name + "'s Home Workout");





        SquatTextView = findViewById(R.id.SquatTextView);
        SquatImageView = findViewById(R.id.SquatImageView);
        SquatExplanationTextView = findViewById(R.id.SquatExplanationTextView);
        SquatTimeRemainingLabelTextView = findViewById(R.id.SquatTimeRemainingLabelTextView);
        SquatTimeRemainingLabelEditText = findViewById(R.id.SquatTimeRemainingLabelEditText);
        workoutTimeEditText = findViewById(R.id.workoutTimeEditText);
        restTimeEditText = findViewById(R.id.SquatTimeRemainingLabelEditText);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getIntent().getStringExtra("name");
                if (name == null) {
                    // Display a message asking the user to go back to MainActivity and enter their name
                    Toast.makeText(SquatActivity.this, "Please go back and enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the workout time and rest time from the EditText fields
                String workoutTimeText = workoutTimeEditText.getText().toString();
                String restTimeText = restTimeEditText.getText().toString();

                if (workoutTimeText.isEmpty() || restTimeText.isEmpty()) {
                    // Display a message asking the user to fill in the workout and rest time
                    Toast.makeText(SquatActivity.this, "Please enter the workout and rest time", Toast.LENGTH_SHORT).show();
                    return;
                }

                int workoutTime = Integer.parseInt(workoutTimeText) * 60000; //convert to milliseconds
                int restTime = Integer.parseInt(restTimeText) * 1000; // convert to milliseconds

                Intent intent = new Intent(SquatActivity.this, Timer.class);
                intent.putExtra("name", name);
                intent.putExtra("workoutTime", workoutTime);
                intent.putExtra("restTime", restTime);
                startActivity(intent);
            }
        });


    }

    //private void startWorkout() {
    //  String workoutTimeText = workoutTimeEditText.getText().toString();
    //  String restTimeText = restTimeEditText.getText().toString();

    //if (workoutTimeText.isEmpty() || restTimeText.isEmpty()) {
    // Display a message asking the user to fill in the workout and rest time
    //  Toast.makeText(this, "Please enter the workout and rest time", Toast.LENGTH_SHORT).show();
    //return;
    //}

    //int workoutTime = Integer.parseInt(workoutTimeText) * 60000; //convert to milliseconds
    //int restTime = Integer.parseInt(restTimeText) * 1000; // convert to milliseconds


    //Intent intent = new Intent(this, Timer.class);
    //intent.putExtra("workoutTime", workoutTime);
    //intent.putExtra("restTime", restTime);
    //startActivity(intent);
    // }

    private void startWorkout(int workoutTime, int restTime) {
        String name = getIntent().getStringExtra("name");

        Intent intent = new Intent(SquatActivity.this, Timer.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));

        intent.putExtra("workoutTime", workoutTime);
        intent.putExtra("restTime", restTime);
        startActivity(intent);
    }










    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
