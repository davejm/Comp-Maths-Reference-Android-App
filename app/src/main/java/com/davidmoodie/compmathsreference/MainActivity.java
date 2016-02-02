package com.davidmoodie.compmathsreference;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDbHelper;
    private ArrayList<String> topics;
    private ArrayAdapter<String> topicsAdapter;
    private ListView lvTopics;

    public boolean deleteDataBase() {
        File dbFile = new File("/data/data/com.davidmoodie.compmathsreference/databases/compmaths.db");
        if (dbFile.exists()) {
            return dbFile.delete();
        }
        return false;
    }

    public void doDataBase() {
        myDbHelper = new DataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
            Log.d("TAG", "doDataBase: Created db");
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            myDbHelper.openDataBase();
        } catch(SQLiteException sqle){
            throw sqle;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        deleteDataBase();
        doDataBase();

        lvTopics = (ListView) findViewById(R.id.lvTopics);
        topics = new ArrayList<String>();
        topicsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics);
        lvTopics.setAdapter(topicsAdapter);
        topics.add("First Item");
        topics.add("Second Item");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
