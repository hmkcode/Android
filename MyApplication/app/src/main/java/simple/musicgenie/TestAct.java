package simple.musicgenie;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import simple.musicgenie.R;

public class TestAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final CentralDataRepository centralDataRepository = CentralDataRepository.getInstance(this);

        // here we will use same object for  pseudo-adapter mimic

        // pseudo-adapter registration

        centralDataRepository.registerForDataLoadListener(new CentralDataRepository.DataReadyToSubmitListener() {
            @Override
            public void onDataSubmit(ArrayList<SectionModel> items) {



            }
        });


        final TextView pad = (TextView) findViewById(R.id.pad);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  initiating operation
                // for first time load -  test
                try {
                    centralDataRepository.submitAction(CentralDataRepository.FLAG_FIRST_LOAD, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {
                            Toast.makeText(TestAct.this,"Action Callback",Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
