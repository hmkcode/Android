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

    private CentralDataRepository centralDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        centralDataRepository = CentralDataRepository.getInstance(this);
        final TextView pad = (TextView) findViewById(R.id.pad);

        // here we will use same object for  pseudo-adapter mimic

        // pseudo-adapter registration

        centralDataRepository.registerForDataLoadListener(new CentralDataRepository.DataReadyToSubmitListener() {
            @Override
            public void onDataSubmit(ArrayList<SectionModel> items) {

                pad.setText(pad.getText() + "\n" + "Reported with " + items.get(0).sectionTitle);

            }
        });


    }

    private void test(){

        findViewById(R.id.firstLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  initiating operation
                // for first time load -  test
                try {
                    centralDataRepository.submitAction(CentralDataRepository.FLAG_FIRST_LOAD, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {

                            Toast.makeText(TestAct.this, "Action Callback Flag Restore", Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }

            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  initiating operation
                // for first time load -  test
                try {
                    centralDataRepository.submitAction(CentralDataRepository.FLAG_RESTORE, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {

                            Toast.makeText(TestAct.this, "Action Callback Flag Restore", Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }

            }
        });

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  initiating operation
                // for search -  test
                // we need to set the search term to SP

                SharedPrefrenceUtils.getInstance(TestAct.this).setLastSearchTerm("Pawan Singh");

                try {
                    centralDataRepository.submitAction(CentralDataRepository.FLAG_SEARCH, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {

                            Toast.makeText(TestAct.this,"Action Callback Flag: Search",Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.refress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  initiating operation
                // for refress -  test
                // we need to set the search term to SP

                try {
                    centralDataRepository.submitAction(CentralDataRepository.FLAG_REFRESS, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {

                            Toast.makeText(TestAct.this,"Action Callback Flag: Refress",Toast.LENGTH_LONG).show();

                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
