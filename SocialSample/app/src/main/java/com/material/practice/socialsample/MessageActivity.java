package com.material.practice.socialsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MessageActivity extends AppCompatActivity {

    String remote_username;
    String user_id;
    RecyclerView msg_thread;
    MessageRoomAdapter adapter;
    Toolbar toolbar;
    TextView chat_box_username , chat_box_online_status;
    FrameLayout chat_send_btn;
    EditText chat_input;
    MessageLayoutManager messageLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_room);

        toolbar = (Toolbar) findViewById(R.id.chat_room_toolbar);
        chat_box_username = (TextView) toolbar.findViewById(R.id.chat_box_username);
        chat_box_online_status = (TextView) toolbar.findViewById(R.id.chat_box_online_status);


        chat_input = (EditText) findViewById(R.id.chat_input_box);
        chat_send_btn = (FrameLayout) findViewById(R.id.chat_send_btn);
        user_id = getIntent().getExtras().getString("user_id");
        msg_thread = (RecyclerView) findViewById(R.id.msgRoomMessageRV);
        adapter = MessageRoomAdapter.getInstance(this);
        messageLayoutManager = new MessageLayoutManager(this);
        msg_thread.setLayoutManager(messageLayoutManager);
        msg_thread.setAdapter(adapter);
        chat_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add the chat content to msg send handler
                String chat = chat_input.getText().toString();
                Date d = new Date();
                String doc = DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime()).toString();
                String timeStamp = DateFormat.format("yyyyMMddhhmmss", d.getTime()).toString();
                CollegareMessage msg = new CollegareMessage(timeStamp, chat, "Me", doc, "201451065", user_id, remote_username, "true", "S", "false");
                DatabaseManager.getInstance(MessageRoom.this).appendMessage(msg);

                // current timestamp as task id

                String taskID = "tsk" + timeStamp;
                Log.e("MessageRoom", " new task " + taskID);

                SessionManager.setTasksSequence(SessionManager.getTaskSequence() + "#" + taskID);
                Log.e("MessageRoom", "curr task seq " + SessionManager.getTaskSequence());
                DatabaseManager.getInstance(MessageRoom.this).addTask(new CollegareTask(taskID, chat, user_id, timeStamp));

                chat_input.setText("");
            }
        });
        Log.e("MessageR", "registering for msg add");
        DatabaseManager.getInstance(this).setOnNewMessageAdditionListener(new DatabaseManager.NewMessageListener() {
            @Override
            public void onMessageAdd(String userID) {
                load();
            }
        });
        Log.e("MessageR", "registering for msg sent");
        DatabaseManager.getInstance(this).setOnMessageSentListener(new DatabaseManager.MessageSentListener() {
            @Override
            public void onMessageSent() {
                Log.e("MR", "messageSentListener call");
                load();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        load();
        markThemAsRead();
    }



    public void load(){
        ArrayList<CollegareMessage> msgs = DatabaseManager.getInstance(this).getMessages(user_id);
        CollegareContact contact = DatabaseManager.getInstance(this).getContacts(user_id);

        if(msgs.size()>0){
            remote_username = (msgs.get(0).type.equals("S"))?msgs.get(0).receiver_name:msgs.get(0).sender_name;
            chat_box_username.setText(remote_username);
            adapter.setMessageDataList(msgs);
            msg_thread.smoothScrollToPosition(msgs.size()-1);
        }else{
            remote_username = contact.username;
            chat_box_username.setText(contact.username);
        }

        chat_box_online_status.setText("online");

    }

    public void markThemAsRead(){
        DatabaseManager.getInstance(this).setMessageRead(user_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_room, menu);
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