package fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.spaytconsumer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.LocationSearch;
import utils.Utils;

/**
 * Created by ashish.kumar on 30-10-2018.
 */

public class Home  extends Fragment implements View.OnClickListener {

    Button logout;
    AppController controller;
    TextView city;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,
                container, false);
        ButterKnife.bind(getActivity());
        controller = (AppController) getActivity().getApplicationContext();
        logout=(Button)view.findViewById(R.id.logout);
        city=(TextView) view.findViewById(R.id.location_name);
        logout.setOnClickListener(this);
        city.setOnClickListener(this);
        return view;
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

}
