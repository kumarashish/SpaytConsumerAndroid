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
            int code = jsonObject.getInt("code");
            if (code == 200) {
                return true;
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return status;
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

    public static boolean isTimerStarted(String value)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);

            return jsonObject.getBoolean("istimerstarted");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return false;
    }

}
