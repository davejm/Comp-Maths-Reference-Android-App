package com.davidmoodie.compmathsreference;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDbHelper;
    private ArrayList<IDString> topics;
    private ArrayAdapter<IDString> topicsAdapter;
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
        populateTopicsListView();

        setupListViewListener();
    }

    /** Queries topics table of database and returns list of topic names
     *
     * @return topic names
     */
    public void populateTopicsListView() {
        lvTopics = (ListView) findViewById(R.id.lvTopics);
        topics = new ArrayList<IDString>();
        topicsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topics);
        lvTopics.setAdapter(topicsAdapter);
        Cursor cursor = myDbHelper.selectQuery("SELECT * FROM topic;");
        if (cursor.moveToFirst()) {
            do {
                topics.add(new IDString(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void setupListViewListener() {
        lvTopics.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        Intent i = new Intent(getBaseContext(), ChaptersActivity.class);
                        i.putExtra("topicID", topics.get(pos).getID());
                        startActivity(i);
                    }

                });
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
