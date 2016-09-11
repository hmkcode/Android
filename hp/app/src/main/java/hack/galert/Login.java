package hack.galert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView appIconText;
    TextView personEmailIconText;
    TextView passwordIconText;
    TextView loginBtnText;
    TextView appTitlleText;

    EditText mEmail;
    EditText mPassword;
    private ProgressDialog progressDialog;
    private TextView registerText;
    private View decorView;

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
        registerText = (TextView) findViewById(R.id.registerText);

        // full screen mode
        decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

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

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
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
            makeSnackbar("Empty Fields !! ");
        }

        return validation;
    }

    public void makeSnackbar(String msg) {
        Snackbar.make(passwordIconText, msg, Snackbar.LENGTH_LONG).show();
    }

    public void login() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating You In ....");
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final String TAG = "login";


        final String url = Constants.SERVER_URL_LOGIN;
        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject("{\"email\":" + email + ",\"password\":\"" + password + "\"}");
            Log.d("Login", jsonBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest loginJsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String accessToken = "";
                        try {

                            accessToken = jsonObject.getString("access_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (accessToken != null) {
                            SharedPreferenceManager utils = SharedPreferenceManager.getInstance(Login.this);
                            utils.setUserEmail(email);
                            utils.setUserToken(accessToken);
                            utils.setLoginStatus(true);
                            progressDialog.dismiss();
                            navigate();
                        } else {
                            progressDialog.hide();
                            progressDialog.dismiss();
                            makeSnackbar("Authentication Failed ! ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Log.d("Login", volleyError.toString());
                        makeSnackbar("Authentication Failed !");
                    }
                });

        progressDialog.show();

        loginJsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(loginJsonRequest, TAG, this);
    }

    private void navigate() {

        Intent homeIntent = new Intent(this, Home.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();

    }

}
