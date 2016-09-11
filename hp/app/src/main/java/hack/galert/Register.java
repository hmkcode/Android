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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private static final String TAG = "Register";

    TextView userNameIcon;
    TextView confPassIcon;

    TextView appIconText;
    TextView personEmailIconText;
    TextView passwordIconText;
    TextView registerBtnText;
    TextView appTitlleText;
    TextView loginText;
    EditText mEmail;
    EditText mPassword;
    EditText mPasswordCnf;
    EditText fullName;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeComponents();

    }

    public void initializeComponents() {

        appIconText = (TextView) findViewById(R.id.appIconText);
        personEmailIconText = (TextView) findViewById(R.id.profileIcon);
        passwordIconText = (TextView) findViewById(R.id.lockIcon);
        registerBtnText = (TextView) findViewById(R.id.loginBtn);
        appTitlleText = (TextView) findViewById(R.id.appTitle);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mPasswordCnf = (EditText) findViewById(R.id.passwordCnf);
        fullName = (EditText) findViewById(R.id.fullName);
        loginText = (TextView) findViewById(R.id.loginText);
        userNameIcon = (TextView) findViewById(R.id.userIcon);
        confPassIcon = (TextView) findViewById(R.id.cnfPassIcon);

        setTypeFace();
        attachListeners();
    }

    public void setTypeFace() {

        Typeface materialTypeFace = FontManager.getInstance(this).getTypeFace(FontManager.FONT_MATERIAL);
        Typeface robotoMedium = FontManager.getInstance(this).getTypeFace(FontManager.FONT_ROBOTO_MEDIUM);
        Typeface robotoRegular = FontManager.getInstance(this).getTypeFace(FontManager.FONT_ROBOTO_REGULAR);

        //material Icon Font
        appIconText.setTypeface(materialTypeFace);
        personEmailIconText.setTypeface(materialTypeFace);
        passwordIconText.setTypeface(materialTypeFace);
        confPassIcon.setTypeface(materialTypeFace);
        userNameIcon.setTypeface(materialTypeFace);

        //roboto regular
        mEmail.setTypeface(robotoRegular);
        mPassword.setTypeface(robotoRegular);
        registerBtnText.setTypeface(robotoMedium);
        appTitlleText.setTypeface(robotoMedium);

    }

    public void attachListeners() {
        registerBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionUtils.getInstance(Register.this).isConnected()) {
                    attemptRegister();
                } else {
                    makeSnackbar("No Connectivity !");
                }

            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
    }

    public void attemptRegister() {
        // send server request for validation
        if (validate()) {
            register();
        }

    }

    public boolean validate() {

        boolean validation = false;
        View viewToFocus = null;
        String error = "";
        if (!mEmail.getText().toString().isEmpty()) {

            if (fullName.getText().toString().isEmpty()) {
                viewToFocus = fullName;
                error = "Have a Full Name !";
            }

            if (!mPassword.getText().toString().isEmpty()) {
                if (mPassword.getText().toString().equals(mPasswordCnf.getText().toString())) {
                    validation = true;
                } else {

                    error = "Password Does Not Matches !";
                }
            } else {
                error = "Must Have Password !";
                viewToFocus = mPassword;
            }
        } else {
            error = "Email Cannot Be Blank ! ";
            viewToFocus = mEmail;
        }



        if (viewToFocus != null) {
            viewToFocus.requestFocus();
            makeSnackbar(error);
        }

        return validation;
    }

    public void makeSnackbar(String msg) {
        Snackbar.make(passwordIconText, msg, Snackbar.LENGTH_LONG).show();
    }

    public void register() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Applying Registration Process....");
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String fullname = fullName.getText().toString();
        final String TAG = "register";


        final String url = Constants.SERVER_URL_REGISTER;
        JSONObject jsonBody = null;
        try {
            String header = "{" +
                    "\"email\":\"" + email + "\"," +
                    "\"full_name\":\"" + fullname + "\"," +
                    "\"password\":\"" + password + "\"" +
                    "}";

            Log.d("Register", header);
            jsonBody = new JSONObject(header);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest loginJsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String email = "";
                        String fName = "";
                        int userId = -1;
                        try {

                            email = jsonObject.getString("email");
                            fName = jsonObject.getString("full_name");
                            userId = jsonObject.getInt("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (userId > 0) {
                            SharedPreferenceManager.getInstance(Register.this).setUserFullName(fName);
                            SharedPreferenceManager.getInstance(Register.this).setUserEmail(email);
                            redirectLogin(email, password);
                        } else {
                            progressDialog.hide();
                            progressDialog.dismiss();
                            makeSnackbar("Registration Failed ! ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Log.d("Login", volleyError.toString());
                        makeSnackbar("Registration Failed !");
                    }
                });

        progressDialog.show();

        loginJsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyUtils.getInstance().addToRequestQueue(loginJsonRequest, TAG, this);
    }

    public void redirectLogin(String email, String password) {

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
                            SharedPreferenceManager utils = SharedPreferenceManager.getInstance(Register.this);
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
