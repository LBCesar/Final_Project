/*
    Cesar Gonzalez
    Shoraj Manandhar
    App: eTRACK
    Final Project: Expense Tracker
    2nd July, 2020
 */

package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
    MainActivity is the main Log in page after the splash screen.

    The user is able to login in to their account if their account is present
    in the database, else they are prompted to Signup a new account.
 */
public class MainActivity extends AppCompatActivity {

    // Initialize all the variables
    private Button btnRegister;
    private Button btnLogin;
    private EditText userName;
    private EditText password;
    private CheckBox cb;

    DBHelper mydb;
    int z = 0;
    int y = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb = findViewById(R.id.checkBox);
        userName = findViewById(R.id.txtUserName);
        password = findViewById(R.id.txtPassword);

        final String sss = date();

        mydb = new DBHelper(this);

        //logInSearch will look through the DB for a user who has chosen to remain logged in.
        //The search will return their userID.
        y = mydb.logInSearch();
        if (y != -1) {
            String datetest = mydb.lastLoginDate(y);
            //"2020-07-08", sss
            //If your last login date does not match today's date
            //We will reset all the item monetary values back to 0.
            if(!datetest.equals(sss)){
                mydb.itsANewDay(y, datetest);
                Toast.makeText(getApplicationContext(), "Values have been reset",
                        Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(MainActivity.this, DBMainActivity.class);
            intent.putExtra("itemid", y);
            startActivity(intent);
        }

        // go to DBMainActivity (the base/main activity) activity
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verify if the info entered is present in the database or not
                String s1 = userName.getText().toString();
                String s2 = password.getText().toString();
                int x = 0;
                Cursor res = mydb.verify2(s1, s2);
                if (res.getCount() > 0) {
                    res.moveToFirst();
                    while (!res.isAfterLast()) {
                        String pwd = res.getString(res.getColumnIndex(DBHelper.USERS_COLUMN_PASSWORD));
                        if (pwd.equals(s2)) {
                            x = res.getInt(res.getColumnIndex(DBHelper.USERS_COLUMN_ID));
                        }
                        res.moveToNext();
                    }
                }

                if (x != 0) {
                    Toast.makeText(getApplicationContext(), "Login Successful" + x,
                            Toast.LENGTH_LONG).show();
                    //If z==1 the user has chosen to remain logged in.
                    if (z == 1) {
                        mydb.setLogIn(x);
                    }

                    String datetest=mydb.lastLoginDate(x);
                    //"2020-07-08", sss
                    if(!datetest.equals(sss)){
                        mydb.itsANewDay(x,datetest);
                        Toast.makeText(getApplicationContext(), "Values have been reset",
                                Toast.LENGTH_LONG).show();
                    }

                    // Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    Intent intent = new Intent(MainActivity.this, DBMainActivity.class);
                    intent.putExtra("itemid", x);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "Cannot Login", Toast.LENGTH_SHORT).show();
            }
        });

        // enter register activity, signup a new account
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, Register.class);
                startActivity(register);
            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                z = 1;
            }
        });

    }   // end onCreate method

    /**
     * A method to return a String of Today's Date in yyyy-mm-dd format
     * using SimpleDateFormat library
     *
     * @return date
     */
    public String date() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(dNow);
    }


}   // end MainAcitivity class
