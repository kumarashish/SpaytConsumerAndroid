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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import adapter.OrderAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import intefaces.ButtonClickListner;
import intefaces.WebApiResponseCallback;
import models.OrderModel;
import models.OutstandingOrderModel;
import prelogin.Login;
import utils.Utils;

public class OrderList  extends Activity implements View.OnClickListener , WebApiResponseCallback , ButtonClickListner {
    @BindView(R.id.back)
    ImageView back;

    AppController controller;

    @BindView(R.id.listview)
    ListView orderList;
    @BindView(R.id.noOrderView)
            TextView noorderView;
    Dialog pd;
    int apiCall;

    String totalPayableAmout = "";

    int getOrderList = 1;
    Dialog dailog;
  OrderModel model=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        if (Utils.isNetworkAvailable(OrderList.this)) {
            dailog = Utils.showPogress(OrderList.this);
            apiCall = getOrderList;
            controller.getApiCall().getData(Common.getOutstandingOrder, controller.getPrefManager().getUserToken(), this);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;



        }
    }


    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (dailog!= null) {
                    dailog.cancel();
                }
                if (Utils.getStatus(value) == true) {
                    try{

                        model=new Gson().fromJson(value,OrderModel.class);
                        if(model.getOrderData().size()>0){
                            orderList.setAdapter(new OrderAdapter(OrderList.this,model.getOrderData()));
                            orderList.setVisibility(View.VISIBLE);
                            noorderView.setVisibility(View.GONE);
                        }else{
                            noorderView.setVisibility(View.VISIBLE);
                            orderList.setVisibility(View.GONE);
                        }
                    }catch (Exception ex)
                    {
                      ex.fillInStackTrace();
                    }

                } else {
                    Utils.showToast(OrderList.this, Utils.getMessage(value));
                }
            }
        });


    }


    @Override
    public void onError(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(OrderList.this, Utils.getMessage(value));
                if (dailog != null) {
                    dailog.cancel();
                }
            }
        });
    }

    @Override
    public void OnDetailsClicked(final String orderId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(OrderList.this, GetOutstandingOrderDetails.class);

                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
    }
}