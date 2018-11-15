package com.spaytconsumer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.BatchUpdateException;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import intefaces.WebApiResponseCallback;
import models.ParkingModel;
import utils.Utils;

/**
 * Created by ashish.kumar on 13-11-2018.
 */

public class BookKnow extends Activity implements View.OnClickListener, WebApiResponseCallback {
    public static  ParkingModel model=null;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.placeName)
    TextView placeName;
    @BindView(R.id.carPlateNumber)
    EditText carPlateNumber;
    @BindView(R.id.minHour)
    TextView minHour;
    @BindView(R.id.maximumParkingAmount)
    TextView maximumParkingAmount;
    @BindView(R.id.perHourCharge)
    TextView perHourCharge;
    @BindView(R.id.booknow)
    Button booknow;
    @BindView(R.id.openingHours)
    LinearLayout openingHours;
    @BindView(R.id.openingHours_icon)
    ImageView dropdown;
    @BindView(R.id.openingHours_tv)
    TextView openingHourTv;
    AppController controller;
    Dialog dailog;
    int Apicall=0;
    int getCarPlateNumber=1,getPlaceDetails=2,bookNow=3;
    String []headings={"Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag","Sonntag"};
    boolean isViewExpanded=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booknow);
        controller=(AppController) getApplicationContext();
        ButterKnife.bind(this);
        booknow.setOnClickListener(this);
        back.setOnClickListener(this);
        openingHourTv.setOnClickListener(this);
        dropdown.setOnClickListener(this);
        initializeData();
        if(Utils.isNetworkAvailable(BookKnow.this))

        {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    final String response = controller.getApiCall().postFlormData(Common.getUSerCarPlate, controller.getProfile().getUser_id());
                    if (response.length() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                carPlateNumber.setText(Utils.getcarPlateNumber(response));
                            }
                        });
                    }
                }
            });
        t.start();

            Apicall=getPlaceDetails;
            dailog=Utils.showPogress(BookKnow.this);
            controller.getApiCall().postFlormData(Common.getlocationDetails,Common.idKey,model.getId(),BookKnow.this);
        }
    }

    private void initializeData() {
        placeName.setText(model.getLocation_name());
        address.setText(model.getStreet_name()+", "+model.getDoor_no());
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.booknow:
                Utils.showToast(BookKnow.this,"UnderDevelopment");
                    break;
            case R.id.openingHours_icon:
            case R.id.openingHours_tv:
                if(isViewExpanded)
                {isViewExpanded=false;
                    openingHours.setVisibility(View.GONE);
                }else{
                    isViewExpanded=true;
                    openingHours.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public void onSucess(String value) {

if(value.length()>60)
{
    model.setBusinessDetails(value);
    updateUI();
}else{
    Utils.showToast(BookKnow.this,"Business details not available");
}
        if(dailog!=null)
        {
            dailog.cancel();
        }
    }

    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                perHourCharge.setText(" € " + model.getParking_fees().getParking_fee_per_hour());
                maximumParkingAmount.setText(" € " + model.getParking_fees().getMaximum_parking_fees());
                minHour.setText(model.getParking_fees().getMinimum_parking_hours());
                for (int i = 0; i < 7; i++) {
                    View view = getLayoutInflater().inflate(R.layout.parking_hours, null, false);
                    TextView heading = (TextView) view.findViewById(R.id.heading);
                    TextView value = (TextView) view.findViewById(R.id.value);
                    heading.setText(headings[i]);
                    switch (i) {
                        case 0:
                            value.setText(model.getOpening_hours().getMondayTimming());
                            break;
                        case 1:
                            value.setText(model.getOpening_hours().getTuesdayTimming());
                            break;
                        case 2:
                            value.setText(model.getOpening_hours().getWednesdayTimming());
                            break;
                        case 3:
                            value.setText(model.getOpening_hours().getThursdayTimming());
                            break;
                        case 4:
                            value.setText(model.getOpening_hours().getFridayTimming());
                            break;
                        case 5:
                            value.setText(model.getOpening_hours().getSaturdayTimming());
                            break;
                        case 6:
                            value.setText(model.getOpening_hours().getSundayTimming());
                            break;
                    }
                    openingHours.addView(view);
                }
            }
        });
    }

    @Override
    public void onError(String value) {
        if(dailog!=null)
        {
            dailog.cancel();
        }
        Utils.showToast(BookKnow.this,Utils.getMessage(value));
    }
}
