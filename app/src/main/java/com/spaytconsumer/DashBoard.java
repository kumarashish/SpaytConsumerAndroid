package com.spaytconsumer;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import utils.Utils;

public class DashBoard extends Activity implements View.OnClickListener{
AppController controller;
    @BindView(R.id.dashdoard)
    View content;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        logout=(Button)content.findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.logout:
                controller.getPrefManager().setUserLoggedIn(false);
                Utils.Logout(DashBoard.this);
                break;
        }
    }
}
