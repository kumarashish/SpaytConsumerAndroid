package fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.spaytconsumer.R;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import common.LocationSearch;
import intefaces.WebApiResponseCallback;
import models.CategoryModel;
import utils.Utils;

/**
 * Created by ashish.kumar on 30-10-2018.
 */

public class Home  extends Fragment implements View.OnClickListener, WebApiResponseCallback {

    Button logout;
    AppController controller;
    TextView city,category;
    Dialog progressDailog;
    CategoryModel model=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,
                container, false);
        ButterKnife.bind(getActivity());
        controller = (AppController) getActivity().getApplicationContext();
        logout=(Button)view.findViewById(R.id.logout);
        city=(TextView) view.findViewById(R.id.location_name);
        category=(TextView)view.findViewById(R.id.category);
        if(controller.getAddress().length()>0) {
            city.setText(controller.getAddress());
            getBusinessLocations();

        }
        logout.setOnClickListener(this);
        city.setOnClickListener(this);
        return view;
    }

    public void getBusinessLocations()
    {
        if(model==null) {
            progressDailog = Utils.showPogress(getActivity());
            controller.getApiCall().getData(Common.getCategories, this);
        }else {
            category.setText(model.getCategoryName());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.logout:
                controller.getPrefManager().setUserLoggedIn(false);
                Utils.Logout(getActivity());
                break;

            case R.id.location_name:
                startActivityForResult(new Intent(getActivity(), LocationSearch.class),22);
                break;
        }
    }

    public void setCityName(String name) {
        if (city != null) {
            city.setText(name);
            getBusinessLocations();
        }
    }

    public void getData(String lat, String lon) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 22) && (resultCode == -1)) {
            city.setText(controller.getAddress());
        }
    }

    @Override
    public void onSucess(String value) {
        if(value.length()>0)
        {JSONArray jsonArray=Utils.getJSonArray(value);
        try {
            model = new CategoryModel(jsonArray.getJSONObject(0));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(model!=null)
                    {
                        category.setText(model.getCategoryName());
                    }
                }
            });
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        }else {
            utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        }
        if(progressDailog!=null)
        {
            progressDailog.cancel();
        }
    }

    @Override
    public void onError(String value) {
        utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        if(progressDailog!=null)
        {
            progressDailog.cancel();
        }
    }
}
