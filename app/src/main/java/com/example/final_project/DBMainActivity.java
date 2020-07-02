/*
    Cesar Gonzalez
    Shoraj Manandhar
    App: eTRACK
    Final Project: Expense Tracker
    2nd July, 2020
 */

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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class DBMainActivity extends AppCompatActivity {

    // Initialize all the required variables
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    private DBHelper mydb;

    private TextView expenseMain ;
    private TextView savingsGoal;
    private TextView nameMain;
    private TextView txtDailySavings;
    int ourID;
    int xx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbactivity_main);

        expenseMain =  findViewById(R.id.textView);
        savingsGoal = findViewById(R.id.txtSavingsGoal);
        nameMain =  findViewById(R.id.textView2);
        txtDailySavings = findViewById(R.id.txtDailySavings);

        mydb = new DBHelper(this);
        int x = 0;

        // Retrieve the username from the MainActivity
        Intent intent = getIntent();
        String message = intent.getStringExtra("first message");
        ourID = intent.getIntExtra("itemid",0);

        // Update the text on the welcome screen
        String todayDate = date();
        int o = mydb.getSumDaily(ourID,0, todayDate);

        // show the user's name
        String ourName = mydb.getOurName(ourID);
        nameMain.setText(nameMain.getText().toString() + ourName);

        String ourGoal = mydb.getOurIncome(ourID);
        String ourGoal2 = mydb.getOurIncome(ourID);

//        this is income
        String myIncome = mydb.getOurGoal(ourID);
        Double dailyExp = (Double.valueOf(myIncome)/365) - o;
        Double d1=Double.valueOf(ourGoal);
        Double d2=Double.valueOf(o);
        Double dailyExp2=(d1-d2);
        String result = currencyFormat(String.valueOf(Double.toString(dailyExp)));
        String todaysExpense = currencyFormat(Integer.toString(o));
        expenseMain.setText(expenseMain.getText().toString() + "$" + todaysExpense);

        ourGoal = currencyFormat(ourGoal);
        savingsGoal.setText(savingsGoal.getText().toString() + " $" + ourGoal2);

        String result2 = currencyFormat(String.valueOf(Double.toString(dailyExp2)));
        txtDailySavings.setText(txtDailySavings.getText().toString() + " $" + result2);

        //String dummyDate="2020-07-2";
        final ArrayList array_list = mydb.getAllItemsNameWithDate(ourID,todayDate);
        ArrayList array_list2= new ArrayList();
        final ArrayList lastAL=new ArrayList();

        for(int i=0;i<array_list.size();i++){
            float here=mydb.getAllExpensesForOneDayAndOneItem(ourID,array_list.get(i).toString(),todayDate);
            String formating=currencyFormat(String.valueOf(here));
            array_list2.add(i,formating);
            lastAL.add(i,array_list.get(i).toString()+"    "+array_list2.get(i));
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, lastAL);
        obj = findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {
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

            case R.id.item3:    // report 1 pie chart
                Intent intent2 = new Intent(getApplicationContext(), DashboardActivity.class);
                ArrayList<String> allitems=new ArrayList<>();
                ArrayList<Integer> totalSumforItem=new ArrayList<>();

                allitems=mydb.getAllItemsName(ourID);
                for(int i=0;i<allitems.size();i++){
                    int u=mydb.sumAllExpensesForAnItem(ourID,allitems.get(i));
                    totalSumforItem.add(i,u);
                }

                intent2.putStringArrayListExtra("ai",allitems);
                intent2.putIntegerArrayListExtra("tsfi",totalSumforItem);
                intent2.putExtra("ourID",ourID);
                intent2.putExtra("itemid",xx);
                startActivity(intent2);
                return true;

            case R.id.item6:    // report 2 line graph
                Intent intentx = new Intent(getApplicationContext(), DashboardActivity1.class);
                Toast.makeText(getApplicationContext(), "Nothing"+ ourID,
                        Toast.LENGTH_SHORT).show();
                // ArrayList<String> a=mydb.getAllItemsName(ourID);
                //ArrayList<String> b=mydb.getAllExpenses(ourID);
                ArrayList<String> alldates=new ArrayList<String>();
                alldates=mydb.getAllDates(ourID);

                java.util.Set<String> set2 = new HashSet<>(alldates);
                alldates.clear();
                alldates.addAll(set2);
                //        int y=mydb.getSumDaily(ourID,0,alldates.get(0));
                ArrayList<Integer> myExp=new ArrayList<Integer>();
                //if(alldates.size()>0) {
                    for (int i = 0; i < alldates.size(); i++) {
                        if(alldates.get(i)!=null) {
                            String w=alldates.get(i);
                            int w2=mydb.getSumDaily(ourID, 0, w);
                           // myExp.set(i, w2);
                            myExp.add(i,w2);
                        }
                    }
                //}
                if(myExp.size()>0) {
                    Toast.makeText(getApplicationContext(), "======LLL"+myExp.get(0),
                            Toast.LENGTH_SHORT).show();
                }
                if(alldates.size()>0) {
                    Toast.makeText(getApplicationContext(), "======" + alldates.get(0),
                            Toast.LENGTH_SHORT).show();
                }
                intentx.putStringArrayListExtra("ad",alldates);
                intentx.putIntegerArrayListExtra("me",myExp);
                intentx.putExtra("ourID",ourID);
                intentx.putExtra("itemid",xx);
                startActivity(intentx);
                return true;

            case R.id.item7: // report 3

                Intent intent7 = new Intent(getApplicationContext(), DashboardActivity2.class);
                ArrayList<String> alldates2=new ArrayList<String>();
                alldates2=mydb.getAllDates(ourID);

                java.util.Set<String> set3 = new HashSet<>(alldates2);
                alldates2.clear();
                alldates2.addAll(set3);
                Toast.makeText(getApplicationContext(), "======" ,
                        Toast.LENGTH_SHORT).show();
                if(alldates2.size()>0) {
                    Toast.makeText(getApplicationContext(), "======" + alldates2.get(0),
                            Toast.LENGTH_SHORT).show();
                }
                ArrayList<Integer> myExp2=new ArrayList<Integer>();
                //if(alldates.size()>0) {
                for (int i = 0; i < alldates2.size(); i++) {
                    if(alldates2.get(i)!=null) {
                        String w=alldates2.get(i);
                        int w2=mydb.getSumDaily(ourID, 0, w);
                        // myExp.set(i, w2);
                        myExp2.add(i,w2);
                    }
                }
                //}
                if(myExp2.size()>0) {
                    Toast.makeText(getApplicationContext(), "======LLL"+myExp2.get(0),
                            Toast.LENGTH_SHORT).show();
                }
                if(alldates2.size()>0) {
                    Toast.makeText(getApplicationContext(), "======" + alldates2.get(0),
                            Toast.LENGTH_SHORT).show();
                }
                //intentx.putStringArrayListExtra("ad",alldates);
                int annaulINC=mydb.getAnnualIncome(ourID);
                ArrayList<Integer> mySave= new ArrayList<Integer>();
                for (int i = 0; i < alldates2.size(); i++) {
                    if(alldates2.get(i)!=null) {
                        //d. Daily Saving = ((annual income)/365) – (Daily expense)
                        mySave.add(i,((annaulINC)/365)-myExp2.get(i));
                    }
                }
                intent7.putIntegerArrayListExtra("me",myExp2);
                intent7.putIntegerArrayListExtra("ms",mySave);
                intent7.putStringArrayListExtra("ad",alldates2);
                intent7.putExtra("ourID",ourID);
                intent7.putExtra("itemid",xx);
                startActivity(intent7);
                return true;

            case R.id.item4:
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                //intent2.putExtras(dataBundle);
                mydb.logOut(ourID);
                intent3.putExtra("ourID",ourID);
                intent3.putExtra("itemid",xx);
                startActivity(intent3);
                return true;

             case R.id.item5:
                 Intent intent5 = new Intent(getApplicationContext(), Settings.class);
                 intent5.putExtra("ourID", ourID);
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

    public String date() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(dNow);
    }

    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }


}
