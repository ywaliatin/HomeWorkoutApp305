package com.example.homeworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;

import android.animation.ObjectAnimator;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class MainActivity extends AppCompatActivity {

    private ImageView wheelImageView;
    private ImageView needleImageView;
    private TextView workoutTextView;
    private Button spinButton;
    private TextView EnterNameTextView;
    private EditText EnterNameEditText;
    int totalTime = 0;
    private ProgressBar progressBar;
    private int progress = 0; // declare the progress variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the SharedPreferences object
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

// Retrieve the user's name from SharedPreferences
        String name = sharedPreferences.getString("userName", "");

// Use the name to customize the UI, etc.

        // Set the title of the activity to the user's name
        //String name = getIntent().getStringExtra("name");
        if (name != null && !name.isEmpty()) {
            setTitle("Welcome, " + name);
        }


        //String userName = UserSession.userName;


        // Retrieve the user's name from SharedPreferences
        //SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //String name = preferences.getString("userName", "");
        //if (!name.isEmpty()) {
            // Display the user's name in a TextView or any other UI element
        //}

        // Get references to the UI elements
        wheelImageView = findViewById(R.id.wheelImageView);
        needleImageView = findViewById(R.id.needleImageView);
        workoutTextView = findViewById(R.id.workoutTextView);
        spinButton = findViewById(R.id.spinButton);
        EnterNameTextView = findViewById(R.id.EnterNameTextView);
        EnterNameEditText = findViewById(R.id.EnterNameEditText);









        // Set the initial rotation angle of the wheel image view
        wheelImageView.setRotation(18);

        // Set the needle image view to invisible
        needleImageView.setVisibility(View.INVISIBLE);

        // Set a click listener for the spin button
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the name entered by the user
                String name = EnterNameEditText.getText().toString().trim();
                Log.d("Timer", "Name value is: " + name);

                if (name.isEmpty()) {
                    // Display a message to the user asking them to enter their name
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                // Generate a random number between 1 and 5
                Random random = new Random();
                int randomNumber = random.nextInt(5) + 1;

                // Set the rotation angle of the wheel image view
                float wheelRotation = (float) ((randomNumber - 1) * 60 + 60);
                wheelImageView.setRotation(wheelRotation);

                // Set the rotation angle of the needle image view
                float needleRotation = (float) ((randomNumber - 1) * 60 + 28);
                needleImageView.setRotation(needleRotation);

                // Make the needle image view invisible
                needleImageView.setVisibility(View.GONE);

                // Disable the spin button while the wheel is spinning
                spinButton.setEnabled(false);




                    // Start an animation to spin the wheel
                ObjectAnimator animator = ObjectAnimator.ofFloat(wheelImageView, "rotation", wheelRotation, wheelRotation + 360);
                animator.setDuration(2000);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Enable the spin button when the animation is finished
                        spinButton.setEnabled(true);





                        // Display the workout option based on the selected number
                        switch (randomNumber) {
                            case 1:
                                workoutTextView.setText("Push-up");
                                totalTime = 30000; // Set the total time for push-ups
                                Intent pushUpIntent = new Intent(MainActivity.this, PushUpActivity.class);
                                pushUpIntent.putExtra("workoutName", "Push-ups");
                                pushUpIntent.putExtra("workoutImageId", R.drawable.pushup);
                                pushUpIntent.putExtra("workoutExplanation", "Get into a plank position with your hands slightly wider than shoulder-width apart. Lower your body until your chest nearly touches the floor, then push back up to the starting position. Repeat.");
                                pushUpIntent.putExtra("name", name); // add the name as an extra
                                startActivity(pushUpIntent);
                                break;
                            case 2:
                                workoutTextView.setText("Sit-up");
                                Intent sitUpIntent = new Intent(MainActivity.this, SitUpActivity.class);
                                sitUpIntent.putExtra("workoutName", "Sit-ups");
                                sitUpIntent.putExtra("workoutImageId", R.drawable.situp);
                                sitUpIntent.putExtra("workoutExplanation", "Lie on your back with your knees bent and feet flat on the ground. Place your hands behind your head, and lift your upper body off the ground. Lower yourself back down and repeat.");
                                sitUpIntent.putExtra("name", name); // add the name as an extra
                                startActivity(sitUpIntent);
                                break;
                            case 3:
                                workoutTextView.setText("Squat");
                                Intent squatIntent = new Intent(MainActivity.this, SquatActivity.class);
                                squatIntent.putExtra("workoutName", "Squat");
                                squatIntent.putExtra("workoutImageId", R.drawable.squat);
                                squatIntent.putExtra("workoutExplanation", "Lower your body by bending your knees and hips, and then stand back up. To perform a squat, stand with your feet shoulder-width apart, and slowly lower your body by bending your knees and pushing your hips back. Keep your chest up and your weight on your heels. Lower your body until your thighs are parallel to the ground or as low as you can comfortably go. Then, push through your heels and stand back up to the starting position. Repeat.");
                                squatIntent.putExtra("name", name); // add the name as an extra
                                startActivity(squatIntent);
                                break;
                            case 4:
                                workoutTextView.setText("Burpees");
                                Intent burpeesIntent = new Intent(MainActivity.this, BurpeesActivity.class);
                                burpeesIntent.putExtra("workoutName", "Burpees");
                                burpeesIntent.putExtra("workoutImageId", R.drawable.burpees);
                                burpeesIntent.putExtra("workoutExplanation", ",Starting in a standing position, then squatting down and placing your hands on the ground. Kick your feet back to a push-up position, perform a push-up, then jump your feet back to your hands and stand up. Repeat.");
                                burpeesIntent.putExtra("name", name); // add the name as an extra

                                startActivity(burpeesIntent);
                                break;



                            case 5:
                                workoutTextView.setText("Jumping Jack");
                                Intent jumpingjackIntent = new Intent(MainActivity.this, JumpingJackActivity.class);
                                jumpingjackIntent.putExtra("workoutName", "Jumping-Jack");
                                jumpingjackIntent.putExtra("workoutImageId", R.drawable.jumpingjack);
                                jumpingjackIntent.putExtra("workoutExplanation", ",Start with your feet together and your arms by your sides, jump up and spread your feet out to the sides while raising your arms overhead, and jump back to the starting position. Repeat.");
                                jumpingjackIntent.putExtra("name", name); // add the name as an extra
                                startActivity(jumpingjackIntent);
                                break;
                            case 6:
                                workoutTextView.setText("Plank");
                                Intent plankIntent = new Intent(MainActivity.this, PlankActivity.class);
                                plankIntent.putExtra("workoutName", "Plank");
                                plankIntent.putExtra("workoutImageId", R.drawable.plank);
                                plankIntent.putExtra("workoutExplanation", ",Get into a push-up position with your forearms flat on the ground, hold your body in a straight line from head to heels, engage your core muscles, hold for a specified time, and lower your body back down and rest. Repeat.");
                                plankIntent.putExtra("name", name); // add the name as an extra
                                startActivity(plankIntent);
                                break;
                        }


                        // Make the needle image view visible
                        //needleImageView.setVisibility(View.VISIBLE);
                        needleImageView.setVisibility(View.INVISIBLE);

                    }
                });
                animator.start();
            }}
        });

    }



    private int calculateProgress() {
        int total = getIntent().getIntExtra("total", 0);
        int current = getIntent().getIntExtra("current", 0);

        int progress = (int) ((float) current / (float) total * 100.0);
        progressBar.setProgress(progress); // set the progress on the progress bar
        return progress;
    }


    private void updateProgress() {
        // Do some work here
        progress = calculateProgress();
        progressBar.setProgress(progress);
    }

}
