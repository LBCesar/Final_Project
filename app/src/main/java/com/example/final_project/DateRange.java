package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class DateRange extends AppCompatActivity {
    private EditText firstDate;
    private EditText lastDate;
    private Button suBtn;
    ArrayList<String> alldates;
    ArrayList<Integer> myExp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_range);
        Intent intent = getIntent();

        alldates = intent.getStringArrayListExtra("ad");
        myExp = intent.getIntegerArrayListExtra("me");
        firstDate = findViewById(R.id.newName);
        lastDate = findViewById(R.id.newUsername);
        suBtn=findViewById(R.id.btnRegisterComplete);
        suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1 = firstDate.getText().toString();
                String date2 = lastDate.getText().toString();
                Intent intent = new Intent(getApplicationContext(), DashboardActivity1.class);
                intent.putExtra("d1",date1);
                intent.putExtra("d2",date2);
                intent.putIntegerArrayListExtra("me",myExp);
                intent.putStringArrayListExtra("ad",alldates);
                startActivity(intent);

            }
        });

    }
}