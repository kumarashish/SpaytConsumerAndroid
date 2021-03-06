package com.spaytconsumer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import org.json.JSONException;

import java.math.BigDecimal;

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
    int getOrderDetails=1,updatePaymentStatus=2;
    Dialog dailog;
    private static final String CONFIG_CLIENT_ID = Common.paypalClientIdLive;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
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
       orderId=getIntent().getStringExtra("orderId");
        //orderId="12";
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        if(orderId.length()>0) {
            if (Utils.isNetworkAvailable(GetOutstandingOrderDetails.this)) {
                dailog = Utils.showPogress(GetOutstandingOrderDetails.this);
                apiCall = getOrderDetails;
                controller.getApiCall().postData(Common.getOutstandingOrderDetails, controller.getPrefManager().getUserToken(), new String[]{Common.idKey}, new String[]{orderId}, GetOutstandingOrderDetails.this);
            }
        }else {
            Toast.makeText(GetOutstandingOrderDetails.this,"Order Id Missing",Toast.LENGTH_SHORT).show();
        }


    }

    public void setValue()
    {
       double amount=0.00;

        {
            if (model.getOrderDetailsData().size() > 0) {
                for (int i = 0; i < model.getOrderDetailsData().size(); i++) {
                    OutstandingOrderModel.OrderDetailsData modell = model.getOrderDetailsData().get(i);
                    View row = getLayoutInflater().inflate(R.layout.my_cart_row, null);

                    TextView productName = (TextView) row.findViewById(R.id.productName);
                    final TextView total_price = (TextView) row.findViewById(R.id.total_price);
                    final EditText quantity = (EditText) row.findViewById(R.id.quantity);
                    final EditText price = (EditText) row.findViewById(R.id.price);

                    productName.setText(modell.getName());
                    quantity.setText(Utils.getFormattedAmount(modell.getQuantity()));
                    price.setText(Utils.getFormattedAmount(modell.getNetAmount()));
                    amount += Double.parseDouble(modell.getNetAmount());
                    total_price.setText(Utils.getFormattedAmount(modell.getNetAmount()) + " €");
                    price.setEnabled(false);
                    total_price.setFocusable(false);
                    price.setFocusable(false);
                    total_price.setEnabled(false);

                    content.addView(row);
                }
            }
        totalPayableAmout=model.getOrderData().getGrossAmount() ;
        grandTotal.setText(Utils.getFormattedAmount(model.getOrderData().getNetAmount())+" €");
        if(dailog!=null)
        {dailog.cancel();}
    }
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
                {model=new Gson().fromJson(value,  OutstandingOrderModel.class);
                    switch (apiCall)
                    {
                        case 1:
                            if(Integer.parseInt(model.getCode())==200) {
                                setValue();
                            }else{
                                dailog.cancel();
                                Toast.makeText(GetOutstandingOrderDetails.this,model.getMsg(),Toast.LENGTH_SHORT).show();

                            }
                            break;
                        case 2:
                            if(model.getOrderData().getStatus().equalsIgnoreCase("paid"))
                            {
                                Toast.makeText(GetOutstandingOrderDetails.this,"Payment status updated sucessfully to shopkeeper",Toast.LENGTH_SHORT).show();
                              finish();
                            }

                            break;
                    }
                }else{
                    Utils.showToast(GetOutstandingOrderDetails.this,Utils.getMessage(value));
                    cancelDialog();
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
                        String paymentId=Utils.getPaymentId(confirm.toJSONObject().toString(4));
                        Toast.makeText(GetOutstandingOrderDetails.this,"Payment Confirmation  received from PayPal,Payment ID :"+paymentId,Toast.LENGTH_SHORT).show();
                        updatePaymentDetails(paymentId);

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

    public void cancelDialog()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(pd!=null)
                {
                    pd.cancel();
                }
            }
        });
    }
    public void updatePaymentDetails(String paymentId)
    {
        if(Utils.isNetworkAvailable(GetOutstandingOrderDetails.this))
        { dailog.show();
            apiCall=updatePaymentStatus;
            controller.getApiCall().postDataWithJSon(Common.updatePaymentStatus,controller.getPrefManager().getUserToken(),Common.updatePaymentStatusKeys,new String[]{orderId,paymentId},GetOutstandingOrderDetails.this);
        }
    }

    }