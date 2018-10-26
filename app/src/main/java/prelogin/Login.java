package prelogin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.spaytconsumer.DashBoard;
import com.spaytconsumer.R;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import common.AppController;
import common.Common;
import intefaces.WebApiResponseCallback;
import models.UserProfile;
import utils.Validation;

/**
 * Created by ashish.kumar on 22-10-2018.
 */

public class Login extends Activity implements View.OnClickListener, WebApiResponseCallback{
    @BindView(R.id.input_email)
    EditText edt_email;
    @BindView(R.id.input_password)
    EditText edt_password;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_fblogin)
    LoginButton btn_fblogin;
    @BindView(R.id.fbLogin)
    Button fbLogin;
    @BindView(R.id.txtvw_forgetPassword)
    TextView forgetPassword;
    @BindView(R.id.txtvw_register)
    TextView register;
    Validation validation;
    private static final String EMAIL = "email";
    AppController controller;
    int apiCall=-1;
    int login=1,loginWithFb=2,registerwithFb=3;
    Dialog dialog;
    CallbackManager  callbackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        validation=new Validation(this);
        edt_email.addTextChangedListener(new MyTextWatcher(edt_email));
        edt_password.addTextChangedListener(new MyTextWatcher( edt_password));
        btn_login.setOnClickListener(this);
        btn_fblogin.setOnClickListener(this);
        fbLogin.setOnClickListener(this);
        btn_fblogin.setReadPermissions(Arrays.asList(EMAIL,"public_profile"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        btn_fblogin.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        // App code
                        if (loginResult.getAccessToken().isExpired()) {
                            LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "email"));
                        }
                        btn_fblogin.setVisibility(View.GONE);
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        try {
                                            // Application code
                                            String email = response.getJSONObject().getString("email");
                                            String id = response.getJSONObject().getString("id");
                                            String name = response.getJSONObject().getString("name");
                                            apiCall = loginWithFb;
                                            dialog = utils.Utils.showPogress(Login.this);
                                            controller.getApiCall().loginWithFb(Common.registerWithFb, id, email, name, utils.Utils.getDeviceID(Login.this), loginResult.getAccessToken().getToken(), Login.this);
                                            LoginManager.getInstance().logOut();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ;
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                        Log.d("Status", loginResult.toString());

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("Status", exception.toString());
                    }
                });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                submitForm();
                break;
            case R.id.txtvw_forgetPassword:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
            case R.id.txtvw_register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.fbLogin:
                btn_fblogin.performClick();
                break;
        }
    }

    private void submitForm() {
        if (!validation.validateEmail(edt_email, inputLayoutEmail)) {
            return;
        }
        if (!validation.validatePassword(edt_password, inputLayoutPassword)) {
            return;
        }
        if (utils.Utils.isNetworkAvailable(Login.this)) {
            apiCall = login;
            dialog = utils.Utils.showPogress(this);
            controller.getApiCall().login(Common.login, edt_email.getText().toString(), edt_password.getText().toString(), utils.Utils.getDeviceID(Login.this), this);
        }
    }

    @Override
    public void onSucess(String value) {
        if ((utils.Utils.getStatus(value))||utils.Utils.getMessage(value).equalsIgnoreCase("Email already in use")) {
             controller.setProfile(new UserProfile(value));
             controller.getPrefManager().setUserLoggedIn(true);
            utils.Utils.showToast(this, "Logged in sucessfully.");
           startActivity(new Intent(this, DashBoard.class));
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
                case R.id.input_email:
                    validation.validateEmail(edt_email,inputLayoutEmail);
                    break;
                case R.id.input_password:
                    validation.validatePassword(edt_password,inputLayoutPassword);
                    break;

            }
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
