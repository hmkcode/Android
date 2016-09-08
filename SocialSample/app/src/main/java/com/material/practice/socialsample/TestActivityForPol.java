package com.material.practice.socialsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class TestActivityForPol extends AppCompatActivity {

    ListView op;
    PollOptionsAdapter adapter;
    ListView optionsListView;
    ImageView addBtn;
    PollOptionsEditListAdapter adapterEdit;
    EditText inputBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_card);




        op= (ListView) findViewById(R.id.Polloptions);
        adapter= new PollOptionsAdapter(this);
        adapter.addPollOption(new CollegarePollOption("12","Ankit Kumar","1"));
        adapter.addPollOption(new CollegarePollOption("5","Rajesh Kumar","1"));
        // these steps to be done in FeedsAdapter
        // only for test
        RelativeLayout lockHolder= (RelativeLayout) findViewById(R.id.lockHolder);
        final ImageView lock = (ImageView) findViewById(R.id.lock);
        lockHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getSelected()==-1)return;
                lock.setImageResource(R.drawable.lock_pressed);
                adapter.show();

            }
        });
        op.setAdapter(adapter);



/*

        optionsListView= (ListView) findViewById(R.id.pollOptionList);
        addBtn= (ImageView) findViewById(R.id.addBtn);
        adapterEdit = new PollOptionsEditListAdapter(this);
        inputBox= (EditText) findViewById(R.id.optionInputBox);
        optionsListView.setAdapter(adapterEdit);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("polltest", "" + inputBox.getText().toString());
                if (!inputBox.getText().toString().trim().isEmpty()) {
                    adapterEdit.add(inputBox.getText().toString());
                    inputBox.setText("");
                }
            }
        });



*/



   /*     lockHolder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            Log.e("PollT","down");
                            break;
                        case  MotionEvent.ACTION_UP:
                            Log.e("PollT","up");
                            break;
                        default:
                            break;
                    }
                return false;
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_poll_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
