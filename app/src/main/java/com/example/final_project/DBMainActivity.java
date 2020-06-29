package com.example.final_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DBMainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbactivity_main);



        mydb = new DBHelper(this);
        int x=0;
        mydb.insertUser(0,"cesar","123","Cesar",1000);
        mydb.insertUser(0,"shoraj","123","Shoraj",1253);

        mydb.insertItem(0,1,"Groceries","Food and stuff");
        mydb.insertItem(0,1,"Book","This is a book");
        mydb.insertItem(0,1,"Ice Cream","Mint chocolate chip");
        mydb.insertItem(0,2,"Book","Book number 2");
        mydb.insertItem(0,2,"Movie Theater","Saw Paul Blart mall cop");

        mydb.insertExpenses(1,1,1,55555,"2020-06-28");
        mydb.insertExpenses(1,1,2,50,"2020-06-28");
        mydb.insertExpenses(1,1,3,1000,"2020-06-28");
       // mydb.insertUser(0,"cesar","cesar123","Cesar",430);
        //mydb.insertUser(0,"shoraj","shoraj123","Shoraj",5);
        //int x=mydb.verify("cesar","cesar123");
        /*
       Cursor res=mydb.verify2("cesar","cesar123");
        if(res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                x = res.getInt(res.getColumnIndex(DBHelper.USERS_COLUMN_ID));
                res.moveToNext();
            }
        }
        Toast.makeText(getApplicationContext(), "Login Successful"+x,
                Toast.LENGTH_LONG).show();



         */

        //ArrayList array_list = mydb.getAllCotacts();
        ArrayList array_list = mydb.getAllItemsName();

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {
                // TODO Auto-generated method stub
                int id_To_Search = position + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dbmain_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
