package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
//        Intent register =  getIntent();
        suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if(valid.validate()){
                    Toast toast=Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG);
                    toast.show();
                    //Creating a new user, and filling it with data.
                    //UserData user = new UserData();
                    String name1=name.getText().toString();
                    String name2=uName.getText().toString();
                    String pwd1=pwd.getText().toString();
                    String save1=email.getText().toString();
                    int save2=Integer.parseInt(save1);
                    String annual1=phone.getText().toString();
                    int annual2=Integer.parseInt(annual1);
                    String annualIncome=annual.getText().toString();
                    int annual3=Integer.parseInt(annualIncome);
                    //Add the new user to the hashmap
                    //credentials.put(user.name,user);
                mydb.insertUser(0,name2,pwd1,name1,save2,annual2,annual3,0);
                int x=0;
                Cursor res =mydb.verify2(name2,pwd1);
                if(res.getCount() > 0) {
                    res.moveToFirst();
                    while (!res.isAfterLast()) {
                        // Do whatever you like with the result.
                        String pwd = res.getString(res.getColumnIndex(DBHelper.USERS_COLUMN_PASSWORD));
                        if (pwd.equals(pwd1)) {
                            //res.close();
                            //res.getInt(res.g)
                            x= res.getInt(res.getColumnIndex(DBHelper.USERS_COLUMN_ID));
                        }
                        res.moveToNext();
                    }
                }

                mydb.insertItem(0,x,"Groceries","Food and stuff",123,"2002-10-26");
                mydb.insertItem(0,x,"Book","This is a book",456,"2002-10-26");
                mydb.insertItem(0,x,"Ice Cream","Mint chocolate chip",789,"2002-10-26");
                mydb.insertItem(0,x,"Movie Theater","Saw Paul Blart mall cop",123,"2002-10-26");


                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    //Send the hash map back to the main activity
                    //i.putExtra("data", credentials);
                    startActivity(i);
               // }
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

    }

