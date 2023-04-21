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

public class BurpeesActivity extends AppCompatActivity {

    private TextView BurpeesTextView;
    private ImageView BurpeesImageView;
    private TextView BurpeesExplanationTextView;
    private TextView BurpeesTimeRemainingLabelTextView;
    private EditText BurpeesTimeRemainingLabelEditText;
    private EditText workoutTimeEditText;
    private EditText restTimeEditText;
    private Button startButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.burpees_activity);





        // Get the name entered by the user
        String name = getIntent().getStringExtra("name");
        setTitle(name + "'s Burpees Class");
        Log.d("BurpeesActivity", "Name value is: " + name);

// Set the name as the text of the nameTextView
        //TextView nameTextView = findViewById(R.id.nameTextView);
        //nameTextView.setText("Hi " + name);

        // Set the name as the title of the activity
        setTitle("Hi " + name + "'s Home Workout");





        BurpeesTextView = findViewById(R.id.BurpeesTextView);
        BurpeesImageView = findViewById(R.id.BurpeesImageView);
        BurpeesExplanationTextView = findViewById(R.id.BurpeesExplanationTextView);
        BurpeesTimeRemainingLabelTextView = findViewById(R.id.BurpeesTimeRemainingLabelTextView);
        BurpeesTimeRemainingLabelEditText = findViewById(R.id.BurpeesTimeRemainingLabelEditText);
        workoutTimeEditText = findViewById(R.id.workoutTimeEditText);
        restTimeEditText = findViewById(R.id.BurpeesTimeRemainingLabelEditText);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getIntent().getStringExtra("name");
                if (name == null) {
                    // Display a message asking the user to go back to MainActivity and enter their name
                    Toast.makeText(BurpeesActivity.this, "Please go back and enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the workout time and rest time from the EditText fields
                String workoutTimeText = workoutTimeEditText.getText().toString();
                String restTimeText = restTimeEditText.getText().toString();

                if (workoutTimeText.isEmpty() || restTimeText.isEmpty()) {
                    // Display a message asking the user to fill in the workout and rest time
                    Toast.makeText(BurpeesActivity.this, "Please enter the workout and rest time", Toast.LENGTH_SHORT).show();
                    return;
                }

                int workoutTime = Integer.parseInt(workoutTimeText) * 60000; //convert to milliseconds
                int restTime = Integer.parseInt(restTimeText) * 1000; // convert to milliseconds

                //Intent intent = new Intent(BurpeesActivity.this, Timer.class);
                //intent.putExtra("name", name);
                //intent.putExtra("workoutTime", workoutTime);
                //intent.putExtra("restTime", restTime);
                //startActivity(intent);

                Intent intent = new Intent(BurpeesActivity.this, Timer.class);
                intent.putExtra("workoutName", "Burpees");
                intent.putExtra("workoutTime", workoutTime);
                intent.putExtra("restTime", restTime);
                intent.putExtra("name", name);
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

        Intent intent = new Intent(BurpeesActivity.this, Timer.class);
        intent.putExtra("name", name);
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
