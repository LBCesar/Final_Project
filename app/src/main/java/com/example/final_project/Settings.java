package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        ArrayAdapter myAdapter =  new ArrayAdapter<String>(this,R.layout.item_settings_recyclerview, settings_menu);
        ListView listView = findViewById(R.id.settingsListView);
        listView.setAdapter(myAdapter);
    }
}

