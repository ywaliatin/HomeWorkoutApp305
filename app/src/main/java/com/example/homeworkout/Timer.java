package com.example.homeworkout;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;


public class Timer extends AppCompatActivity {

    private TextView timerTextView;
    private TextView restTimerTextView;
    private TextView yourRestTextView;
    private TextView workoutTextView;

    private Button startButton;
    private Button finishButton;
    private Button backtowheelButton;
    private CountDownTimer countDownTimer;
    private CountDownTimer restCountDownTimer;
    private boolean timerRunning = false;
    private boolean restTimerRunning = false;
    private boolean isPlaying = false;

    private MediaPlayer mediaPlayer;
    private int totalWorkouts = 0;
    private int totalWorkoutTime = 0;
    private int burpeesWorkoutTime =0;
    private int  pushupsWorkoutTime =0;
    private int workoutTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        SharedPreferences sharedPref = getSharedPreferences("my_prefs", MODE_PRIVATE);
        //String name = sharedPref.getString("user_name", "");

        String name = getIntent().getStringExtra("name");
        setTitle("Hi " + name + "'s Home Workout");
        Log.d("Timer", "Name value is: " + name);




        //String name = getIntent().getStringExtra("name");
        //setTitle(name + "'s Main Class");









        workoutTime = getIntent().getIntExtra("workoutTime", 0);

        workoutTextView = findViewById(R.id.workoutTextView);
        TextView workoutTextView = findViewById(R.id.workoutTextView);
        workoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the total number of workouts
                totalWorkouts++;

                // Determine which exercise was selected and update the corresponding workout time
                if (workoutTextView.getText().equals("Burpees")) {
                    burpeesWorkoutTime += workoutTime;
                } else if (workoutTextView.getText().equals("PushUps")) {
                    pushupsWorkoutTime += workoutTime;
                }

                // Update the total workout time
                totalWorkoutTime = burpeesWorkoutTime + pushupsWorkoutTime;

                onWorkoutClick(v);
            }
        });






        timerTextView = findViewById(R.id.timerTextView);
        restTimerTextView = findViewById(R.id.resttimerTextView);
        yourRestTextView = findViewById(R.id.yourRestTextView);
        startButton = findViewById(R.id.startButton);
        startButton.setText("Stop");

        final int workoutTime = getIntent().getIntExtra("workoutTime", 0);
        final int restTime = getIntent().getIntExtra("restTime", 0);

        startWorkout(workoutTime, restTime);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.start();
                    isPlaying = false;
                    startButton.setText("Start");
                } else {
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(Timer.this, R.raw.workout_music);
                        mediaPlayer.setLooping(true);
                    }
                    mediaPlayer.start();
                    isPlaying = true;
                    startButton.setText("Stop");
                    mediaPlayer.pause();
                }
            }
        });









        finishButton = findViewById(R.id.totalworkoutButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                finishAffinity();
            }
        });













    }



        private void startWorkout(final int workoutTime, final int restTime) {
            final int totalTime = workoutTime + restTime;

            // Retrieve the total workout time and the previous user from SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences("my_prefs", MODE_PRIVATE);
            totalWorkoutTime = sharedPref.getInt("totalWorkoutTime", 0);
            String previousUser = sharedPref.getString("previousUser", "");

            // Retrieve the current user
            String currentUser = getIntent().getStringExtra("name");

            // If the previous user is not the same as the current user, reset the total workout time to 0
            if (!previousUser.equals(currentUser)) {
                totalWorkoutTime = 0;
            }

            // Update the total workout time with the current workout and exercise times
            totalWorkoutTime += workoutTime + burpeesWorkoutTime + pushupsWorkoutTime;

            // Store the updated total workout time and the current user in SharedPreferences
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("totalWorkoutTime", totalWorkoutTime);
            editor.putString("previousUser", currentUser);
            editor.apply();




            // Update the NameTextView with the updated total workout time
            TextView nameTextView = findViewById(R.id.nameTextView);
            nameTextView.setText("Total workout time: " + formatTime(totalWorkoutTime));
            if (mediaPlayer == null) { // check if the mediaPlayer is not already playing
            mediaPlayer = MediaPlayer.create(this, R.raw.workout_music); // replace R.raw.workout_music with your audio file name
            mediaPlayer.setLooping(true);
            mediaPlayer.start();


        }

        countDownTimer = new CountDownTimer(totalTime, 1000) {
            public void onTick(long millisUntilFinished) {
                long workoutMillisLeft = Math.max(millisUntilFinished - restTime, 0);
                timerTextView.setText(formatTime(workoutMillisLeft));

                // Pause music playback when there are 10 seconds left in the workout time
                if (workoutMillisLeft <= 10000 && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }

                if (millisUntilFinished <= restTime && !restTimerRunning) {
                    startRest(restTime);
                    restTimerRunning = true;
                    mediaPlayer.pause();
                }
            }

            public void onFinish() {
                timerTextView.setText("00:00:00");
                restTimerTextView.setText(formatTime(restTime));
                yourRestTextView.setText("Rest time!");
                restTimerRunning = true;
                mediaPlayer.pause();
            }
        }.start();
    }

    private void stopWorkout() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) { // check if the mediaPlayer is playing before stopping it
            mediaPlayer.pause();
        }
        // TODO: Display a message that the workout has been stopped
    }




    private void startRest(final int restTime) {
        restCountDownTimer = new CountDownTimer(restTime, 1000) {
            public void onTick(long millisUntilFinished) {
                restTimerTextView.setText(formatTime(millisUntilFinished));
                if (millisUntilFinished <= 10000) { //when rest timer less or equal than 10 secs
                    yourRestTextView.setText("Get ready for next set!");
                    yourRestTextView.setTextColor(getResources().getColor(R.color.red));
                } else {
                    long secondsRemaining = millisUntilFinished / 1000;
                    long minutes = secondsRemaining / 60;
                    long seconds = secondsRemaining % 60;
                    String timeRemaining = String.format("%02d:%02d", minutes, seconds);
                    //yourRestTextView.setText("Rest Time: " + timeRemaining);
                    yourRestTextView.setText("Time for stretching " );
                    yourRestTextView.setTextColor(getResources().getColor(R.color.black));
                }

            }


            public void onFinish() {
                timerTextView.setText("00:00:00");
                restTimerTextView.setText(formatTime(restTime));
                yourRestTextView.setText("Rest time!");
                restTimerRunning = true;
                mediaPlayer.pause();
                mediaPlayer.release();

            }

        }.start();
    }


    public void onWorkoutClick(View view) {
        // Increment the total number of workouts
        totalWorkouts++;

        // Determine which exercise was selected and update the corresponding workout time
        if (workoutTextView.getText().equals("Burpees")) {
            burpeesWorkoutTime += workoutTime;
        } else if (workoutTextView.getText().equals("PushUps")) {
            pushupsWorkoutTime += workoutTime;
        }

        // Update the total workout time
        totalWorkoutTime = burpeesWorkoutTime + pushupsWorkoutTime;

        Intent intent = new Intent(this, BurpeesActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }


    public void PushUpOnClick(View view) {
        Intent intent = new Intent(this, PushUpActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }

    public void SitUpOnClick(View view) {
        Intent intent = new Intent(this, SitUpActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }

    public void JumpingJackOnClick(View view) {
        Intent intent = new Intent(this, JumpingJackActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
    }








    //private void stopWorkout() {
      //  if (countDownTimer != null) {
        //    countDownTimer.cancel();
          //  countDownTimer = null;
        //}
        // TODO: Display a message that the workout has been stopped
    //}



    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

