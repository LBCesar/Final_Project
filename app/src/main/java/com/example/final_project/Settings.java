package com.example.final_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings  extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent5 = getIntent();

        ArrayList<String> settings_menu = new ArrayList<>();
        settings_menu.add("Update User Info");
        settings_menu.add("Help");
        settings_menu.add("About");
        settings_menu.add("Logout");

        ArrayAdapter myAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings_menu);
        ListView listView = findViewById(R.id.settingsListView);
        listView.setAdapter(myAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Toast.makeText(Settings.this, "update the database", Toast.LENGTH_SHORT).show();
                }
                if (position == 1){
                    Intent intentHelp = new Intent(getApplicationContext(), Help.class);
                    startActivity(intentHelp);
                }
                if (position == 2){
                    Intent intentAbout = new Intent(getApplicationContext(), About.class);
                    startActivity(intentAbout);
                }
                if (position == 3){
                    Toast.makeText(Settings.this, "Log out", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

