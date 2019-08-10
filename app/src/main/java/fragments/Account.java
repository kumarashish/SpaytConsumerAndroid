package fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spaytconsumer.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import adapter.Invoices_ListAdapter;
import common.AppController;
import common.Common;
import common.FileDownloader;
import intefaces.InvoiceButtonClickListner;
import intefaces.WebApiResponseCallback;
import models.OrderInvoiceModel;
import models.UserProfile;
import utils.Utils;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by ashish.kumar on 30-10-2018.
 */
public class Account extends Fragment implements View.OnClickListener , WebApiResponseCallback, InvoiceButtonClickListner {
    LinearLayout view1;
    LinearLayout view2;
    LinearLayout view3;
    View personalInfo,ruchnungen;
    Button back,back2;
    EditText fname;
    EditText lname;
    TextView jan,feb,march,april,may,june,july,august,sep,oct,nov,dec;
    ListView invoices_list;
    HorizontalScrollView scrollView;
    AppController controller;
    Dialog dialog;
    int apiCall=0;
    int updateProfile=1,getInvoice=2,getQRCode=3,downloadInvoice=5,emailInvoice=4;
    int currentYear=2018;

    ImageView qrCode;
   // View profile;
   InvoiceButtonClickListner callback;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accounts, container, false);
        controller=(AppController)getActivity().getApplicationContext();
        callback=this;
        personalInfo=(View)view.findViewById(R.id.personalInfo);
        //profile=(View)view.findViewById(R.id.profile);
        ruchnungen=(View)view.findViewById(R.id.ruchnungen);
        view1=(LinearLayout)view.findViewById(R.id.view1);
        view2=(LinearLayout)view.findViewById(R.id.view2);
        view3=(LinearLayout)view.findViewById(R.id.view3);
        fname=(EditText)view.findViewById(R.id.fname);
        lname=(EditText)view.findViewById(R.id.lname);
        fname.setText(controller.getProfile().getFirst_name());
        lname.setText(controller.getProfile().getLast_name());
        fname.setSelection(controller.getProfile().getFirst_name().length());
        lname.setSelection(controller.getProfile().getLast_name().length());
        qrCode=(ImageView)view.findViewById(R.id.qrCode);
        jan=(TextView)view.findViewById(R.id.jan);
        feb=(TextView)view.findViewById(R.id.feb);
        march=(TextView)view.findViewById(R.id.mar);
        april=(TextView)view.findViewById(R.id.aprl);
        may=(TextView)view.findViewById(R.id.may);
        june=(TextView)view.findViewById(R.id.jun);
        july=(TextView)view.findViewById(R.id.jul);
        august=(TextView)view.findViewById(R.id.aug);
        sep=(TextView)view.findViewById(R.id.sep);
        oct=(TextView)view.findViewById(R.id.oct);
        nov=(TextView)view.findViewById(R.id.nov);
        dec=(TextView)view.findViewById(R.id.dec);
        invoices_list=(ListView)view.findViewById(R.id.invoices_list);
        back=(Button) view.findViewById(R.id.back);
        back2=(Button) view.findViewById(R.id.back2);
        scrollView=(HorizontalScrollView)  view.findViewById(R.id.horizontalScrollView);
        personalInfo.setOnClickListener(this);
        ruchnungen.setOnClickListener(this);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);
        setMonthsIngerman();
        jan.setOnClickListener(this);
        feb.setOnClickListener(this);
        march.setOnClickListener(this);
        april.setOnClickListener(this);
        may.setOnClickListener(this);
        june.setOnClickListener(this);
        july.setOnClickListener(this);
        august.setOnClickListener(this);
        sep.setOnClickListener(this);
        oct.setOnClickListener(this);
        nov.setOnClickListener(this);
        dec.setOnClickListener(this);
       // profile.setOnClickListener(this);
        getQRCode();
        lname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                   if((fname.getText().length()>0)&&(lname.getText().length()>0))
                    {
                        if(Utils.isNetworkAvailable(getActivity()))
                        {   apiCall=updateProfile;
                            dialog=Utils.showPogress(getActivity());
                            controller.getApiCall().postData(Common.updateProfile,upDateProfileJSON().toString(),Account.this);
                        }
                    }else {
                       if(fname.getText().length()==0)
                       {
                           Utils.showToast(getActivity(),"Please enter first name");
                       }else{
                           Utils.showToast(getActivity(),"Please enter last name");
                       }
                   }
                }
                return false;
            }
        });


        return view;
    }


    public void getQRCode()
    {
        if(Utils.isNetworkAvailable(getActivity()))
        {
            apiCall=getQRCode;
            dialog=Utils.showPogress(getActivity());
            controller.getApiCall().getData(Common.getQRCode,controller.getPrefManager().getUserToken(),Account.this);
        }

    }

    public void setMonthsIngerman()
    {
        jan.setText("Januar");
        feb.setText("Februar");
        march.setText("März");

        may.setText("Kann");
        june.setText("Juni");
        july.setText("Juli");


        oct.setText("Oktober");

        dec.setText("Dezember");
    }

    public JSONObject upDateProfileJSON() {
        JSONObject jsonObject = new JSONObject();
        {
            try {
                jsonObject.put("id", controller.getProfile().getUser_id());
                jsonObject.put("first_name", controller.getProfile().getFirst_name());
                jsonObject.put("last_name", controller.getProfile().getLast_name());
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
        return jsonObject;
    }

    public void getData(int currentMonth) {
        if (Utils.isNetworkAvailable(getActivity())) {
            apiCall=getInvoice;
            dialog = Utils.showPogress(getActivity());
             controller.getApiCall().getInvoices(Common.getInvoices, controller.getPrefManager().getUserToken(), Integer.toString(currentMonth), Integer.toString(currentYear), Account.this);
            //controller.getApiCall().getInvoices(Common.getInvoices,"81", Integer.toString(currentMonth), Integer.toString(currentYear), Account.this);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case    R.id.personalInfo:
            //case    R.id.profile:
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
//                if(Utils.isNetworkAvailable(getActivity()))
//                {   apiCall=getQRCode;
//                    dialog=Utils.showPogress(getActivity());
//                    controller.getApiCall().getData(Common.getQRCode,Account.this);
//                }
                break;
            case      R.id.ruchnungen:
                Calendar c = Calendar.getInstance();
                int month = c.get(Calendar.MONTH);
               currentYear=c.get(Calendar.YEAR);
                   month=month+1;
                getData(month);
                   if(month>5)
                   {
                       scrollView.post(new Runnable() {
                           @Override
                           public void run() {
                               scrollView.fullScroll(View.FOCUS_RIGHT);

                           }
                       });
                   }
                handleMonth( month);
                view1.setVisibility(View.GONE);
                view3.setVisibility(View.VISIBLE);
                break;
            case      R.id.back:
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                break;
            case      R.id.back2:
                view1.setVisibility(View.VISIBLE);
                view3.setVisibility(View.GONE);
                break;
            case R.id.jan:
                handleMonth(1);
                getData(1);
                break;
            case R.id.feb:
                handleMonth(2);
                getData(2);
                break;
            case R.id.mar:
                handleMonth(3);
                getData(3);
                break;
            case R.id.aprl:
                handleMonth(4);
                getData(4);
                break;
            case R.id.may:
                handleMonth(5);
                getData(5);
                break;
            case R.id.jun:
                handleMonth(6);
                getData(6);
                break;
            case R.id.jul:
                handleMonth(7);
                getData(7);
                break;
            case R.id.aug:
                handleMonth(8);
                getData(8);
                break;
            case R.id.sep:
                handleMonth(9);
                getData(9);
                break;
            case R.id.oct:
                handleMonth(10);
                getData(10);
                break;
            case R.id.nov:
                handleMonth(11);
                getData(11);
                break;
            case R.id.dec:
                handleMonth(12);
                getData(12);
                break;
        }
    }

    public void handleMonth(int month)
    {switch (month)
    {
        case 1:
            jan.setTextColor(getResources().getColor(android.R.color.black));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 2:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.black));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 3:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.black));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 4:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.black));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 5:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.black));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 6:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.black));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 7:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.black));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 8:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.black));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 9:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.black));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 10:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.black));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 11:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.black));
            dec.setTextColor(getResources().getColor(android.R.color.darker_gray));
            break;
        case 12:
            jan.setTextColor(getResources().getColor(android.R.color.darker_gray));
            feb.setTextColor(getResources().getColor(android.R.color.darker_gray));
            march.setTextColor(getResources().getColor(android.R.color.darker_gray));
            april.setTextColor(getResources().getColor(android.R.color.darker_gray));
            may.setTextColor(getResources().getColor(android.R.color.darker_gray));
            june.setTextColor(getResources().getColor(android.R.color.darker_gray));
            july.setTextColor(getResources().getColor(android.R.color.darker_gray));
            august.setTextColor(getResources().getColor(android.R.color.darker_gray));
            sep.setTextColor(getResources().getColor(android.R.color.darker_gray));
            oct.setTextColor(getResources().getColor(android.R.color.darker_gray));
            nov.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dec.setTextColor(getResources().getColor(android.R.color.black));
            break;
    }

    }

    @Override
    public void onSucess(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (apiCall) {
                    case 1:
                        if (utils.Utils.getStatus(value)) {
                            UserProfile profile = controller.getProfile();
                            profile.setFirst_name(fname.getText().toString());
                            profile.setLast_name(lname.getText().toString());
                            controller.setProfile(profile);
                        } else {
                            utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
                        }
                        break;
                    case 2:
                        if (!value.contains("False")) {
                            try {
                                final OrderInvoiceModel model = new Gson().fromJson(value, OrderInvoiceModel.class);
                                if (model.getOrderData().size() > 0) {

                                    invoices_list.setAdapter(new Invoices_ListAdapter(getActivity(), model.getOrderData(), callback));
                                    invoices_list.setVisibility(View.VISIBLE);
                                } else {
                                    invoices_list.setVisibility(View.GONE);
                                    Utils.showToast(getActivity(), "No record found");
                                }
                            } catch (Exception ex) {
                                ex.fillInStackTrace();
                                invoices_list.setVisibility(View.GONE);
                                Utils.showToast(getActivity(), ex.fillInStackTrace().toString());
                            }
                        } else {
                            invoices_list.setVisibility(View.GONE);
                            Utils.showToast(getActivity(), "No record found");
                        }

                        break;
                    case 3:
                        if (utils.Utils.getStatus(value)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String qrUrl = Utils.getQRUrl(value);
                                    if (qrUrl.length() > 0) {
                                        Picasso.with(getActivity()).load(qrUrl).placeholder(R.drawable.progress_drawable).error(android.R.drawable.stat_notify_error).into(qrCode);
                                    }
                                }
                            });
                        } else {
                            utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
                        }
                        break;

                    case 4:
                        if (dialog != null) {
                            dialog.cancel();
                        }
                        if (Utils.getStatus(value)) {
                            Utils.showToast(getActivity(), "Email has been send to registered Email Id.");
                        } else {
                            Utils.showToast(getActivity(), Utils.getMessage(value));
                        }
                        break;
                    case 5:
                        if (dialog != null) {
                            dialog.cancel();
                        }
                        if (Utils.getStatus(value)) {
                            String pdfPath = Utils.getString(value, "pdf_path");
                            dialog.show();
                            new DownloadFile().execute(pdfPath, Utils.getFileName(controller.getProfile().getFirst_name()));
                        } else {
                            Utils.showToast(getActivity(), Utils.getMessage(value));
                        }
                        break;
                }
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        });
    }

    @Override
    public void onError(String value) {
        utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        if(dialog!=null)
        {
            dialog.cancel();
        }
    }

    @Override
    public void onEmailClick(final String orderId) {
getActivity().runOnUiThread(new Runnable() {
    @Override
    public void run() {
   emailInvoice(orderId);
    }
});
    }

    @Override
    public void onDownloadClick(final String orderId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handleDownload(orderId);
            }
        });
    }

    public void emailInvoice(String orderId) {
        apiCall = emailInvoice;
        dialog = Utils.showBluePogress(getActivity());
        controller.getApiCall().postData(Common.sendOrderPdfEmail, controller.getPrefManager().getUserToken(), Common.id, new String[]{orderId}, this);

    }

    public void handleDownload(String orderId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                downloadInvoice(orderId);
                //File write logic here

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
            }
        } else {
            downloadInvoice(orderId);
        }
    }
    public void downloadInvoice(String orderId) {

        apiCall = downloadInvoice;
        dialog = Utils.showBluePogress(getActivity());
        controller.getApiCall().postData(Common.downloadOrderPdfInvoice, controller.getPrefManager().getUserToken(), Common.id, new String[]{orderId}, this);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
           Utils.showToast(getActivity(),"Permission granted");
        }
    }
    private class DownloadFile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "SpaytBusinessInvoices");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return FileDownloader.downloadFile(fileUrl, pdfFile, getActivity());

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() > 0) {
                openPdf(s);
            }
           dialog.cancel();
        }
    }

    public void openPdf(String fileName) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SpaytBusinessInvoices/" + fileName);  // -> filename = maven.pdf
        if (pdfFile .exists()) {

            Uri path = Uri.fromFile(pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(pdfIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }catch (Exception ex)
            {
                ex.fillInStackTrace();
            }
        }else{
            Toast.makeText(getActivity(), "File Doesnot Exists.", Toast.LENGTH_SHORT).show();
        }
    }
}

