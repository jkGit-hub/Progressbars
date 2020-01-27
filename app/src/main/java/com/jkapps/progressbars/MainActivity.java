package com.jkapps.progressbars;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

//2019-12-07

public class MainActivity extends AppCompatActivity {

    TextView tv_countDownTimer, tv_progressPercent;
    ProgressBar progressBar, circularProgressBar;
    Button btn_startStop;

    public CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private String mTime;
    private long mTimeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_countDownTimer = findViewById(R.id.tv_countDownTimer);
        progressBar = findViewById(R.id.progressBar);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        tv_progressPercent = findViewById(R.id.tv_progressPercent);
        btn_startStop = findViewById(R.id.btn_startStop);

        btn_startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTime = tv_countDownTimer.getText().toString();
                mTimeLeftInMillis = Long.parseLong(mTime) * 60000;

                if (mTimerRunning) {
                    stopTimer(); //app dies when Stop button pressed
                } else {
                    startTimer();
                }
            }
        });
    }

    private void stopTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateCountDownText();
        updateUI();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                startProgressBar();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                tv_countDownTimer.setVisibility(TextView.VISIBLE);
                tv_countDownTimer.setText("Great Job!");
                btn_startStop.setText("RESTART");
            }
        }.start();

        mTimerRunning = true;
        updateUI();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        }
        tv_countDownTimer.setText(timeLeftFormatted);
    }

    private void startProgressBar() {
        int mTimeSec = Integer.parseInt(mTime) * 60;
        int timeLeftSec = (int) (long) (mTimeLeftInMillis / 1000);
        float timePastSec = (float)(mTimeSec - timeLeftSec) / mTimeSec;
        int pp = (int)(float)((timePastSec) * 100);

        progressBar.setProgress(pp);
        circularProgressBar.setProgress(pp);
        tv_progressPercent.setText(pp + "%");
    }

    private void updateUI() {
        if (mTimerRunning) {
            tv_countDownTimer.setVisibility(TextView.VISIBLE);
            btn_startStop.setText("STOP");
        } else {
            tv_countDownTimer.setVisibility(TextView.INVISIBLE);
            btn_startStop.setText("START");
        }
    }
}