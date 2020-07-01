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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DBMainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    int ourID;
    int xx;
    TextView expenseMain ;
    TextView nameMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbactivity_main);
        expenseMain =  findViewById(R.id.textView);
        nameMain =  findViewById(R.id.textView2);
        mydb = new DBHelper(this);
        int x=0;
        //txtName=findViewById(R.id.txtName);
        Intent intent=getIntent();
        //Retrieve the username from the main activity
        String message=intent.getStringExtra("first message");
        ourID=intent.getIntExtra("itemid",0);
        //Update the text on the welcome screen
        int o=mydb.getSumDaily(ourID,0,"2020-06-29");
        Toast.makeText(getApplicationContext(),"OUR SUM:"+o,
                Toast.LENGTH_SHORT).show();
        String ourName=mydb.getOurName(ourID);
        expenseMain.setText(expenseMain.getText().toString()+o);
        nameMain.setText(nameMain.getText().toString()+ourName);
        //ArrayList array_list = mydb.getAllCotacts();
        final ArrayList array_list = mydb.getAllItemsName(ourID);

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
                intent.putExtra("ourID",ourID);
                Toast.makeText(getApplicationContext(), array_list.get(id_To_Search-1).toString(),
                        Toast.LENGTH_SHORT).show();
                Cursor u=mydb.getItemID(ourID,array_list.get(id_To_Search-1).toString());
                if(u.getCount() > 0) {
                   u.moveToFirst();
                    while (!u.isAfterLast()) {

                        xx = u.getInt(u.getColumnIndex(DBHelper.ITEMS_COLUMN_ID));
                        Toast.makeText(getApplicationContext(), "In Main xx"+xx,
                                Toast.LENGTH_SHORT).show();
                        u.moveToNext();
                    }
                }
                intent.putExtra("itemid",xx);
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
            case R.id.item1:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);
                intent.putExtra("ourID",ourID);
                intent.putExtra("itemid",xx);
                startActivity(intent);
                return true;

            case R.id.item3:
                Intent intent2 = new Intent(getApplicationContext(), DashboardActivity.class);
                intent2.putExtra("ourID",ourID);
                intent2.putExtra("itemid",xx);
                startActivity(intent2);
                return true;

             case R.id.item5:
                 Intent intent5 = new Intent(getApplicationContext(), Settings.class);
                 startActivity(intent5);

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
