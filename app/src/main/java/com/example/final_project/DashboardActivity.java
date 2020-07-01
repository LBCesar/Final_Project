package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.final_project.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        int ourID;
        // all this is default stuff upon creating tab activity
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        mydb = new DBHelper(this);

        Intent intent = getIntent();
        ourID=intent.getIntExtra("ourID",0);
        Toast.makeText(getApplicationContext(), "Nothing"+ourID,
                Toast.LENGTH_SHORT).show();
        ArrayList<String> a=mydb.getAllItemsName(ourID);
        ArrayList<String> b=mydb.getAllExpenses(ourID);
        if(a.size()>0) {
            Toast.makeText(getApplicationContext(), "======" + a.get(0),
                    Toast.LENGTH_SHORT).show();
            if(b.size()>0) {
                Toast.makeText(getApplicationContext(), "=======" + b.get(0),
                        Toast.LENGTH_SHORT).show();
            }
        }


    }


}