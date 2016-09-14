package simple.musicgenie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ErrorSplash extends AppCompatActivity {

    private TextView tv;
    private TextView conError;
    private TextView contBtn;
    private TextView poweredBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_splash);

        // check connectivity and redirect
        redirectIfConnected();
        // set up Warning Page
        setUpWarningPage();


    }

    private void setUpWarningPage() {

        // xml -> java objects
        tv = (TextView) findViewById(R.id.no_con_text);
        conError = (TextView) findViewById(R.id.no_connection_wifi_icon);
        contBtn = (TextView) findViewById(R.id.continueBtn);
        poweredBy = (TextView) findViewById(R.id.poweredBy);

        // set Type faces
        tv.setTypeface(FontManager.getInstance(this).getTypeFace(FontManager.FONT_RALEWAY_REGULAR));
        conError.setTypeface(FontManager.getInstance(this).getTypeFace(FontManager.FONT_MATERIAL));
        contBtn.setTypeface(FontManager.getInstance(this).getTypeFace(FontManager.FONT_RALEWAY_REGULAR));
        poweredBy.setTypeface(FontManager.getInstance(this).getTypeFace(FontManager.FONT_RALEWAY_REGULAR));

        // set Text Colors
        conError.setTextColor(getResources().getColor(R.color.NoWifiColor));

        // attach Click Listener
        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToHome();
            }
        });


    }

    private void redirectIfConnected() {

        if (ConnectivityUtils.getInstance(this).isConnectedToNet())
            navigateToHome();
    }

    private void navigateToHome(){
        startActivity(new Intent(this, Home.class));
        finish();
    }


}
