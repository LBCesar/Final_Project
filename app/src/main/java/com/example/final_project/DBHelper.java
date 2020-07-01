package com.example.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName2.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;

    public static final String USERS_TABLE_NAME="users";
    public static final String USERS_COLUMN_ID="userid";
    public static final String USERS_COLUMN_USERNAME="username";
    public static final String USERS_COLUMN_PASSWORD="password";
    public static final String USERS_COLUMN_NAME="name";
    public static final String USERS_COLUMN_BUDGET="budget";
    public static final String USERS_COLUMN_ANNUAL="annual";
    public static final String USERS_COLUMN_SAVINGS="savings";
    public static final String USERS_COLUMN_LOG="log";
    public static final String USERS_COLUMN_DATE="date";


    public static final String ITEMS_TABLE_NAME="items";
    public static final String ITEMS_COLUMN_ID="itemid";
    public static final String ITEMS_COLUMN_USER_ID="userid";
    public static final String ITEMS_COLUMN_ITEM="item";
    public static final String ITEMS_COLUMN_DESCRIPTION="description";

    public static final String EXPENSES_TABLE_NAME="expenses";
    public static final String EXPENSES_COLUMN_ID="expensesid";
    public static final String EXPENSES_COLUMN_USER_ID="userid";
    public static final String EXPENSES_COLUMN_ITEM_ID="itemid";
    public static final String EXPENSES_COLUMN_PRICE="price";
    public static final String EXPENSES_COLUMN_date="date";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
        db.execSQL(
                "create table expenses"+
                        "(expensesid integer primary key AUTOINCREMENT NOT NULL,userid integer,itemid integer,price integer,date text)"
        );
        db.execSQL(
                "create table users"+
                        "(userid integer primary key AUTOINCREMENT NOT NULL,username text,password text,name text,budget integer" +
                        ",annual integer,savings integer,log integer,date String)"
        );
        //income (annually), maximum daily expense, and the amount of savin
        db.execSQL(
                "create table items"+
                        "(itemid integer primary key AUTOINCREMENT NOT NULL,userid integer,item text,description text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS items");
        db.execSQL("DROP TABLE IF EXISTS expenses");
        onCreate(db);
    }
    //The insert___ family is for inserting a new row into the table
    //This is not for updating existing values.
    public boolean insertExpenses (int expensesid, int userid, int itemid, int price,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("expensesid", expensesid);
        contentValues.put("userid", userid);
        contentValues.put("itemid", itemid);
        contentValues.put("price", price);
        contentValues.put("date", date);
        db.insert("expenses", null, contentValues);
        return true;
    }
    public boolean insertItem (int itemid, int userid, String item, String description, int price,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("itemid", itemid);
        contentValues.put("userid", userid);
        contentValues.put("item", item);
        contentValues.put("description", description);
        long id=db.insert("items", null, contentValues);
        insertExpenses(0,userid,(int)id,price,date);
        return true;
    }

    public boolean insertUser (int userid, String username, String password, String name,int budget,int annual,int savings, int log,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("userid", userid);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("budget", budget);
        contentValues.put("annual", annual);
        contentValues.put("savings", savings);
        contentValues.put("log", log);
        contentValues.put("date", date);
        db.insert("users", null, contentValues);
        return true;
    }
    public Cursor verify2(String username2,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from users where username= ?", new String[] { username2 } );
        return res;

    }
    public int verify(String username2,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where username="+ username2+"", null );
        if(res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                // Do whatever you like with the result.
                String pwd = res.getString(res.getColumnIndex(USERS_COLUMN_PASSWORD));
                if (password.equals(pwd)) {
                    //res.close();
                    //res.getInt(res.g)
                    return res.getInt(res.getColumnIndex(USERS_COLUMN_ID));
                }
                res.moveToNext();
            }
        }

        res.close();
        return 0;
    }
    public Cursor getItemID(int userid1,String item){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from items where item= ? and userid= "+userid1+"", new String[] { item } );
        //Cursor res = db.rawQuery( "select * from items where item= "+item+ " and userid= "+userid1+"", null);

        return res;

    }
    public String getOurName(int userid1){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res=db.rawQuery( "select * from users where  userid= "+userid1+"", null);
        String ourName="";
        if(res.getCount() > 0) {
            res.moveToFirst();
            while (!res.isAfterLast()) {
                ourName=res.getString(res.getColumnIndex(USERS_COLUMN_NAME));
                res.moveToNext();
            }
        }
    return ourName;
    }


    public boolean insertContact (String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }
    //The getData___ family will return a pointer to all of the data we queried.
    //ex. getDataItem(int id) will return all items belonging to a particular user id.
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }
    public Cursor getDataItem(int id,int itemid2) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from items where userid="+id+" and itemid ="+itemid2+"", null );
        return res;
    }
    public Cursor getDataUsers(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where id="+id+"", null );
        return res;
    }
    public Cursor getDataAllExpenses(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from expenses where userid="+id+"", null );
        return res;
    }
    public Cursor getDataExpensesForItem(int id,int iid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from expenses where userid="+id+" and itemid="+iid+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateItem (int itemid, int userid, String item, String description, int price,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMS_COLUMN_USER_ID, userid);
        contentValues.put(ITEMS_COLUMN_ITEM, item);
        contentValues.put(ITEMS_COLUMN_DESCRIPTION, description);
        //contentValues.put(ITEM, street);
        //contentValues.put("place", place);
        db.update("items", contentValues, "id = ? ", new String[] { Integer.toString(itemid) } );
        return true;
    }
    public boolean updateExpense (int expensesid, int userid, int itemid, int price,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("expensesid", expensesid);
        contentValues.put("userid", userid);
        contentValues.put("itemid", itemid);
        contentValues.put("price", price);
        contentValues.put("date", date);
        db.update("expenses", contentValues, "expensesid = ? ", new String[] { Integer.toString(expensesid) } );
        return true;
    }
    public void itsANewDay(int id,String date){
        ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<String> array_list2 = new ArrayList<String>();

        String todaysDate=date();
        array_list=getAllItemsNameWithDate(id,date);
        array_list2=getAllItemsDescriptionWithDate(id,date);
        for(int i=0;i<array_list.size();i++) {
            insertItem(0, id, array_list.get(i), array_list2.get(i), 0, todaysDate);
        }
        dateUpdate(id);
    }
    public String lastLoginDate(int uid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where userid="+uid+"",null );
        res.moveToFirst();
        String results="";
        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            //x=x+res.getInt(res.getColumnIndex(EXPENSES_COLUMN_PRICE));
            results=res.getString(res.getColumnIndex(USERS_COLUMN_DATE));
            res.moveToNext();
        }
        return results;
    }
    public void dateUpdate(int uid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String todaysDate=date();

        contentValues.put("date", todaysDate);
        db.update("users", contentValues, "userid = ? ", new String[] { Integer.toString(uid) } );
        //return true;
    }
    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer deleteItem (Integer id) {
        deleteExpense(id);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("items",
                "itemid = ? ",
                new String[] { Integer.toString(id) });
    }
    public Integer deleteExpense (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("expenses",
                "expensesid = ? ",
                new String[] { Integer.toString(id) });
    }

    //The getAll___ family will return an array list containing all Strings that are relevant to the table.
    //ex. getAllItems() will return every single item in the table. It does not check for user id, so every single
    //item will be returned, even duplicates.
    public int getSumDaily(int userid1,int itemid1,String date1){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from expenses where userid="+userid1+" and date= ?", new String[] { date1 } );
        res.moveToFirst();
        int x=0;
        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            x=x+res.getInt(res.getColumnIndex(EXPENSES_COLUMN_PRICE));
            res.moveToNext();
        }
        return x;
    }
//    public ArrayList<String> getAllExpensesWithDate(int id,String date) {
//        ArrayList<String> array_list = new ArrayList<String>();
//
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from expenses where userid="+id+"and expenses.date= ?", new String[] { date} );
//        res.moveToFirst();
//
//        while(res.isAfterLast() == false){
//            array_list.add(res.getString(res.getColumnIndex(EXPENSES_COLUMN_PRICE)));
//            res.moveToNext();
//        }
//        return array_list;
//    }

    public void logOut(int uid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("log", 0);
        db.update("users", contentValues, "userid = ? ", new String[] { Integer.toString(uid) } );
        //return true;
    }

    public void setLogIn(int uid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("log", 1);
        db.update("users", contentValues, "userid = ? ", new String[] { Integer.toString(uid) } );
        //return true;
    }
    public int logInSearch(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where log="+1+"" ,null );
        res.moveToFirst();
        int x=0;
        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            x=x+res.getInt(res.getColumnIndex(USERS_COLUMN_ID));

            res.moveToNext();
            return x;
        }
        return -1;
    }
    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllUsersName() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllItemsName(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from items where userid="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_ITEM)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllItemsNameWithDate(int id,String date) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor r=db.rawQuery("select * from items,expenses where expenses.date= ? and items.userid= ? ", new String[] { date,Integer.toString(id) });
        Cursor r=db.rawQuery("select items.item from items inner join expenses on expenses.itemid = items.itemid where items.userid="+id+
                " and expenses.date= ?", new String[] { date});
        r.moveToFirst();

        while(r.isAfterLast() == false){
            array_list.add(r.getString(r.getColumnIndex(ITEMS_COLUMN_ITEM)));
            r.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllItemsDescriptionWithDate(int id,String date) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor r=db.rawQuery("select * from items,expenses where expenses.date= ? and items.userid= ? ", new String[] { date,Integer.toString(id) });
        Cursor r=db.rawQuery("select items.description from items inner join expenses on expenses.itemid = items.itemid where items.userid="+id+
                " and expenses.date= ?", new String[] { date});
        r.moveToFirst();

        while(r.isAfterLast() == false){
            array_list.add(r.getString(r.getColumnIndex(ITEMS_COLUMN_DESCRIPTION)));
            r.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllExpenses(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from expenses where userid="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(EXPENSES_COLUMN_PRICE)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllExpensesWithDate(int id,String date) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from expenses where userid="+id+"and expenses.date= ?", new String[] { date} );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(EXPENSES_COLUMN_PRICE)));
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<String> getAllDates(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from expenses where userid="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(EXPENSES_COLUMN_date)));
            res.moveToNext();
        }
        return array_list;
    }
    public String date() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMMM dd, yyyy");
//        String output = outputFormat.format(inputFormat.parse(String.valueOf(dNow)));

        //System.out.println("Current Date: "+ft.format(dNow));
        return ft.format(dNow);
    }
}