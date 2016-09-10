package hack.galert;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    TextView appIconText;
    TextView personEmailIconText;
    TextView passwordIconText;
    TextView loginBtnText;
    TextView appTitlleText;

    EditText mEmail;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPreferenceManager.getInstance(this).isLoggedIn()) {
            navigate();
        }

        initializeComponents();
        attachListeners();
    }

    public void initializeComponents() {

        appIconText = (TextView) findViewById(R.id.appIconText);
        personEmailIconText = (TextView) findViewById(R.id.profileIcon);
        passwordIconText = (TextView) findViewById(R.id.lockIcon);
        loginBtnText = (TextView) findViewById(R.id.loginBtn);
        appTitlleText = (TextView) findViewById(R.id.appTitle);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        setTypeFace();

    }

    public void setTypeFace() {

        Typeface materialTypeFace = FontManager.getInstance(this).getTypeFace(FontManager.FONT_MATERIAL);
        Typeface robotoMedium = FontManager.getInstance(this).getTypeFace(FontManager.FONT_ROBOTO_MEDIUM);
        Typeface robotoRegular = FontManager.getInstance(this).getTypeFace(FontManager.FONT_ROBOTO_REGULAR);

        //material Icon Font
        appIconText.setTypeface(materialTypeFace);
        personEmailIconText.setTypeface(materialTypeFace);
        passwordIconText.setTypeface(materialTypeFace);

        //roboto regular
        mEmail.setTypeface(robotoRegular);
        mPassword.setTypeface(robotoRegular);
        loginBtnText.setTypeface(robotoMedium);
        appTitlleText.setTypeface(robotoMedium);

    }

    public void attachListeners() {
        loginBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionUtils.getInstance(Login.this).isConnected()) {
                    attemptLogin();
                } else {
                    makeSnackbar("No Connectivity !");
                }

            }
        });
    }

    public void attemptLogin() {
        // send server request for validation
        if (validate()) {
            login();
        }

    }

    public boolean validate() {

        boolean validation = false;
        View viewToFocus = null;
        if (!mEmail.getText().toString().isEmpty()) {
            if (!mPassword.getText().toString().isEmpty()) {
                validation = true;
            } else {
                viewToFocus = mPassword;
            }
        } else {
            viewToFocus = mEmail;
        }

        if (viewToFocus != null) {
            viewToFocus.requestFocus();
        }

        return validation;
    }

    public void makeSnackbar(String msg) {
        Snackbar.make(passwordIconText, msg, Snackbar.LENGTH_LONG).show();
    }

    public void login() {

        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String TAG = "login";
        // TODO: append email and password to url
        final String url = Constants.SERVER_URL_LOGIN;

        StringRequest loginRequest = new StringRequest(
                StringRequest.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //TODO: parse json and navigate to home
                        //SharedPreferenceManager.getInstance(Login.this).setLoginStatus(true);
                        //navigate();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        makeSnackbar("Authentication Failed !");
                    }
                });

        VolleyUtils.getInstance().addToRequestQueue(loginRequest, TAG, this);
    }

    private void navigate() {

        Intent homeIntent = new Intent(this, Home.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();

    }

}
