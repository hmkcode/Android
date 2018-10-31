package com.example.pallavibhandari.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class Date extends AppCompatActivity{
    DatePicker myDatePicker;
    Button DateIsSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        myDatePicker=findViewById(R.id.myDatePicker);
        DateIsSelected=findViewById(R.id.DateIsSelected);
        DateIsSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dayofMonth,month,year;
                dayofMonth=myDatePicker.getDayOfMonth();
                month=myDatePicker.getMonth();
                year=myDatePicker.getYear();
            }
        });
    }
}
