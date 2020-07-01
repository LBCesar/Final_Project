package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Register extends AppCompatActivity {
    private EditText name;
    private EditText uName;
    private EditText pwd;
    private EditText pwd2;
    private EditText email;
    private EditText phone;
    private EditText annual;
    private Button suBtn;
    DBHelper mydb;
    String currentDate=date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.newName);
        uName=findViewById(R.id.newUsername);
        pwd=findViewById(R.id.newPassword);
        pwd2=findViewById(R.id.newRePassword);
        email=findViewById(R.id.newSavings);
        phone=findViewById(R.id.newAnnual);
        annual=findViewById(R.id.newAnnual2);
        mydb = new DBHelper(this);

        suBtn=findViewById(R.id.btnRegisterComplete);
        suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast toast=Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG);
                    toast.show();
                    String name1=name.getText().toString();
                    String name2=uName.getText().toString();
                    String pwd1=pwd.getText().toString();
                    String save1=email.getText().toString();
                    int save2=Integer.parseInt(save1);
                    String annual1=phone.getText().toString();
                    int annual2=Integer.parseInt(annual1);
                    String annualIncome=annual.getText().toString();
                    int annual3=Integer.parseInt(annualIncome);
                mydb.insertUser(0,name2,pwd1,name1,annual2,annual3,save2,0,currentDate);
                int x=0;
                Cursor res =mydb.verify2(name2,pwd1);
                if(res.getCount() > 0) {
                    res.moveToFirst();
                    while (!res.isAfterLast()) {
                        // Do whatever you like with the result.
                        String pwd = res.getString(res.getColumnIndex(DBHelper.USERS_COLUMN_PASSWORD));
                        if (pwd.equals(pwd1)) {
                            x= res.getInt(res.getColumnIndex(DBHelper.USERS_COLUMN_ID));
                        }
                        res.moveToNext();
                    }
                }
                Toast toast2=Toast.makeText(getApplicationContext(), "Current Date Register: "+currentDate, Toast.LENGTH_LONG);
                toast2.show();

                mydb.insertItem(0,x,"Groceries","Food and stuff",0,currentDate);
                mydb.insertItem(0,x,"Book","This is a book",0,currentDate);
                mydb.insertItem(0,x,"Ice Cream","Mint chocolate chip",0,currentDate);
                mydb.insertItem(0,x,"Movie Theater","Saw Paul Blart mall cop",0,currentDate);


                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
            }
        });
    }
    public int helperID(Cursor res) {
        int x = 0;

        if (res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                x = res.getInt(res.getColumnIndex(DBHelper.ITEMS_COLUMN_ID));
                res.moveToNext();
            }
        }
        return x;
    }
    public String date() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(dNow);
    }
}

