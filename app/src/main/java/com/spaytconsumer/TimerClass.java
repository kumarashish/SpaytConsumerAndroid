package com.spaytconsumer;

import android.app.Activity;
import android.app.Dialog;
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
import common.Common;
import intefaces.WebApiResponseCallback;
import models.LocationDetails;
import models.UserTimers;
import utils.Utils;

import static com.facebook.internal.Utility.logd;

/**
 * Created by ashish.kumar on 05-12-2018.
 */

public class TimerClass extends Activity implements View.OnClickListener , WebApiResponseCallback {
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
    Dialog dailog;
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
    public void updateTimerStatus()
    {
        dailog=Utils.showPogress(TimerClass.this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
        controller.getApiCall().postData(Common.getStartedTimerUrl,Common.afterTimerStartKeys,new String[]{"true","true","false",timers.getBusiness_location_id(),timers.getStart_time(),dateFormat.format(System.currentTimeMillis()),controller.getProfile().getUser_id(),"","",timers.getId()},TimerClass.this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stop_timer:
                updateTimerStatus();
                break;
        }
    }

    @Override
    public void onSucess(final String value) {

if(Utils.getStatus(value)==true)
{
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Intent in = new Intent(TimerClass.this,PaymentPage.class);
            PaymentPage.locationDetails = new LocationDetails(Utils.getLocationDetails(value));
            PaymentPage.timers = new UserTimers(Utils.getTimers(value));
            in.putExtra("pendingamount", Utils.getTotalParkingAmount(value));
            startActivity(in);
            finish();
        }
    });

}else{
    if(dailog!=null)
    {
        dailog.cancel();
    }
    Utils.showToast(TimerClass.this,Utils.getMessage(value));
}
    }

    @Override
    public void onError(String value) {
        if(dailog!=null)
        {
            dailog.cancel();
        }
        Utils.showToast(TimerClass.this,Utils.getMessage(value));
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


    /*
    NSDictionary *params = @ {@"IsTimerStarted" : _IsTimerStarted, @"IsTimerStoped" : _IsTimerStoped, @"IsPaymentDone" : _IsPaymentDone,
        @"business_location_id" : nearByObject->location_Id , @"start_time" : startTime, @"end_time" : _endTime, @"user_id" : [Global sharedInstance]->currentUserDetails->userID, @"amount" : _amount, @"parking_carplate_no" : vehicleNumberTF.text, @"timer_id" : timerIdString};
     */
}
