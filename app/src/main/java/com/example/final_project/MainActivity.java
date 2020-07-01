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

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;
    private Button btnLogin;

    private EditText userName;
    private EditText password;
    private CheckBox cb;
    DBHelper mydb;
    int z=0;
    int y=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cb = findViewById(R.id.checkBox);
        userName=findViewById(R.id.txtUserName);
        password=findViewById(R.id.txtPassword);
        mydb = new DBHelper(this);
        y=mydb.logInSearch();
        if(y!=-1){
            Intent intent = new Intent(MainActivity.this, DBMainActivity.class);
            intent.putExtra("itemid",y);
            startActivity(intent);
        }

        // go to dashboard activity
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=userName.getText().toString();
                String s2=password.getText().toString();
               // int x= mydb.verify(s1,s2);
                int x=0;
                Cursor res =mydb.verify2(s1,s2);
                if(res.getCount() > 0) {
                    res.moveToFirst();
                    while (!res.isAfterLast()) {
                        // Do whatever you like with the result.
                        String pwd = res.getString(res.getColumnIndex(DBHelper.USERS_COLUMN_PASSWORD));
                        if (pwd.equals(s2)) {
                            //res.close();
                            //res.getInt(res.g)
                            x= res.getInt(res.getColumnIndex(DBHelper.USERS_COLUMN_ID));
                        }
                        res.moveToNext();
                    }
                }

                if(x!=0) {
                    Toast.makeText(getApplicationContext(), "Login Successful" + x,
                            Toast.LENGTH_LONG).show();
                    if(z==1){
                        mydb.setLogIn(x);
                    }
                    //Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    Intent intent = new Intent(MainActivity.this, DBMainActivity.class);
                    intent.putExtra("itemid",x);
                    startActivity(intent);
                }
            }
        });

        // enter register activity
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
                z=1;
            }
        });


    }


}
