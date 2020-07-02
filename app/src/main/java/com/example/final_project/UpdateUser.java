package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateUser extends AppCompatActivity {
    private EditText name;
    private EditText uName;
    private EditText pwd;
    private EditText pwd2;
    private EditText email;
    private EditText phone;
    private EditText annual;
    private Button suBtn;

    DBHelper mydb;
    int ourID;
    String currentDate=date();

    // https://github.com/thyrlian/AwesomeValidation
    AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    private final String RegexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{5,}";
    String RegexNumber = "[/^\\d+\\.?\\d*$/]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Intent intent=getIntent();

        // Retrieve the username from the main activity
        String message = intent.getStringExtra("first message");
        ourID = intent.getIntExtra("itemid",0);

        name = findViewById(R.id.newName);
        uName = findViewById(R.id.newUsername);
        pwd = findViewById(R.id.newPassword);
        pwd2 = findViewById(R.id.newRePassword);
        email = findViewById(R.id.newSavings);
        phone = findViewById(R.id.newAnnual);
        annual = findViewById(R.id.newAnnual2);
        mydb = new DBHelper(this);

        suBtn=findViewById(R.id.btnRegisterComplete);
//        Intent register =  getIntent();


//        mAwesomeValidation.addValidation(Register.this, R.id.newName, "[a-zA-Z\\s]+", R.string.err_name);
//        mAwesomeValidation.addValidation(Register.this, R.id.newUsername, "[a-zA-Z\\s]+", R.string.err_username);
//        mAwesomeValidation.addValidation(Register.this, R.id.newPassword, RegexPassword,  R.string.err_password);
//
//        mAwesomeValidation.addValidation(Register.this, R.id.newRePassword, RegexPassword,  R.string.err_password);
//        mAwesomeValidation.addValidation(Register.this, R.id.newRePassword, R.id.newPassword, R.string.err_repassword);
//
//        mAwesomeValidation.addValidation(Register.this, R.id.newSavings, "[/^\\d+\\.?\\d*$/]+", R.string.err_savings_goal);
//        mAwesomeValidation.addValidation(Register.this, R.id.newAnnual, "[/^\\d+\\.?\\d*$/]+", R.string.err_daily_expense);
//        mAwesomeValidation.addValidation(Register.this, R.id.newAnnual2, "[/^\\d+\\.?\\d*$/]+", R.string.err_annual_income);


        suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (mAwesomeValidation.validate()) {        // turn this on for awesome validation. And bottom too
                Toast toast = Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_LONG);
                toast.show();

                //Creating a new user, and filling it with data.
                //UserData user = new UserData();
                String name1 = name.getText().toString();
                String name2 = uName.getText().toString();
                String pwd1 = pwd.getText().toString();
                String save1 = email.getText().toString();
                int save2 = Integer.parseInt(save1);
                String annual1 = phone.getText().toString();
                int annual2 = Integer.parseInt(annual1);
                String annualIncome = annual.getText().toString();
                int annual3 = Integer.parseInt(annualIncome);

                //Add the new user to the hashmap
                //credentials.put(user.name,user);
                //mydb.insertUser(0, name2, pwd1, name1, save2, annual2, annual3, 0, currentDate);
                mydb.updateUser(ourID, name2, pwd1, name1, annual2,  save2,annual3, 0, " ");
                int x = 0;
                Cursor res = mydb.verify2(name2, pwd1);

                if (res.getCount() > 0) {
                    res.moveToFirst();
                    while (!res.isAfterLast()) {
                        String pwd = res.getString(res.getColumnIndex(DBHelper.USERS_COLUMN_PASSWORD));
                        if (pwd.equals(pwd1)) {
                            x = res.getInt(res.getColumnIndex(DBHelper.USERS_COLUMN_ID));
                        }
                        res.moveToNext();
                    }
                }

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

        });

    }   // end onCreate method

    public String date() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(dNow);
    }

}