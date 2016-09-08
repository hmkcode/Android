package com.material.practice.socialsample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    ImageView proImage;
    TextView emailDisplay;
    ImageView editEmailImg, saveEmailImg;
    EditText editEmailBox,editNameBox;
    LinearLayout editHolder, displayHolder;

    Toolbar toolbar;

    TextView contactDisplay,username;
    ImageView editContactImg, saveContactImg,saveNameImg,editNameImg;
    EditText editContactBox;
    LinearLayout editcontactHolder, displayContactHolder ,displayNameHolder;

    TextView bioDisplay,nameDisplay;
    ImageView editBioImg, saveBioImg;
    EditText editBioBox;
    LinearLayout editBioHolder, displayBioHolder,editNameHolder;
    ProgressDialog progressDialog;
    RelativeLayout proImageLayout;


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

    private void changeImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
