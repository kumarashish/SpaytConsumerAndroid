package com.spaytconsumer;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.wallet.WalletConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;

import models.LocationDetails;
import models.UserTimers;

/**
 * Created by ashish.kumar on 05-12-2018.
 */

public class PaymentPage  extends Activity{
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
    @BindView(R.id.pay_now)
    Button paynow;
String totalFees="";
int  REQUEST_CODE=33;
    //private static BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX, "cdfnmm4xdrs95nsh", "h8522t5v68rttkf6", "54906a99c3d32c0a927688fc0eae1e21"
   
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
//        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) &&(Build.VERSION.SDK_INT <26)) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//        }else if(Build.VERSION.SDK_INT >=26){
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) paynow.getLayoutParams();
//            params.bottomMargin=120;
//        }
        totalFees=getIntent().getStringExtra("pendingamount");
        updateUI();
       // intialize();
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

//    public void intialize() {
//        DropInRequest dropInRequest = new DropInRequest()
//                .clientToken("sandbox_vqbwrqs6_cdfnmm4xdrs95nsh")
//               .amount("1.00")
//                .requestThreeDSecureVerification(true);
//        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
//
//
//
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                // use the result to update your UI and send the payment method nonce to your server
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // the user canceled
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//                error.fillInStackTrace();
//            }
//        }
//    }
//    private void enableGooglePay(DropInRequest dropInRequest) {
//        GooglePaymentRequest googlePaymentRequest = new GooglePaymentRequest()
//                .transactionInfo(TransactionInfo.newBuilder()
//                        .setTotalPrice("1.00")
//                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
//                        .setCurrencyCode("USD")
//                        .build())
//                .billingAddressRequired(true); // We recommend collecting and passing billing address information with all Google Pay transactions as a best practice.
//
//        dropInRequest.googlePaymentRequest(googlePaymentRequest);
//    }
}
