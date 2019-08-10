package utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.spaytconsumer.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import prelogin.Login;


/**
 * Created by ashish.kumar on 22-10-2018.
 */

public class Utils {
    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(act, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static String getDeviceID(Activity act) {
        String deviceId = "";
        deviceId = Settings.Secure.getString(act.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }
    public static boolean getStatus(String data) {
        boolean status = false;
        try {
            JSONObject jsonObject = new JSONObject(data);
            if(jsonObject.isNull("Status")) {
                int code = jsonObject.getInt("code");
                if (code == 200) {
                    return true;
                }
            }else{
                return jsonObject.getBoolean("Status");
            }
        } catch (Exception ex) {

            ex.fillInStackTrace();
        }
        return status;
    }
    public static String getQRUrl(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
          return jsonObject.getString("qrurl");
        } catch (Exception ex) {

            ex.fillInStackTrace();
        }
        return "";
    }

    public static JSONObject getJSONObject(String data,String key) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            return jsonObject.getJSONObject(key);
        } catch (Exception ex) {

            ex.fillInStackTrace();
        }
        return null;
    }

    public static void showToast(final Activity act, final String message) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, message, Toast.LENGTH_LONG).show();
            }
        });

    }
    public static String getMessage(String data) {
        String message = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            message = jsonObject.getString("msg");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return message;
    }
    public static Dialog showPogress(Activity act) {
        final Dialog dialog = new Dialog(act);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        return dialog;
    }
    public static Dialog showBluePogress(Activity act) {
        final Dialog dialog = new Dialog(act);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        return dialog;
    }
    public static void Logout(final Activity act) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(act, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(intent);
            }

        });

    }
    public static String getCompleteAddressString(final Activity act, final double LATITUDE, final double LONGITUDE) {
        List<Address> list;
        String strAdd=null;
        Geocoder geocoder = new Geocoder(act, Locale.getDefault());
        try {
            list = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            for (int n = 0; n <= list.get(0).getMaxAddressLineIndex()-2; n++) {
                strAdd +=   list.get(0).getAddressLine(n) + ", ";
            }

            strAdd = list.get(0).getLocality();
            if(strAdd.length()>22)
            {
                strAdd= strAdd.substring(0,22);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAdd;

    }
public static JSONArray getJSonArray(String value)
{
    try{
        JSONArray jsonArray=new JSONArray(value);
        return jsonArray;
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    return null;
}

    public static JSONObject getTimers(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            return jsonObject.getJSONObject("users_timer");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static JSONObject getLocationDetails(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONArray jsonArray = jsonObject.getJSONArray("Location Details");

            return jsonArray.getJSONObject(0);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }
    public static JSONArray getLocationsArray(String value)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);
            JSONArray jsonArray=jsonObject.getJSONArray("locations");
            return jsonArray;
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static boolean[] isTimerStarted(String value)
    {
        try{JSONObject jsonObj=new JSONObject(value);
            JSONObject jsonObject=jsonObj.getJSONObject("users_timer");

            return new boolean[]{jsonObject.getBoolean("IsTimerStarted"),jsonObject.getBoolean("IsTimerStoped"),jsonObject.getBoolean("IsPaymentDone")};
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return new boolean[]{};
    }

    public static String getcarPlateNumber(String value) {
        try{
            JSONObject jsonObject=new JSONObject(value);
           return jsonObject.getString("parking_carplate_no");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return "";
    }

    public static String getTotalParkingAmount(String value) {
        try{
            JSONObject jsonObject=new JSONObject(value);
            return jsonObject.getString("total_parking_fees");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static String getParkingDuration(String start_time, String end_time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
        int day=0;
        int hour=00;
        int  minute=00;
        int second=00;
        try {

            Date date1=dateFormat.parse(start_time);
            Date date2=dateFormat.parse(end_time);
            long miliseconds=date1.getTime();

            long  difference=(date2.compareTo(date1))/1000;
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
        if(day>0)
        {
            return day+"d"+":"+hour+":"+minute+":"+second;
        }else{
            return hour+":"+minute+":"+second;
        }

    }
public static String getPaymentId(String value)
{
    try{
        JSONObject jsonObject=new JSONObject(value);
        JSONObject response=jsonObject.getJSONObject("response");
        return response.getString("id");
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    return "";
}
    public static String getFormattedAmount(String amount) {
        String formatedValue;
        try{
            if(amount.contains(","))
            {
                amount=amount.replaceAll(",","");
            }
            if(amount.contains(".")) {
                String[] value = amount.split("\\.");
                if (value[1].length() > 2) {
                    formatedValue=String.format("%,.3f", Double.parseDouble(amount));

                }else{
                    formatedValue= String.format("%,.2f", Double.parseDouble(amount));
                }

            }else{
                formatedValue= String.format("%,.2f", Double.parseDouble(amount));
            }
            if(formatedValue.contains(","))
            {
                formatedValue=formatedValue.replaceAll(",","");
            }
            return formatedValue;
        } catch (Exception ex)
        {
            ex.fillInStackTrace();
        }

        return "";
    }
    public static String getString(String data,String key) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            value =jsonObject.isNull(key)?"": jsonObject.getString( key);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        if(value.contains("//"))
        {
            value=value.replaceAll("//","/");
        }
        if(value.contains("https:/"))
        {
            value=value.replaceAll("https:/","https://");
        }
        return value;
    }

    public static String getFileName(String loggedInCustomer)
    {
        return "Invoice_"+loggedInCustomer+"_"+Long.toString(System.currentTimeMillis())+".pdf";
    }
}
