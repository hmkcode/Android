package com.material.practice.socialsample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

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

    private void saveChanges(){

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri Selectionuri = data.getData();
            String[] pathCols = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(Selectionuri, pathCols, null, null, null);
            cursor.moveToFirst();
            final String path = cursor.getString(cursor.getColumnIndex(pathCols[0]));
            cursor.close();
            String imgStr= getStringImage(BitmapFactory.decodeFile(path));
            Bitmap bmp=null;

            // cross-test for decode and encode
            byte[] barr= Base64.decode(imgStr,Base64.DEFAULT);
            bmp= BitmapFactory.decodeByteArray(barr, 0,barr.length);
            // end


            proImage.setImageBitmap(bmp);
            // proImage.setImageBitmap(BitmapFactory.decodeFile(path));

            if(InternetManager.getInstance(this).isConnectedToNet()){
                Log.e("EP", "trying upload");
                Handler handler= new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("EP", "thread dispatched");
                        ImageUploader.getInstance(new Contexter().getContext()).upload(BitmapFactory.decodeFile(path));
                    }
                });

            }

        }

    }
}