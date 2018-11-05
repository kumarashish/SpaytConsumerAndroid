package fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.spaytconsumer.R;

import org.json.JSONObject;

import java.util.Calendar;

import common.AppController;
import common.Common;
import intefaces.WebApiResponseCallback;
import models.UserProfile;
import utils.Utils;

/**
 * Created by ashish.kumar on 30-10-2018.
 */

public class Account extends Fragment implements View.OnClickListener , WebApiResponseCallback{
    LinearLayout view1;
    LinearLayout view2;
    LinearLayout view3;
    View personalInfo,ruchnungen;
    Button back,back2;
    EditText fname;
    EditText lname;
    TextView jan,feb,march,april,may,june,july,august,sep,oct,nov,dec;
    HorizontalScrollView scrollView;
AppController controller;
Dialog dialog;
int apiCall=0;
int updateProfile=1,getInvoice=2;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accounts,
                container, false);
        controller=(AppController)getActivity().getApplicationContext();
        personalInfo=(View)view.findViewById(R.id.personalInfo);
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
        back=(Button) view.findViewById(R.id.back);
        back2=(Button) view.findViewById(R.id.back2);
        scrollView=(HorizontalScrollView)  view.findViewById(R.id.horizontalScrollView);
        personalInfo.setOnClickListener(this);
        ruchnungen.setOnClickListener(this);
        back.setOnClickListener(this);
        back2.setOnClickListener(this);

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
        lname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                   if((fname.getText().length()>0)&&(lname.getText().length()>0))
                    {
                        if(Utils.isNetworkAvailable(getActivity()))
                        {
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
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case      R.id.personalInfo:
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                break;
            case      R.id.ruchnungen:
                Calendar c = Calendar.getInstance();
                int month = c.get(Calendar.MONTH);
                   month=month+1;
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
                break;
            case R.id.feb:
                handleMonth(2);
                break;
            case R.id.mar:
                handleMonth(3);
                break;
            case R.id.aprl:
                handleMonth(4);
                break;
            case R.id.may:
                handleMonth(5);
                break;
            case R.id.jun:
                handleMonth(6);
                break;
            case R.id.jul:
                handleMonth(7);
                break;
            case R.id.aug:
                handleMonth(8);
                break;
            case R.id.sep:
                handleMonth(9);
                break;
            case R.id.oct:
                handleMonth(10);
                break;
            case R.id.nov:
                handleMonth(11);
                break;
            case R.id.dec:
                handleMonth(12);
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
    public void onSucess(String value) {
        if(   utils.Utils.getStatus(value)) {
            UserProfile profile=controller.getProfile();
            profile.setFirst_name(fname.getText().toString());
            profile.setLast_name(lname.getText().toString());
            controller.setProfile(profile);
        }
        utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        if(dialog!=null)
        {
            dialog.cancel();
        }
    }

    @Override
    public void onError(String value) {
        utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        if(dialog!=null)
        {
            dialog.cancel();
        }
    }
}
