package com.spaytconsumer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import models.UserTimers;

/**
 * Created by ashish.kumar on 05-12-2018.
 */

public class TimerClass extends Activity implements View.OnClickListener {
    AppController controller;
    private final int interval = 1000; // 1 Second
    int duration=0;
    public static UserTimers timers=null;
    private Handler handler = new Handler();
    @BindView(R.id.timervalue)
    TextView timer;
    @BindView(R.id.stop_timer)
    Button stopTimer;
    int hour=0;
    int minute=0;
    int second=0;
    Timer t;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);
        controller=(AppController) getApplicationContext();
        ButterKnife.bind(this);
        MyTimerTask yourTask = new MyTimerTask();
        t = new Timer();
        t.scheduleAtFixedRate(yourTask, 0, 1000);
        stopTimer.setOnClickListener(this);
    }

    public void setTime() {
        second++;
        if (second == 60) {
            second = 0;
            minute++;
        }
        if (minute == 60) {
            minute = 0;
            hour++;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String sec, min, hou;
                if (second < 10) {
                    sec = "0" + second;
                } else {
                    sec = Integer.toString(second);
                }
                if (minute < 10) {
                    min = "0" + minute;
                } else {
                    min = Integer.toString(minute);
                }
                if (hour < 10) {
                    hou = "0" + hour;
                } else {
                    hou = Integer.toString(hour);
                }
                timer.setText(hou + ":" + min + ":" + sec);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stop_timer:
                startActivity(new Intent(this, PaymentPage.class));
                finish();
                break;
        }
    }


    class MyTimerTask extends TimerTask {
        public void run() {
            setTime();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"You are not allowed to exit from this page without stoping timer",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        timers=null;
        super.onDestroy();
    }
}
