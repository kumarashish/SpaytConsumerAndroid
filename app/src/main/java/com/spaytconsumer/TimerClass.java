package com.spaytconsumer;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import models.UserTimers;

import static com.facebook.internal.Utility.logd;

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
    int day=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_view);

        controller=(AppController) getApplicationContext();

        ButterKnife.bind(this);
        if(timers!=null) {
            initializeTimer();
        }

//        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) &&(Build.VERSION.SDK_INT <26)) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//        }else if(Build.VERSION.SDK_INT >=26){
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) stopTimer.getLayoutParams();
//            params.bottomMargin=120;
//        }
        MyTimerTask yourTask = new MyTimerTask();
        t = new Timer();
        t.scheduleAtFixedRate(yourTask, 0, 1000);
        stopTimer.setOnClickListener(this);
    }

    public void initializeTimer()

    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
        try {

            Date date1=dateFormat.parse(timers.getStart_time());
            long miliseconds=date1.getTime();
            Date date2= Calendar.getInstance().getTime();
            long miliseconds2=date2.getTime();
            long  difference=(miliseconds2-miliseconds)/1000;
            hour  = (int) (difference )/3600;
            long val=difference%3600;
            minute=(int)val/60;
            second=(int)val%60;

            if(hour>24)
            {
                day=(int )hour/24;
                hour=hour%24;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        if(hour==24)
        {
            hour=0;
            day++;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String sec, min, hou,da;
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
                if(day>0)
                {
                    timer.setText(day+" day : "+hou + " : " + min + " : " + sec);
                }else {


                    timer.setText(hou + ":" + min + ":" + sec);
                }
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
