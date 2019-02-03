package com.spaytconsumer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wallet.WalletConstants;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;

import intefaces.WebApiResponseCallback;
import models.LocationDetails;
import models.OrderDetailsModel;
import models.ParkingModel;
import models.UserTimers;
import prelogin.Login;
import utils.Utils;

/**
 * Created by ashish.kumar on 05-12-2018.
 */

public class PaymentPage  extends Activity implements View.OnClickListener , WebApiResponseCallback {
    AppController controller;
    public static LocationDetails locationDetails=null;
    public static UserTimers timers=null;
    public static OrderDetailsModel orderDetails=null;
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
    private static final String CONFIG_CLIENT_ID = Common.paypalClientId;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final int REQUEST_CODE_PAYMENT = 1;
    //private static BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX, "cdfnmm4xdrs95nsh", "h8522t5v68rttkf6", "54906a99c3d32c0a927688fc0eae1e21"
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("SpaytConsumer")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.spayt.de"))
            .merchantUserAgreementUri(Uri.parse("https://www.spayt.de"));
    private static final String TAG = "paymentExample";

    int apiCall=0;
    final int updateTimer=1,updatePaymentStatus=2,getLocationsDetails=3;
    Dialog dailog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        totalFees=getIntent().getStringExtra("pendingamount");
        updateUI();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        paynow.setOnClickListener(this);
        if(locationDetails!=null)
        {       apiCall=getLocationsDetails;
                dailog=Utils.showPogress(PaymentPage.this);
                controller.getApiCall().postFlormData(Common.getlocationDetails,Common.idKey,timers.getBusiness_location_id(),PaymentPage.this);
        }
    }
    public void updateUI()
    {runOnUiThread(new Runnable() {
        @Override
        public void run() {
            if(locationDetails!=null) {
                placeName.setText(locationDetails.getLocation_name());
                address.setText(locationDetails.getStreet_name() + "," + locationDetails.getCity() + "," + locationDetails.getDoor_no());
            }
            startTime.setText(timers.getStart_time());
            endTime.setText(timers.getEnd_time());
            amount.setText("€"+totalFees);
        }
    });

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                      Toast.makeText(PaymentPage.this,"PaymentConfirmation info received from PayPal",Toast.LENGTH_SHORT).show();
                        updatePaymentDetails(Utils.getPaymentId(confirm.toJSONObject().toString(4)));

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
    public void onBuyPressed(View pressed) {
        /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(PaymentPage.this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(totalFees), "EUR", "Parking Fees",
                paymentIntent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.pay_now:

                 onBuyPressed(v);
            break;
        }
    }


    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                {
                    switch (apiCall) {

                        case updatePaymentStatus:
                            if (Utils.getMessage(value).equalsIgnoreCase("Success")) {
                                dailog.cancel();
                                updatePaymentStatus();
                            }else{
                                dailog.cancel();
                                Toast.makeText(PaymentPage.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case updateTimer:
                            if(Utils.getStatus(value)) {
                                Toast.makeText(PaymentPage.this, "Payment staus updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PaymentPage.this, Sucess.class);
                                startActivity(intent);
                                finish();

                            }else{
                                dailog.cancel();
                                Toast.makeText(PaymentPage.this,Utils.getMessage(value),Toast.LENGTH_SHORT).show();

                            }
                            break;

                        case getLocationsDetails:
                            try {
                                JSONObject jsonObject = new JSONObject(value);
                                JSONArray locationDetailss = jsonObject.getJSONArray("Location Details");
                                locationDetails=new LocationDetails(locationDetailss.getJSONObject(0));
                                updateUI();
                                } catch (Exception ex) {
                                ex.fillInStackTrace();
                            }
                            if(dailog!=null)
                            {
                                dailog.cancel();
                            }


                            break;
                    }


                }
                }

        });


    }

    @Override
    public void onError(String value) {
        dailog.cancel();
       Utils.showToast(PaymentPage.this,Utils.getMessage(value));

    }


    public void updatePaymentDetails(String transaction)
    {
        dailog=Utils.showPogress(PaymentPage.this);
        apiCall=updatePaymentStatus;
        controller.getApiCall().postData(Common.getUpdateOrder,Common.updatePaymentKeys,new String[]{timers.getBusiness_location_id(),timers.getOrder_id(),transaction,totalFees,controller.getProfile().getUser_id(),Utils.getParkingDuration(timers.getStart_time(),timers.getEnd_time()),"Paid","up76u0939"},PaymentPage.this);


    }
    public void updatePaymentStatus()
    {
        dailog=Utils.showPogress(PaymentPage.this);
        apiCall=updateTimer;
        controller.getApiCall().postData(Common.getStartedTimerUrl,Common.afterTimerStartKeys,new String[]{"true","true","true",timers.getBusiness_location_id(),timers.getStart_time(),timers.getEnd_time(),controller.getProfile().getUser_id(),totalFees,"",timers.getId()},PaymentPage.this);


    }
}
