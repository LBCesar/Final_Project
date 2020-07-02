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
/*
    New Users are able to register for a new accounts.
    Along with Name, Username, Password & Confirm Password, they are also
    required to enter their Savings Goal, Maximum Daily Expense and
    their Annual Income during registration.

    Using AwesomeValidation to check for Invalid inputs
    https://github.com/thyrlian/AwesomeValidation
 */
public class Register extends AppCompatActivity {

    private EditText name;
    private EditText uName;
    private EditText pwd;
    private EditText pwd2;

    private EditText savingsGoal;
    private EditText newBudget;
    private EditText annual;
    private Button suBtn;

    DBHelper mydb;
    String currentDate=date();

    AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    private final String RegexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{5,}";
    String RegexNumber = "[/^\\d+\\.?\\d*$/]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.newName);
        uName = findViewById(R.id.newUsername);
        pwd = findViewById(R.id.newPassword);
        pwd2 = findViewById(R.id.newRePassword);
        savingsGoal = findViewById(R.id.newSavings);
        newBudget = findViewById(R.id.newAnnual);
        annual = findViewById(R.id.newAnnual2);
        mydb = new DBHelper(this);

        suBtn=findViewById(R.id.btnRegisterComplete);
//        Intent register =  getIntent();

//        enable the AwesomeValidation comments for the registration
        mAwesomeValidation.addValidation(Register.this, R.id.newName, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(Register.this, R.id.newUsername, "[a-zA-Z\\s]+", R.string.err_username);
        mAwesomeValidation.addValidation(Register.this, R.id.newPassword, RegexPassword,  R.string.err_password);

        mAwesomeValidation.addValidation(Register.this, R.id.newRePassword, RegexPassword,  R.string.err_password);
        mAwesomeValidation.addValidation(Register.this, R.id.newRePassword, R.id.newPassword, R.string.err_repassword);

        mAwesomeValidation.addValidation(Register.this, R.id.newSavings, "[/^\\d+\\.?\\d*$/]+", R.string.err_savings_goal);
        mAwesomeValidation.addValidation(Register.this, R.id.newAnnual, "[/^\\d+\\.?\\d*$/]+", R.string.err_daily_expense);
        mAwesomeValidation.addValidation(Register.this, R.id.newAnnual2, "[/^\\d+\\.?\\d*$/]+", R.string.err_annual_income);


        suBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAwesomeValidation.validate()) {        // turn this on for awesome validation. And bottom too
                    Toast toast = Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG);
                    toast.show();

                    //Creating a new user, and filling it with data.
                    //UserData user = new UserData();
                    String name1 = name.getText().toString();
                    String name2 = uName.getText().toString();
                    String pwd1 = pwd.getText().toString();
                    String save1 = savingsGoal.getText().toString();
                    int save2 = Integer.parseInt(save1);
                    String annual1 = newBudget.getText().toString();
                    int annual2 = Integer.parseInt(annual1);
                    String annualIncome = annual.getText().toString();
                    int annual3 = Integer.parseInt(annualIncome);

                    //Add the new user to the hashmap
                    //credentials.put(user.name,user);
                    //mydb.in
                //budget annual savings.
                    mydb.insertUser(0, name2, pwd1, name1,  annual2,save2, annual3, 0, currentDate);
                    int x = 0;
                    Cursor res = mydb.verify2(name2, pwd1);

                    if (res.getCount() > 0) {
                        res.moveToFirst();
                        while (!res.isAfterLast()) {
                            // Do whatever you like with the result.
                            String pwd = res.getString(res.getColumnIndex(DBHelper.USERS_COLUMN_PASSWORD));
                            if (pwd.equals(pwd1)) {
                                //res.close();
                                //res.getInt(res.g)
                                x = res.getInt(res.getColumnIndex(DBHelper.USERS_COLUMN_ID));
                            }
                            res.moveToNext();
                        }
                    }

                    Toast toast2 = Toast.makeText(getApplicationContext(), "Current Date Register: " + currentDate, Toast.LENGTH_LONG);
                    toast2.show();

                    mydb.insertItem(0, x, "Groceries", "Food and stuff", 0, currentDate);
                    mydb.insertItem(0, x, "Book", "This is a book", 0, currentDate);
                    mydb.insertItem(0, x, "Ice Cream", "Mint chocolate chip", 0, currentDate);
                    mydb.insertItem(0, x, "Movie Theater", "Saw Paul Blart mall cop", 0, currentDate);


                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //Send the hash map back to the main activity
                    //i.putExtra("data", credentials);
                    startActivity(i);
                }
                else
                    Toast.makeText(Register.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            }

        });

    }   // end onCreate method


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

