package prelogin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.internal.Validate;
import com.spaytconsumer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import common.AppController;
import common.Common;
import intefaces.WebApiResponseCallback;
import network.WebApiCall;
import okhttp3.internal.Util;
import utils.Validation;

/**
 * Created by ashish.kumar on 22-10-2018.
 */

public class ForgetPassword extends Activity implements View.OnClickListener , WebApiResponseCallback{
@BindView(R.id.input_email)
EditText edt_email;
@BindView(R.id.input_layout_email)
    TextInputLayout  inputLayoutEmail;
AppController controller;
Dialog dialog;
Validation validate;
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    setContentView(R.layout.forgetpassword);
    ButterKnife.bind(this);
    validate=new Validation(this);
    controller=(AppController)getApplicationContext();
}

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.btn_login:
            if( validate.validateEmail(edt_email,inputLayoutEmail)) {
                if (utils.Utils.isNetworkAvailable(ForgetPassword.this)) {
                    dialog = utils.Utils.showPogress(this);
                    controller.getApiCall().forgetPassword(Common.forgetPassword, edt_email.getText().toString(), this);
                }
            }
            break;
        case R.id.back:
            finish();
            break;
    }

    }

    @Override
    public void onSucess(String value) {
        if (utils.Utils.getStatus(value)) {
            utils.Utils.showToast(this, "Your password has been Reset Sucessfully.Your new password has been send to " + edt_email.getText().toString());
            finish();
        } else {
            utils.Utils.showToast(this, utils.Utils.getMessage(value));
        }
       if(dialog!=null)
       {
           dialog.cancel();
       }
    }

    @Override
    public void onError(String value) {
        utils.Utils.showToast(this, utils.Utils.getMessage(value));
        if(dialog!=null)
        {
            dialog.cancel();
        }
    }
}
