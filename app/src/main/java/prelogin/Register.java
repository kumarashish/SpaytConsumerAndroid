package prelogin;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.spaytconsumer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import intefaces.WebApiResponseCallback;
import models.UserProfile;
import utils.Validation;

/**
 * Created by ashish.kumar on 22-10-2018.
 */

public class Register extends Activity implements View.OnClickListener, WebApiResponseCallback{

    @BindView(R.id.input_fname)
    EditText input_fname;
    @BindView(R.id.input_name)
    EditText input_Lname;
    @BindView(R.id.input_email)
    EditText edt_email;
    @BindView(R.id.input_password)
    EditText input_password;
    @BindView(R.id.input_confirmpassword)
    EditText input_confirmpassword;
    @BindView(R.id.input_layout_fname)
    TextInputLayout input_layout_fname;
    @BindView(R.id.input_layout_name)
    TextInputLayout input_layout_name;
    @BindView(R.id.input_layout_email)
    TextInputLayout input_layout_email;
    @BindView(R.id.input_layout_password)
    TextInputLayout input_layout_password;
    @BindView(R.id.input_layout_confirmpassword)
    TextInputLayout input_layout_confirmpassword;
    @BindView(R.id.btn_register)
    Button register;
    @BindView(R.id.back)
    Button back;
    Validation validation;
    Dialog dialog;
    AppController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        if( (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)&&(Build.VERSION.SDK_INT <27) ){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else if(Build.VERSION.SDK_INT >=27){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)register.getLayoutParams();
            params.bottomMargin=120;
        }
        validation=new Validation(this);
        controller=(AppController)getApplicationContext();

        back.setOnClickListener(this);
        register.setOnClickListener(this);
       input_fname.addTextChangedListener(new MyTextWatcher( input_fname));
      input_Lname.addTextChangedListener(new MyTextWatcher(input_Lname));
      edt_email.addTextChangedListener(new MyTextWatcher(edt_email));
      input_password.addTextChangedListener(new MyTextWatcher(input_password));
      input_confirmpassword.addTextChangedListener(new MyTextWatcher( input_confirmpassword));
    }
    private void submitForm() {
        if (!validation.validateFName(input_fname, input_layout_fname)) {
            return;
        }
        if (!validation.validateLName(input_Lname, input_layout_name)) {
            return;
        }
        if (!validation.validateEmail(edt_email, input_layout_email)) {
            return;
        }
        if (!validation.validatePassword(input_password, input_layout_password)) {
            return;
        }
        if (!validation.validateConfirmPassword(input_confirmpassword, input_layout_confirmpassword)) {
            return;
        }
        if (!validation.validatePasswordConfirmPassword(input_password, input_layout_password,input_confirmpassword, input_layout_confirmpassword)) {
            return;
        }
        if (utils.Utils.isNetworkAvailable(Register.this)) {
            dialog = utils.Utils.showPogress(this);
            //controller.getApiCall().register(Common.register,input_fname.getText().toString(),input_Lname.getText().toString(),edt_email.getText().toString(),input_password.getText().toString(), this);
            controller.getApiCall().getData(Common.getRegisterUrl(input_fname.getText().toString(),input_Lname.getText().toString(),edt_email.getText().toString(),input_password.getText().toString()), this);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn_register:
                submitForm();
                break;
        }

    }

    @Override
    public void onSucess(String value) {
        if (utils.Utils.getStatus(value)) {
            utils.Utils.showToast(this,"Registered Sucessfully.Verification mail has been sent to "+edt_email.getText().toString()+",Please verify to use service.");
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
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_fname:
                    validation.validateFName(input_fname, input_layout_fname);
                    break;
                case R.id.input_name:
                    validation.validateLName(input_Lname, input_layout_name);
                    break;
                case R.id.input_email:
                    validation.validateEmail(edt_email, input_layout_email);
                    break;
                case R.id.input_password:
                    validation.validatePassword(input_password, input_layout_confirmpassword);
                    break;
                case R.id.input_confirmpassword:
                    validation.validatePassword(input_confirmpassword, input_layout_confirmpassword);
                    break;

            }
        }


    }
}