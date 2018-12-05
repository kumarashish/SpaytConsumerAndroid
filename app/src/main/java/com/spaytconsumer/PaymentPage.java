package com.spaytconsumer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import models.LocationDetails;
import models.UserTimers;

/**
 * Created by ashish.kumar on 05-12-2018.
 */

public class PaymentPage  extends Activity {
    AppController controller;
    public static LocationDetails locationDetails=null;
    public static UserTimers timers=null;
    @BindView(R.id.placeName)
    TextView placeName;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.startTime)
    TextView startTime;
    @BindView(R.id.endTime)
    TextView endTime;
    @BindView(R.id.totalamount)
    TextView amount;
String totalFees="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        totalFees=getIntent().getStringExtra("pendingamount");
        updateUI();
    }
    public void updateUI()
    {
        placeName.setText(locationDetails.getLocation_name());
        address.setText(locationDetails.getStreet_name()+","+locationDetails.getCity()+","+locationDetails.getDoor_no());
        startTime.setText(timers.getStart_time());
        endTime.setText(timers.getEnd_time());
        amount.setText("€"+totalFees);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"You are not allowed to exit from this page without making payment",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        timers=null;
        locationDetails=null;
        super.onDestroy();
    }
}