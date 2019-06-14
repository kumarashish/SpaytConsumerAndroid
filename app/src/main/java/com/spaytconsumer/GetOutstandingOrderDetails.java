package com.spaytconsumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import intefaces.WebApiResponseCallback;
import models.OutstandingOrderModel;
import utils.Utils;

public class GetOutstandingOrderDetails extends Activity implements View.OnClickListener , WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.content)
    LinearLayout content;
    AppController controller;
    @BindView(R.id.grand_total)
    TextView grandTotal;
    @BindView(R.id.pay)
    Button submit;
    @BindView(R.id.customer_name)
    TextView customer_name;
    Dialog pd;
    int apiCall;
    String orderId="";
    String totalPayableAmout="";
    @BindView(R.id.progressbar2)
    ProgressBar progressBar;
    int getOrderDetails=1;
    Dialog dailog;
    private static final String CONFIG_CLIENT_ID = Common.paypalClientIdSanbox;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("SpaytConsumer")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.spayt.de"))
            .merchantUserAgreementUri(Uri.parse("https://www.spayt.de"));
    private static final String TAG = "paymentExample";
    OutstandingOrderModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cart);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
       // orderId=getIntent().getStringExtra("orderId");
        orderId="11";
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        if(Utils.isNetworkAvailable(GetOutstandingOrderDetails.this))
        {
            dailog=Utils.showPogress(GetOutstandingOrderDetails.this);
            apiCall=getOrderDetails;
            controller.getApiCall().postData(Common.getOutstandingOrderDetails,controller.getPrefManager().getUserToken(),new String[]{Common.idKey},new String[]{orderId},GetOutstandingOrderDetails.this);
        }



    }

    public void setValue()
    {
       double amount=0.00;
       for (int i=0;i<model.getOrderData().size();i++)
        {
            final OutstandingOrderModel.OrderData modell=model.getOrderData().get(i);
            View row = getLayoutInflater().inflate(R.layout.my_cart_row, null);

            TextView date=(TextView)row.findViewById(R.id.date) ;
            TextView productName=(TextView)row.findViewById(R.id.productName) ;
            final TextView total_price=(TextView)row.findViewById(R.id.total_price);
            final EditText quantity=(EditText) row.findViewById(R.id.quantity) ;
            final EditText price=(EditText) row.findViewById(R.id.price) ;

            date.setText( modell.getUpdatedOn());
            productName.setText(modell.getBusinessuserFullname());
            quantity.setText("0");
            price.setText(modell.getNetAmount());
            amount+=Double.parseDouble(modell.getNetAmount());
            //Double priceValue=Double.parseDouble(modell.getPrice())*(modell.getQuantity());
            total_price.setText(modell.getNetAmount()+" £");


         price.setEnabled(false);
           total_price.setFocusable(false);
            price.setFocusable(false);
            total_price.setEnabled(false);

            content.addView(row);
        }
        totalPayableAmout=Double.toString(amount);
        grandTotal.setText(Double.toString( amount)+" £");
        if(dailog!=null)
        {dailog.cancel();}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.pay:
            {
                if(Utils.isNetworkAvailable(GetOutstandingOrderDetails.this))
                {
                    onBuyPressed(v);
                }
                    }
                break;


        }
    }


    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(pd!=null)
                {
                    pd.cancel();
                }
                if(Utils.getStatus(value)==true)
                {
                    switch (apiCall)
                    {
                        case 1:
                            model=new Gson().fromJson(value, OutstandingOrderModel.class);
                            if(model.getCode()==200)
                            {
                                setValue();
                            }
                            break;
                    }
                }else{
                    Utils.showToast(GetOutstandingOrderDetails.this,Utils.getMessage(value));
                }
            }
        });


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
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE,totalPayableAmout,"items");

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(GetOutstandingOrderDetails.this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent,String fees,String items) {
        return new PayPalPayment(new BigDecimal(fees), "EUR", items,
                paymentIntent);
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
                        Toast.makeText(GetOutstandingOrderDetails.this,"PaymentConfirmation info received from PayPal",Toast.LENGTH_SHORT).show();
                        //updatePaymentDetails(Utils.getPaymentId(confirm.toJSONObject().toString(4)));

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
    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(GetOutstandingOrderDetails.this,Utils.getMessage(value));
                if(pd!=null)
                {
                    pd.cancel();
                }
            }
        });
    }

    }