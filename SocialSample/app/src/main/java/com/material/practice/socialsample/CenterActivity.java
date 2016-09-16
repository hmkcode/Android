package com.material.practice.socialsample;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class CenterActivity extends AppCompatActivity {


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog= new ProgressDialog(this);

        username=(TextView)findViewById(R.id.username);
        proImage = (ImageView) findViewById(R.id.image);
        emailDisplay = (TextView) findViewById(R.id.Email);
        editEmailImg = (ImageView) findViewById(R.id.editEmailImage);
        saveEmailImg = (ImageView) findViewById(R.id.saveEmail);
        editEmailBox = (EditText) findViewById(R.id.emailEditBox);
        editHolder = (LinearLayout) findViewById(R.id.editEmailHolder);
        displayHolder = (LinearLayout) findViewById(R.id.displayEmailHolder);


        contactDisplay = (TextView) findViewById(R.id.Contact);
        editContactImg = (ImageView) findViewById(R.id.editContactImage);
        saveContactImg = (ImageView) findViewById(R.id.saveContact);
        saveNameImg= (ImageView) findViewById(R.id.saveName);
        editNameBox= (EditText) findViewById(R.id.nameEditBox);
        editContactBox = (EditText) findViewById(R.id.contactEditBox);
        editcontactHolder = (LinearLayout) findViewById(R.id.editContactHolder);
        displayContactHolder = (LinearLayout) findViewById(R.id.displayContactHolder);
        displayNameHolder= (LinearLayout) findViewById(R.id.displayNameHolder);
        nameDisplay = (TextView) findViewById(R.id.Name);
        bioDisplay = (TextView) findViewById(R.id.Bio);
        editBioImg = (ImageView) findViewById(R.id.editBioImage);
        saveBioImg = (ImageView) findViewById(R.id.saveBio);
        editNameImg= (ImageView) findViewById(R.id.editNameImage);

        editBioBox = (EditText) findViewById(R.id.bioEditBox);
        editNameBox= (EditText) findViewById(R.id.nameEditBox);

        editNameHolder= (LinearLayout) findViewById(R.id.editNameHolder);
        editBioHolder = (LinearLayout) findViewById(R.id.editBioHolder);
        displayBioHolder = (LinearLayout) findViewById(R.id.displayBioHolder);

        proImageLayout = (RelativeLayout) findViewById(R.id.changeImageBtn);

        proImageLayout.setOnClickListener(this);

        saveEmailImg.setOnClickListener(this);
        editEmailImg.setOnClickListener(this);

        saveContactImg.setOnClickListener(this);
        editContactImg.setOnClickListener(this);

        saveBioImg.setOnClickListener(this);
        editBioImg.setOnClickListener(this);

        saveNameImg.setOnClickListener(this);
        editNameImg.setOnClickListener(this);

        progressDialog.setMessage("Saving Back....");
        progressDialog.setIndeterminate(true);
    }

    public void initialize(){
        CollegareUser user= DatabaseManager.getInstance(this).getUser();

        username.setText(user.firstname+" "+user.lastname);
        emailDisplay.setText(user.email);
        contactDisplay.setText("000-000000-00");
        bioDisplay.setText("null for now");

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

            case R.id.changeImageBtn:
                changeImage();
                break;

            case R.id.saveEmail:
                editHolder.setVisibility(View.GONE);
                displayHolder.setVisibility(View.VISIBLE);
                emailDisplay.setText(editEmailBox.getText().toString());
                break;
            case R.id.editEmailImage:
                editHolder.setVisibility(View.VISIBLE);
                displayHolder.setVisibility(View.GONE);
                editEmailBox.setText(emailDisplay.getText().toString());
                break;

            case R.id.saveContact:
                editcontactHolder.setVisibility(View.GONE);
                displayContactHolder.setVisibility(View.VISIBLE);
                contactDisplay.setText(editContactBox.getText().toString());
                break;
            case R.id.editContactImage:
                editcontactHolder.setVisibility(View.VISIBLE);
                displayContactHolder.setVisibility(View.GONE);
                editContactBox.setText(contactDisplay.getText().toString());
                break;

            case R.id.saveBio:
                editBioHolder.setVisibility(View.GONE);
                displayBioHolder.setVisibility(View.VISIBLE);
                bioDisplay.setText(editBioBox.getText().toString());
                break;
            case R.id.editBioImage:
                editBioHolder.setVisibility(View.VISIBLE);
                displayBioHolder.setVisibility(View.GONE);
                editBioBox.setText(bioDisplay.getText().toString());
                break;

            case R.id.saveName:
                editNameHolder.setVisibility(View.GONE);
                displayNameHolder.setVisibility(View.VISIBLE);
                nameDisplay.setText(editNameBox.getText().toString());
                break;
            case R.id.editNameImage:
                editNameHolder.setVisibility(View.VISIBLE);
                displayNameHolder.setVisibility(View.GONE);
                editNameBox.setText(nameDisplay.getText().toString());
                break;
        }

    }
