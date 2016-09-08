package com.material.practice.socialsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Poll extends AppCompatActivity {

    ListView pollOptionListView;
    Button doneBtn,addBtn;
    EditText pollOptionsInputBox;
    PollOptionsEditListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);
        pollOptionListView = (ListView) findViewById(R.id.pollOptionList);
        doneBtn = (Button) findViewById(R.id.doneBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        pollOptionsInputBox = (EditText) findViewById(R.id.optionInputBox);
        adapter = new PollOptionsEditListAdapter(this);
        pollOptionListView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Pc", "poll option addition");
                adapter.add(pollOptionsInputBox.getText().toString());
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_poll, menu);
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
